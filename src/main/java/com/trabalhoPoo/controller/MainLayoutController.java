package com.trabalhoPoo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import java.io.IOException;

public class MainLayoutController {

    @FXML
    private AnchorPane contentPane; // O painel central onde o conteúdo será carregado

    // Método genérico para carregar conteúdo no painel central
    private void loadFXML(String fxmlPath) {
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

        } catch (IOException e) {
            e.printStackTrace();
            // Trate o erro (ex: exibir um alerta)
        }
    }

    @FXML
    private void handleDashboard() {
        loadFXML("/fxml/dashboard.fxml");
    }

    @FXML
    private void handleProjetos() {
        loadFXML("/fxml/detalhes_projeto.fxml");  // Ou o FXML que você quer para a lista de projetos
    }

    @FXML
    private void handleAtividades() {
        loadFXML("/fxml/detalhes_atividade.fxml"); // Ou o FXML para a lista de Atividades.
    }
    @FXML
    private void handleNotificacoes(){
        loadFXML("/fxml/notificacoes.fxml");
    }
    // Adicione outros métodos handle... para cada item do menu

    //Adicione este método para que seja possível acessar o contentPane de outras classes.
    public AnchorPane getContentPane() {
        return contentPane;
    }
}