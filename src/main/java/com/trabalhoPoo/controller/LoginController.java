package com.trabalhoPoo.controller;

import com.trabalhoPoo.MainApp;
import com.trabalhoPoo.dao.UsuarioDAO;
import com.trabalhoPoo.model.Usuario;
import com.trabalhoPoo.util.PasswordUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.SQLException;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.layout.AnchorPane;

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
                    //abrirDashboard(usuario); // <---  NÃO ABRA MAIS UMA NOVA JANELA!
                    MainApp.changeScene("/fxml/main_layout.fxml"); // Use o método changeScene do MainApp

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main_layout.fxml"));
                    Parent root = loader.load();

                    // Obtém o controller do MainLayout DEPOIS de carregar a cena principal
                    MainLayoutController mainLayoutController = loader.getController(); //Obtém o controller
                    //Não é mais necessário passar o usuário, ele é gerenciado no proprio controller do dashboard.
                    // mainLayoutController.setUsuarioLogado(usuario);

                    //Carrega o fxml do dashboard e o define como conteúdo do painel central do MainLayout.
                    FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml")); //Carrega o dashboard
                    AnchorPane dashboardPane = dashboardLoader.load();  //  Carrega o AnchorPane do dashboard

                    DashboardController dashboardController = dashboardLoader.getController(); //Pega o controller
                    dashboardController.setUsuarioLogado(usuario); //Define o usuário logado

                    //Obtém o contentPane do MainLayoutController através de um método público que você irá criar.
                    AnchorPane mainContentPane = mainLayoutController.getContentPane();
                    //Define o conteúdo do contentPane do MainLayout como sendo o conteúdo do Dashboard.
                    mainContentPane.getChildren().setAll(dashboardPane); //Adiciona o dashboard


                    return; //  <-- Importante:  Sair do método se o login for bem-sucedido
                } else {
                    System.out.println("Senha incorreta!");
                    showAlert(AlertType.ERROR, "Erro de Login", "Email ou senha inválidos.");
                }
            } else {
                System.out.println("Usuário não encontrado para o email: " + email);
                showAlert(AlertType.ERROR, "Erro de Login", "Email ou senha inválidos.");
            }

        } catch (SQLException | IOException e) {
            showAlert(AlertType.ERROR, "Erro de Banco de Dados", "Erro ao acessar o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    private void handleCadastro() {
        //Abrir tela de cadastro
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cadastro.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            CadastroController controller = loader.getController();
            //controller.setMainApp(this.mainApp); //Se você precisar passar dados

            stage.show();

            // Fechar a janela de login, opcional
            ((Stage) emailField.getScene().getWindow()).close();
        }catch(IOException e){
            showAlert(AlertType.ERROR, "Erro", "Erro ao abrir a tela de Cadastro: " + e.getMessage());
            e.printStackTrace();
        }

    }
    /*
    private void abrirDashboard(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            DashboardController controller = loader.getController();
            controller.setUsuarioLogado(usuario); // Passa o usuário logado para o DashboardController
            //controller.setMainApp(this.mainApp);

            stage.show();

            // Fechar a janela de login
            ((Stage) emailField.getScene().getWindow()).close();

        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Erro", "Erro ao abrir o Dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }
    */


    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}