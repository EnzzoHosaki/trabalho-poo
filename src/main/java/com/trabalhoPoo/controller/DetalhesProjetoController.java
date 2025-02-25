package com.trabalhoPoo.controller;

import com.trabalhoPoo.dao.AtividadeDAO;
import com.trabalhoPoo.dao.ProjetoDAO;
import com.trabalhoPoo.model.Atividade;
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

public class DetalhesProjetoController {

    @FXML private Label nomeLabel;
    @FXML private Label descricaoLabel;
    @FXML private Label dataInicioLabel;
    @FXML private Label dataTerminoLabel;
    @FXML private Label statusLabel;
    @FXML private TableView<Atividade> atividadesTable;
    @FXML private TableColumn<Atividade, String> atividadeNomeCol;
    @FXML private TableColumn<Atividade, String> atividadeStatusCol;
    @FXML private TableColumn<Atividade, Void> atividadeAcoesCol;

    private Projeto projeto;
    private AtividadeDAO atividadeDAO = new AtividadeDAO();
    private ObservableList<Atividade> atividadesData = FXCollections.observableArrayList();

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
        if (projeto != null) {
            exibirDetalhesProjeto();
            carregarAtividades();
        }
    }

    private void exibirDetalhesProjeto() {
        nomeLabel.setText("Nome: " + projeto.getNome());
        descricaoLabel.setText("Descrição: " + projeto.getDescricao());
        dataInicioLabel.setText("Início: " + projeto.getDataInicio().toString());
        dataTerminoLabel.setText("Término: " + projeto.getDataTermino().toString());
        statusLabel.setText("Status: " + projeto.getStatus());
    }


    private void carregarAtividades() {
        try {
            atividadesData.clear();
            atividadesData.addAll(atividadeDAO.listarAtividadesPorProjeto(projeto.getId()));
            atividadesTable.setItems(atividadesData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        atividadeNomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        atividadeStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        atividadeAcoesCol.setCellFactory(criarBotoesAcaoAtividade());
    }

    private Callback<TableColumn<Atividade, Void>, TableCell<Atividade, Void>> criarBotoesAcaoAtividade() {
        return new Callback<>() {
            @Override
            public TableCell<Atividade, Void> call(final TableColumn<Atividade, Void> param) {
                return new TableCell<>() {

                    private final Button btnVisualizar = new Button("Visualizar");
                    private final Button btnEditar = new Button("Editar");
                    private final Button btnExcluir = new Button("Excluir");

                    {
                        btnVisualizar.setOnAction(event -> {
                            Atividade atividade = getTableView().getItems().get(getIndex());
                            abrirTelaDetalhesAtividade(atividade); // Implemente este método
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


    @FXML
    private void handleAdicionarAtividade() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/criar_atividade.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Criar Atividade");

            AtividadeController controller = loader.getController();
            controller.setProjeto(projeto);
            stage.show();

        } catch (IOException e){
            showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao carregar a tela de Criar Atividade: " + e.getMessage());
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

    private void abrirTelaDetalhesAtividade(Atividade atividade) {

    }
}