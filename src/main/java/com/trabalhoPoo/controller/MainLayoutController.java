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
    private Button criarProjetoButton; //Botão criar projeto

    private Usuario usuarioLogado;

    // Método genérico para carregar conteúdo no painel central, agora RETORNA o controller
    private Object loadFXML(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node content = loader.load();

            // Limpa o conteúdo anterior e adiciona o novo
            contentPane.getChildren().clear();
            contentPane.getChildren().add(content);

            // Ajusta o conteúdo para preencher o painel central
            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);

            return loader.getController(); // Retorna o controller

        } catch (IOException e) {
            e.printStackTrace();
            // Trate o erro (ex: exibir um alerta)
            return null; // Retorna null em caso de erro
        }
    }
    //Novos métodos

    //SETANDO O USUARIO LOGADO
    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;

        // Verifica o tipo de usuário
        if (usuarioLogado != null && "Administrador".equals(usuarioLogado.getTipo())) {
            criarProjetoButton.setVisible(true);
            criarProjetoButton.setManaged(true);
        } else {
            criarProjetoButton.setVisible(false);
            criarProjetoButton.setManaged(false);
        }
        //Não chamamos mais o handleDashboard aqui, o usuário vai ser passado como parâmetro
        // handleDashboard(); // ESTA LINHA FAZ O DASHBOARD SER CARREGADO!
    }
    @FXML
    private void handleDashboard() {
        //loadFXML("/fxml/dashboard.fxml"); //Não carrega mais o fxml diretamente
        //Agora, passamos o usuarioLogado
        DashboardController controller = (DashboardController) loadFXML("/fxml/dashboard.fxml");
        if (controller != null) {
            controller.setUsuarioLogado(this.usuarioLogado); //Passa o usuário
        }

    }

    @FXML
    private void handleProjetos() {
        loadFXML("/fxml/projetos.fxml");  // Agora carrega projetos.fxml
    }

    //Removi o método handleAtividades

    @FXML
    private void handleNotificacoes(){
        loadFXML("/fxml/notificacoes.fxml");
    }

    // Ação do botão "Criar Projeto"
    @FXML
    private void handleCriarProjeto() {
        abrirTela("/fxml/criar_projeto.fxml", "Criar Projeto");
    }

    // Método utilitário para abrir telas
    private void abrirTela(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            // Trate o erro adequadamente
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout(){
        MainApp.changeScene("/fxml/login.fxml");
    }

    //Adicione este método para que seja possível acessar o contentPane de outras classes.
    public AnchorPane getContentPane() {
        return contentPane;
    }

    @FXML
    private void initialize(){
        //Deixa o botão invisível, até o usuário ser setado
        criarProjetoButton.setVisible(false);
        criarProjetoButton.setManaged(false);
    }
}