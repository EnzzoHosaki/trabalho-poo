package com.trabalhoPoo.controller;

import com.trabalhoPoo.model.Projeto;
import com.trabalhoPoo.model.Usuario;
import com.trabalhoPoo.dao.ProjetoDAO;
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
import javafx.scene.control.Alert.AlertType;

public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private TableView<Projeto> projetosTable;
    @FXML private TableColumn<Projeto, String> nomeCol;
    @FXML private TableColumn<Projeto, LocalDate> dataTerminoCol;
    @FXML private TableColumn<Projeto, String> statusCol;
    @FXML private Button criarProjetoButton;
    @FXML private Button visualizarProjetoButton; // Botão para visualizar projeto
    @FXML private Button notificacoesButton; //Botão para as notificações

    private Usuario usuarioLogado;
    private ProjetoDAO projetoDAO = new ProjetoDAO();
    private ObservableList<Projeto> projetosData = FXCollections.observableArrayList();

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        welcomeLabel.setText("Bem-vindo, " + usuarioLogado.getNome() + "!");
        //Verifica o tipo de usuário
        if ("Administrador".equals(usuario.getTipo())) {
            criarProjetoButton.setVisible(true);
            criarProjetoButton.setManaged(true);
        } else {
            criarProjetoButton.setVisible(false);
            criarProjetoButton.setManaged(false);
        }

        carregarProjetos();
    }

    @FXML
    private void initialize() {
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        dataTerminoCol.setCellValueFactory(new PropertyValueFactory<>("dataTermino"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        criarProjetoButton.setVisible(false);
        criarProjetoButton.setManaged(false);
        //Desabilitar o botão no inicio, e habilita-lo quando um item for selecionado
        visualizarProjetoButton.setDisable(true);
        //Adiciona um listener na tabela para quando selecionar a linha
        projetosTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    visualizarProjetoButton.setDisable(newValue == null);
                }
        );
    }

    private void carregarProjetos() {
        try {
            projetosData.addAll(projetoDAO.listarProjetos());
            projetosTable.setItems(projetosData);
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Erro", "Erro ao carregar projetos: " + e.getMessage());
            e.printStackTrace();

        }
    }

    // Ação do botão "Criar Projeto"
    @FXML
    private void handleCriarProjeto() {
        abrirTela("/fxml/criar_projeto.fxml", "Criar Projeto");
    }

    // Ação do botão "Visualizar Projeto"
    @FXML
    private void handleVisualizarProjeto() {
        Projeto projetoSelecionado = projetosTable.getSelectionModel().getSelectedItem();
        if (projetoSelecionado != null) {
            abrirTelaDetalhesProjeto(projetoSelecionado);

        } else {
            //Nenhum projeto selecionado
            showAlert(AlertType.WARNING, "Aviso", "Selecione um projeto para visualizar.");
        }
    }
    @FXML
    private void handleNotificacoes(){
        abrirTela("/fxml/notificacoes.fxml", "Notificações");
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
            showAlert(AlertType.ERROR, "Erro", "Erro ao abrir a tela: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Modificado para receber um projeto como parâmetro
    private void abrirTelaDetalhesProjeto(Projeto projeto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/detalhes_projeto.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Detalhes do Projeto");
            // Obtém o controller *ANTES* de mostrar a tela
            ProjetoController controller = loader.getController();
            // Passa o projeto selecionado para o controller da tela de detalhes
            controller.setProjeto(projeto);

            stage.show();

        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Erro", "Erro ao abrir a tela de detalhes do projeto: " + e.getMessage());
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