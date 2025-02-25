package com.trabalhoPoo.controller;

import com.trabalhoPoo.dao.AtividadeDAO;
import com.trabalhoPoo.dao.UsuarioDAO;
import com.trabalhoPoo.model.Atividade;
import com.trabalhoPoo.model.Projeto;
import com.trabalhoPoo.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.time.LocalDate;

public class AtividadeController {

    @FXML private TextField nomeField;
    @FXML private TextArea descricaoField;
    @FXML private DatePicker dataInicioField;
    @FXML private DatePicker dataTerminoField;
    @FXML private ComboBox<Usuario> responsaveisField;
    @FXML private TextField porcentagemConclusaoField;
    @FXML private TextArea justificativaField;
    @FXML private ComboBox<String> statusField;
    @FXML private Label nomeLabel;
    @FXML private Label descricaoLabel;
    @FXML private Label dataInicioLabel;
    @FXML private Label dataTerminoLabel;
    @FXML private Label statusLabel;
    @FXML private Label porcentagemLabel;
    @FXML private Label justificativaLabel;


    private AtividadeDAO atividadeDAO = new AtividadeDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Projeto projeto;
    private Atividade atividade;
    private boolean novaAtividade = true;

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }
    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;

        if (atividade != null) {

            this.novaAtividade = false;
            carregarUsuarios();

            if(nomeField != null){
                nomeField.setText(atividade.getNome());
                descricaoField.setText(atividade.getDescricao());
                dataInicioField.setValue(atividade.getDataInicio());
                dataTerminoField.setValue(atividade.getDataTermino());
                statusField.setValue(atividade.getStatus());
                porcentagemConclusaoField.setText(Integer.toString(atividade.getPorcentagemConclusao()));
                justificativaField.setText(atividade.getJustificativa());

            }
            if(nomeLabel != null){
                nomeLabel.setText("Nome da atividade: "+ atividade.getNome());
                descricaoLabel.setText("Descrição: "+ atividade.getDescricao());
                dataInicioLabel.setText("Data de Inicio: "+ atividade.getDataInicio().toString());
                dataTerminoLabel.setText("Data de Termino: " + atividade.getDataTermino().toString());
                statusLabel.setText("Status: " + atividade.getStatus());
                porcentagemLabel.setText("Progresso: "+ atividade.getPorcentagemConclusao() + "%");
                justificativaLabel.setText("Justificativa: "+ atividade.getJustificativa());

            }
        }
    }

    @FXML
    private void initialize() {
        carregarStatus();
        carregarUsuarios();
    }

    private void carregarUsuarios() {
        try {
            ObservableList<Usuario> usuarios = FXCollections.observableArrayList(usuarioDAO.listarUsuarios());
            responsaveisField.setItems(usuarios);

            responsaveisField.setCellFactory(lv -> new ListCell<Usuario>() {
                @Override
                protected void updateItem(Usuario item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.getNome());
                }
            });

            responsaveisField.setButtonCell(new ListCell<Usuario>() {
                @Override
                protected void updateItem(Usuario item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.getNome());
                }
            });
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao carregar usuários: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void carregarStatus(){
        ObservableList<String> status = FXCollections.observableArrayList("Dentro do Prazo", "Atrasado", "Concluído");
        statusField.setItems(status);
    }
    @FXML
    private void handleSalvar() {

        String nome = nomeField.getText();
        String descricao = descricaoField.getText();
        LocalDate dataInicio = dataInicioField.getValue();
        LocalDate dataTermino = dataTerminoField.getValue();

        int porcentagemConclusao = 0;
        try {
            porcentagemConclusao = Integer.parseInt(porcentagemConclusaoField.getText());
        }catch(NumberFormatException e){
            showAlert(Alert.AlertType.ERROR, "Erro", "Porcentagem inválida");
            return;
        }

        String justificativa = justificativaField.getText();
        String status = statusField.getValue();

        if (nome == null || nome.trim().isEmpty() ||
                descricao == null || descricao.trim().isEmpty() ||
                dataInicio == null ||
                dataTermino == null ||
                status == null) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Preencha todos os campos obrigatórios.");
            return;
        }

        if (dataInicio.isAfter(dataTermino)) {
            showAlert(Alert.AlertType.ERROR, "Erro", "A data de início não pode ser posterior à data de término.");
            return;
        }

        if(this.atividade == null){
            Atividade novaAtividade = new Atividade();
            novaAtividade.setNome(nome);
            novaAtividade.setDescricao(descricao);
            novaAtividade.setDataInicio(dataInicio);
            novaAtividade.setDataTermino(dataTermino);
            novaAtividade.setStatus(status);
            novaAtividade.setPorcentagemConclusao(porcentagemConclusao);
            novaAtividade.setJustificativa(justificativa);
            novaAtividade.setProjetoId(projeto.getId()); // Associa ao projeto!

            try {
                atividadeDAO.adicionarAtividade(novaAtividade);
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Atividade criada com sucesso!");
                Stage stage = (Stage) nomeField.getScene().getWindow();
                stage.close();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao salvar a atividade: " + e.getMessage());
                e.printStackTrace();
            }
        } else{
            try{
                this.atividade.setNome(nome);
                this.atividade.setDescricao(descricao);
                this.atividade.setDataInicio(dataInicio);
                this.atividade.setDataTermino(dataTermino);
                this.atividade.setStatus(status);
                this.atividade.setPorcentagemConclusao(porcentagemConclusao);
                this.atividade.setJustificativa(justificativa);
                this.atividade.setProjetoId(projeto.getId());
                atividadeDAO.atualizarAtividade(this.atividade);
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Atividade atualizada com sucesso!");
                Stage stage = (Stage) nomeField.getScene().getWindow();
                stage.close();
            } catch(SQLException e){
                showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao atualizar a atividade: "+ e.getMessage());
                e.printStackTrace();
            }
        }


    }

    @FXML
    private void handleCancelar() {
        Stage stage = (Stage) nomeField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void exibirDetalhesAtividade(Atividade atividade) {
        if (atividade != null) {
            nomeLabel.setText("Nome: " + atividade.getNome());
            descricaoLabel.setText("Descrição: " + atividade.getDescricao());
            dataInicioLabel.setText("Data de Início: " + atividade.getDataInicio().toString());
            dataTerminoLabel.setText("Data de Término: " + atividade.getDataTermino().toString());
            statusLabel.setText("Status: "+ atividade.getStatus());
            porcentagemLabel.setText("Progresso: " + atividade.getPorcentagemConclusao() + "%");
            justificativaLabel.setText("Justificativa: " + atividade.getJustificativa());

            nomeLabel.setDisable(false);
            descricaoLabel.setDisable(false);
            dataInicioLabel.setDisable(false);
            dataTerminoLabel.setDisable(false);
            statusLabel.setDisable(false);
            porcentagemLabel.setDisable(false);
            justificativaLabel.setDisable(false);
        }
    }
}