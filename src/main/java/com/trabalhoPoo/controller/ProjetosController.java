package com.trabalhoPoo.controller;

import com.trabalhoPoo.dao.ProjetoDAO;
import com.trabalhoPoo.model.Projeto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import javafx.scene.layout.HBox;

public class ProjetosController {

    @FXML private TableView<Projeto> projetosTable;
    @FXML private TableColumn<Projeto, String> nomeCol;
    @FXML private TableColumn<Projeto, LocalDate> dataInicioCol;
    @FXML private TableColumn<Projeto, LocalDate> dataTerminoCol;
    @FXML private TableColumn<Projeto, String> statusCol;
    @FXML private TableColumn<Projeto, Void> acoesCol;

    private ProjetoDAO projetoDAO = new ProjetoDAO();
    private ObservableList<Projeto> projetosData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        dataInicioCol.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        dataTerminoCol.setCellValueFactory(new PropertyValueFactory<>("dataTermino"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        acoesCol.setCellFactory(criarBotoesAcao());

        carregarProjetos();
    }

    @FXML
    private void handleCriarProjeto() {
        abrirTela("/fxml/criar_projeto.fxml", "Criar Projeto");
    }


    public void carregarProjetos() {
        try {
            projetosData.clear();
            projetosData.addAll(projetoDAO.listarProjetos());
            projetosTable.setItems(projetosData);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao carregar projetos: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private Callback<TableColumn<Projeto, Void>, TableCell<Projeto, Void>> criarBotoesAcao() {
        return new Callback<>() {
            @Override
            public TableCell<Projeto, Void> call(final TableColumn<Projeto, Void> param) {
                return new TableCell<>() {

                    private final Button btnVisualizar = new Button("Visualizar");
                    private final Button btnEditar = new Button("Editar"); // Botão Editar
                    private final Button btnExcluir = new Button("Excluir"); // Botão Excluir


                    {
                        btnVisualizar.setOnAction(event -> {
                            Projeto projeto = getTableView().getItems().get(getIndex());
                            abrirTelaDetalhesProjeto(projeto);
                        });

                        btnEditar.setOnAction(event -> {
                            Projeto projeto = getTableView().getItems().get(getIndex());
                            abrirTelaEditarProjeto(projeto);
                        });

                        btnExcluir.setOnAction(event -> {
                            Projeto projeto = getTableView().getItems().get(getIndex());
                            excluirProjeto(projeto);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {

                            HBox pane = new HBox(btnVisualizar, btnEditar, btnExcluir);
                            pane.setSpacing(10);
                            setGraphic(pane);

                        }
                    }
                };
            }
        };
    }

    private void abrirTela(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir a tela: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para abrir a tela de detalhes, agora genérico.
    private void abrirTelaDetalhesProjeto(Projeto projeto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/detalhes_projeto.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Detalhes do Projeto");

            DetalhesProjetoController controller = loader.getController();
            controller.setProjeto(projeto);

            stage.show();

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao carregar a tela Detalhes do Projeto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Método para a tela de edição
    private void abrirTelaEditarProjeto(Projeto projeto){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/criar_projeto.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Editar Projeto");

            ProjetoController controller = loader.getController();
            controller.setProjeto(projeto);
            stage.show();

            stage.setOnHiding(event -> carregarProjetos());
        } catch (IOException e){
            showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir a tela de Editar Projeto: " + e.getMessage());
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

    private void excluirProjeto(Projeto projeto){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Excluir Projeto");
        alert.setContentText("Tem certeza que deseja excluir o projeto '" + projeto.getNome() + "'?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK){
            try{
                projetoDAO.excluirProjeto(projeto.getId());
                projetosData.remove(projeto);
                showAlert(Alert.AlertType.INFORMATION, "Sucesso!", "Projeto Excluído com Sucesso!");
            } catch(SQLException e){
                showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao excluir o projeto: "+ e.getMessage());
                e.printStackTrace();
            }
        }
    }
}