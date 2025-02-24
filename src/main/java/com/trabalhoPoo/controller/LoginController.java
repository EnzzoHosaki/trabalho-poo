package com.trabalhoPoo.controller;

import com.trabalhoPoo.dao.UsuarioDAO;
import com.trabalhoPoo.model.Usuario;
import com.trabalhoPoo.util.PasswordUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.SQLException;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String senha = senhaField.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            showAlert(AlertType.ERROR, "Erro de Login", "Preencha todos os campos.");
            return;
        }

        try {
            Usuario usuario = usuarioDAO.buscarUsuarioPorEmail(email);

            if (usuario != null) {
                System.out.println("Usuário encontrado: " + usuario.getEmail());

                if (PasswordUtil.checkPassword(senha, usuario.getSenha())) {
                    System.out.println("Senha correta (jBCrypt)!");
                    abrirDashboard(usuario);
                    return; //  <--  Importante:  Sair do método se o login for bem-sucedido
                }

                String hashedInputPassword = PasswordUtil.hashPasswordOld(senha); // Usando método ANTIGO
                if (hashedInputPassword.equals(usuario.getSenha())) {
                    System.out.println("Senha correta (método antigo)!  Migrando para jBCrypt...");

                    String novoHash = PasswordUtil.hashPassword(senha);

                    usuario.setSenha(novoHash);
                    usuarioDAO.atualizarUsuario(usuario);

                    System.out.println("Senha migrada com sucesso!");
                    abrirDashboard(usuario); // Agora o login funciona
                    return; //  <-- Importante!
                }

                // 3. SE AMBAS AS TENTATIVAS FALHAREM:
                System.out.println("Senha incorreta!");
                showAlert(AlertType.ERROR, "Erro de Login", "Email ou senha inválidos.");

            } else {
                System.out.println("Usuário não encontrado para o email: " + email);
                showAlert(AlertType.ERROR, "Erro de Login", "Email ou senha inválidos.");
            }

        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Erro de Banco de Dados", "Erro ao acessar o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    private void handleCadastro() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cadastro.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            CadastroController controller = loader.getController();

            stage.show();

            ((Stage) emailField.getScene().getWindow()).close();
        }catch(IOException e){
            showAlert(AlertType.ERROR, "Erro", "Erro ao abrir a tela de Cadastro: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private void abrirDashboard(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            DashboardController controller = loader.getController();
            controller.setUsuarioLogado(usuario);

            stage.show();

            ((Stage) emailField.getScene().getWindow()).close();

        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Erro", "Erro ao abrir o Dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}