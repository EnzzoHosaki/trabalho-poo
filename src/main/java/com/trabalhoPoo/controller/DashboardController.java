package com.trabalhoPoo.controller;

import com.trabalhoPoo.model.Projeto; // Importa a classe Projeto
import com.trabalhoPoo.model.Usuario;
import com.trabalhoPoo.dao.ProjetoDAO; // Importa o DAO
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.control.Alert;


public class DashboardController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private TableView<Projeto> projetosTable; // TableView para exibir os projetos
    @FXML
    private TableColumn<Projeto, String> nomeCol; // Coluna para o nome do projeto
    @FXML
    private TableColumn<Projeto, LocalDate> dataTerminoCol; // Coluna para a data de término
    @FXML
    private TableColumn<Projeto, String> statusCol; // Coluna para o status
    @FXML
    private Button criarProjetoButton;


    private Usuario usuarioLogado;
    private ProjetoDAO projetoDAO = new ProjetoDAO(); // Instância do DAO
    private ObservableList<Projeto> projetosData = FXCollections.observableArrayList(); // Lista observável para a TableView

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        welcomeLabel.setText("Bem-vindo, " + usuarioLogado.getNome() + "!");
        // Verifique se o usuário é um administrador e defina a visibilidade do botão com base nisso
        if ("Administrador".equals(usuario.getTipo())) {
            criarProjetoButton.setVisible(true); // Torna o botão visível
            criarProjetoButton.setManaged(true); // Garante que o botão ocupe espaço no layout
        } else {
            criarProjetoButton.setVisible(false); // Torna o botão invisível
            criarProjetoButton.setManaged(false); // Garante que o botão não ocupe espaço no layout
        }

        carregarProjetos(); // Carrega os projetos após o login
    }

    @FXML
    private void initialize() {
        // Configura as colunas da TableView
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        dataTerminoCol.setCellValueFactory(new PropertyValueFactory<>("dataTermino"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        //Deixei o botão de criar projeto invisível
        criarProjetoButton.setVisible(false);
        criarProjetoButton.setManaged(false);
    }

    private void carregarProjetos() {
        try {
            projetosData.addAll(projetoDAO.listarProjetos());
            projetosTable.setItems(projetosData);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao carregar projetos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    private void handleCriarProjeto() {
        //Abrir tela de cadastro
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/criar_projeto.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            ProjetoController controller = loader.getController();


            stage.show();

            // Fechar a janela de login, opcional
            //((Stage) emailField.getScene().getWindow()).close();
        }catch(IOException e){
            showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir a tela de Criar Projeto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}