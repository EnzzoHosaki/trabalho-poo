package com.trabalhoPoo.controller;

import com.trabalhoPoo.dao.UsuarioDAO;
import com.trabalhoPoo.model.Usuario;
import com.trabalhoPoo.util.PasswordUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.sql.SQLException;

public class CadastroController {

    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private PasswordField confirmarSenhaField;
    //@FXML private ComboBox<String> tipoUsuarioComboBox; // Removido temporariamente
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    private void initialize() {
        // Para inicializar o ComboBox (Removido temporariamente)
        //tipoUsuarioComboBox.getItems().addAll("Administrador", "Usuario");
    }

    @FXML
    private void handleCadastrar() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();
        String confirmarSenha = confirmarSenhaField.getText();
        //String tipoUsuario = tipoUsuarioComboBox.getValue();  // Removido temporariamente
        String tipoUsuario = "Usuario"; // Definindo como Usuario comum para o MVP

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty() /*|| tipoUsuario == null*/) {
            showAlert(AlertType.ERROR, "Erro de Cadastro", "Preencha todos os campos.");
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            showAlert(AlertType.ERROR, "Erro de Cadastro", "As senhas não coincidem.");
            return;
        }

        try {
            //Verifica se já existe um usuário com o mesmo email
            if(usuarioDAO.buscarUsuarioPorEmail(email) != null){
                showAlert(AlertType.ERROR, "Erro de Cadastro", "Já existe um usuário cadastrado com esse email.");
                return;
            }

            // Hash da senha ANTES de salvar no banco
            String senhaHash = PasswordUtil.hashPassword(senha);  //  <--  Usando o método CORRETO do jBCrypt
            Usuario novoUsuario = new Usuario(nome, email, senhaHash, tipoUsuario);

            usuarioDAO.adicionarUsuario(novoUsuario);
            showAlert(AlertType.INFORMATION, "Cadastro", "Usuário cadastrado com sucesso!");
            abrirLogin();


        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Erro de Banco de Dados", "Erro ao cadastrar usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    private void handleVoltarLogin() {
        abrirLogin();
    }

    private void abrirLogin(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
            ((Stage) nomeField.getScene().getWindow()).close(); // Fecha cadastro
        }catch(IOException e){
            showAlert(AlertType.ERROR, "Erro", "Erro ao abrir Login: " + e.getMessage());
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