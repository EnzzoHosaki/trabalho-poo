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
import javafx.scene.layout.HBox; // Import HBox


public class ProjetosController {

    @FXML private TableView<Projeto> projetosTable;
    @FXML private TableColumn<Projeto, String> nomeCol;
    @FXML private TableColumn<Projeto, LocalDate> dataTerminoCol;
    @FXML private TableColumn<Projeto, String> statusCol;
    @FXML private TableColumn<Projeto, Void> acoesCol; // Coluna para botões de ação

    private ProjetoDAO projetoDAO = new ProjetoDAO();
    private ObservableList<Projeto> projetosData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        dataTerminoCol.setCellValueFactory(new PropertyValueFactory<>("dataTermino"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Configura a coluna de ações (botão "Visualizar")
        acoesCol.setCellFactory(criarBotoesAcao());

        carregarProjetos();
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
                    private final Button btnExcluir = new Button("Excluir");// Botão Excluir


                    {
                        btnVisualizar.setOnAction(event -> {
                            Projeto projeto = getTableView().getItems().get(getIndex());
                            abrirTelaDetalhesProjeto(projeto);
                        });

                        //Ação do botão editar
                        btnEditar.setOnAction(event -> {
                            Projeto projeto = getTableView().getItems().get(getIndex());
                            abrirTelaEditarProjeto(projeto);
                        });

                        //Ação do botão excluir
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
                            // Adiciona os botões à célula
                            HBox pane = new HBox(btnVisualizar, btnEditar, btnExcluir); // Adiciona os botões
                            pane.setSpacing(10); //Espaçamento entre os botões
                            setGraphic(pane);

                        }
                    }
                };
            }
        };
    }
    //Método para abrir a tela de detalhes, agora recebendo o Projeto
    private void abrirTelaDetalhesProjeto(Projeto projeto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/detalhes_projeto.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Detalhes do Projeto");

            ProjetoController controller = loader.getController();
            controller.setProjeto(projeto); // Passa o projeto

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
            controller.setProjeto(projeto); //Passa o projeto para a tela
            stage.show();

            stage.setOnHiding(event -> carregarProjetos()); // Atualiza a tabela quando fechar a tela
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
    // Adicione os métodos handleEditarProjeto e handleExcluirProjeto aqui
    private void excluirProjeto(Projeto projeto){
        // 1. Confirmar a exclusão
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Excluir Projeto");
        alert.setContentText("Tem certeza que deseja excluir o projeto '" + projeto.getNome() + "'?");

        Optional<ButtonType> result = alert.showAndWait();

        // 2. Se confirmar, excluir
        if (result.isPresent() && result.get() == ButtonType.OK){
            try{
                projetoDAO.excluirProjeto(projeto.getId()); //Chama o método do DAO
                projetosData.remove(projeto);       //Remove da lista da tabela
                showAlert(Alert.AlertType.INFORMATION, "Sucesso!", "Projeto Excluído com Sucesso!");
            } catch(SQLException e){
                showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao excluir o projeto: "+ e.getMessage());
                e.printStackTrace();
            }
        }
    }
}