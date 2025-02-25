package com.trabalhoPoo.controller;

import com.trabalhoPoo.MainApp;
import com.trabalhoPoo.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class MainLayoutController {

    @FXML
    private AnchorPane contentPane;
    @FXML
    private VBox sideMenuVBox;
    @FXML
    private Button criarProjetoButton;

    private Usuario usuarioLogado;

    private Object loadFXML(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node content = loader.load();

            contentPane.getChildren().clear();
            contentPane.getChildren().add(content);

            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);

            return loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;

        if (usuarioLogado != null && "Administrador".equals(usuarioLogado.getTipo())) {
            criarProjetoButton.setVisible(true);
            criarProjetoButton.setManaged(true);
        } else {
            criarProjetoButton.setVisible(false);
            criarProjetoButton.setManaged(false);
        }
        handleDashboard();
    }
    @FXML
    private void handleDashboard() {
        DashboardController controller = (DashboardController) loadFXML("/fxml/dashboard.fxml");
        if (controller != null) {
            controller.setUsuarioLogado(this.usuarioLogado);
        }

    }

    @FXML
    private void handleProjetos() {
        loadFXML("/fxml/projetos.fxml");  // Agora carrega projetos.fxml
    }

    @FXML
    private void handleNotificacoes(){
        loadFXML("/fxml/notificacoes.fxml");
    }

    @FXML
    private void handleCriarProjeto() {
        abrirTela("/fxml/criar_projeto.fxml", "Criar Projeto");
    }

    private void abrirTela(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleLogout(){
        MainApp.changeScene("/fxml/login.fxml");
    }

    public AnchorPane getContentPane() {
        return contentPane;
    }

    @FXML
    private void initialize(){
        criarProjetoButton.setVisible(false);
        criarProjetoButton.setManaged(false);
    }
}