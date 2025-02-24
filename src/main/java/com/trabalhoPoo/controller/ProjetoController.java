package com.trabalhoPoo.controller;

import com.trabalhoPoo.dao.ProjetoDAO;
import com.trabalhoPoo.dao.UsuarioDAO;
import com.trabalhoPoo.model.Projeto;
import com.trabalhoPoo.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label; // Importe Label

public class ProjetoController {

    @FXML private TextField nomeField;
    @FXML private TextArea descricaoField;
    @FXML private DatePicker dataInicioField;
    @FXML private DatePicker dataTerminoField;
    @FXML private ComboBox<Usuario> responsaveisField; // ComboBox para Usuários.  Verifique o tipo!
    @FXML private ComboBox<String> statusField;


    //Adicione estes campos, para a tela de detalhes:
    @FXML private Label nomeLabel;
    @FXML private Label descricaoLabel;
    @FXML private Label dataInicioLabel;
    @FXML private Label dataTerminoLabel;
    @FXML private Label statusLabel;


    private ProjetoDAO projetoDAO = new ProjetoDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO(); // Para buscar os usuários

    private Projeto projeto; //Para a função de setar o projeto (Quando for editar)
    private boolean novoProjeto = true; //Flag para controlar se é um novo projeto

    @FXML
    private void initialize() {
        //Não faz mais nada aqui!
    }
    //Modifique o método setProjeto para preencher os campos da tela de detalhes/edição:
    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;

        if (projeto != null) {
            //Modo de Edição/Visualização
            this.novoProjeto = false; //Define que não é um novo projeto

            if(nomeField != null){ //Verifica se está na tela de edição/Criação
                nomeField.setText(projeto.getNome());
                descricaoField.setText(projeto.getDescricao());
                dataInicioField.setValue(projeto.getDataInicio());
                dataTerminoField.setValue(projeto.getDataTermino());
                statusField.setValue(projeto.getStatus());
            }

            if(nomeLabel != null){ //Verifica se está na tela de detalhes
                nomeLabel.setText("Nome do Projeto: " + projeto.getNome());
                descricaoLabel.setText("Descrição: " + projeto.getDescricao());
                dataInicioLabel.setText("Data de Início: " + projeto.getDataInicio().toString());
                dataTerminoLabel.setText("Data de Término: " + projeto.getDataTermino().toString());
                statusLabel.setText("Status: " + projeto.getStatus());
            }
        } else{
            //Modo de criação
            // Carregar os usuários no ComboBox de responsáveis
            carregarUsuarios();
            carregarStatus();
        }
    }


    private void carregarUsuarios() {
        try {
            ObservableList<Usuario> usuarios = FXCollections.observableArrayList(usuarioDAO.listarUsuarios());
            responsaveisField.setItems(usuarios); // Aqui estava o erro!
            //Para exibir o nome dos usuários
            responsaveisField.setCellFactory(lv -> new javafx.scene.control.ListCell<Usuario>() {
                @Override
                protected void updateItem(Usuario item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.getNome());
                }
            });
            //Para quando selecionar um usuário, mostrar o nome
            responsaveisField.setButtonCell(new javafx.scene.control.ListCell<Usuario>() {
                @Override
                protected void updateItem(Usuario item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.getNome());
                }
            });
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Erro", "Erro ao carregar usuários: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void carregarStatus(){

        ObservableList<String> status = FXCollections.observableArrayList("Dentro do Prazo", "Atrasado", "Concluído");
        statusField.setItems(status);
    }


    @FXML
    private void handleSalvar() {
        // 1. Obter os valores dos campos
        String nome = nomeField.getText();
        String descricao = descricaoField.getText();
        LocalDate dataInicio = dataInicioField.getValue();
        LocalDate dataTermino = dataTerminoField.getValue();
        Usuario responsavel = responsaveisField.getValue(); // Obter o usuário selecionado
        String status = statusField.getValue();

        // 2. Validação (MUITO IMPORTANTE!)
        if (nome == null || nome.trim().isEmpty() ||
                descricao == null || descricao.trim().isEmpty() ||
                dataInicio == null ||
                dataTermino == null ||
                responsavel == null||
                status == null) {
            showAlert(AlertType.ERROR, "Erro", "Preencha todos os campos obrigatórios.");
            return;
        }

        if (dataInicio.isAfter(dataTermino)) {
            showAlert(AlertType.ERROR, "Erro", "A data de início não pode ser posterior à data de término.");
            return;
        }

        // 3. Criar ou atualizar o objeto Projeto
        if(this.projeto == null) { //CRIAR
            Projeto novoProjeto = new Projeto();
            novoProjeto.setNome(nome);
            novoProjeto.setDescricao(descricao);
            novoProjeto.setDataInicio(dataInicio);
            novoProjeto.setDataTermino(dataTermino);
            novoProjeto.setStatus(status);

            // 4. Chamar o DAO para salvar
            try {
                projetoDAO.adicionarProjeto(novoProjeto);
                showAlert(AlertType.INFORMATION, "Sucesso", "Projeto criado com sucesso!");

                // 5. Fechar a tela (opcional)
                Stage stage = (Stage) nomeField.getScene().getWindow();
                stage.close();

            } catch (SQLException e) {
                showAlert(AlertType.ERROR, "Erro", "Erro ao salvar o projeto: " + e.getMessage());
                e.printStackTrace();
            }
        } else{ //EDITAR
            try{
                this.projeto.setNome(nome);
                this.projeto.setDescricao(descricao);
                this.projeto.setDataInicio(dataInicio);
                this.projeto.setDataTermino(dataTermino);
                this.projeto.setStatus(status);
                projetoDAO.atualizarProjeto(this.projeto);
                showAlert(AlertType.INFORMATION, "Sucesso", "Projeto atualizado com sucesso!");
                Stage stage = (Stage) nomeField.getScene().getWindow();
                stage.close(); //Fecha a tela após a edição
            } catch(SQLException e){
                showAlert(AlertType.ERROR, "Erro", "Erro ao atualizar o projeto: "+ e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleCancelar() {
        // Fechar a janela
        Stage stage = (Stage) nomeField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}