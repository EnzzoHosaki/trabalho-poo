package com.trabalhoPoo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApp extends Application {

    private static Stage primaryStage; // Referência estática para o palco principal

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainApp.primaryStage = primaryStage; // Armazena a referência

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml")); // Carrega o login inicialmente
        Parent root = loader.load();
        primaryStage.setTitle("Sistema de Gerenciamento de Atividades");

        // ***************************************************
        //  ADICIONE ESTA LINHA para carregar o CSS:
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm()); //  <-- AQUI!
        primaryStage.setScene(scene);
        // ***************************************************

        primaryStage.show();
    }
    //Método para trocar a tela principal
    public static void changeScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlPath));
            Scene scene = new Scene(loader.load());
            // ***************************************************
            //  ADICIONE ESTA LINHA para carregar o CSS:
            scene.getStylesheets().add(MainApp.class.getResource("/css/style.css").toExternalForm()); //  <-- AQUI!
            // ***************************************************
            primaryStage.setScene(scene); // Define a nova cena no palco principal

        } catch (IOException e) {
            e.printStackTrace();
            // Tratar o erro (ex: exibir um alerta)
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}