package com.trabalhoPoo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import com.trabalhoPoo.controller.MainLayoutController; // Import

public class MainApp extends Application {

    private static Stage primaryStage;
    private static MainLayoutController mainLayoutController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainApp.primaryStage = primaryStage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Sistema de Gerenciamento de Atividades");

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.show();
    }
    //Método para trocar a tela principal
    public static void changeScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlPath));
            Parent root = loader.load();

            if (fxmlPath.equals("/fxml/main_layout.fxml")) {
                mainLayoutController = loader.getController();
            }

            Scene scene = new Scene(root);
            scene.getStylesheets().add(MainApp.class.getResource("/css/style.css").toExternalForm());
            primaryStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    public static MainLayoutController getMainLayoutController(){
        return mainLayoutController;
    }

    public static void main(String[] args) {
        launch(args);
    }
}