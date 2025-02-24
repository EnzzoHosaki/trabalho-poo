package com.trabalhoPoo.controller;

import com.trabalhoPoo.model.Projeto;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProjetoController {

    @FXML
    private Label detalhesLabel; // Adicione um Label, por exemplo, para exibir informações

    private Projeto projeto; // Variável para armazenar o projeto

    // Método para receber o projeto da tela anterior (Dashboard)
    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
        // Atualize a interface com os dados do projeto
        if (projeto != null) {
            detalhesLabel.setText("Detalhes do Projeto: " + projeto.getNome()); // Exemplo simples
            // Adicione aqui a lógica para preencher os campos da tela com os dados do projeto
        }
    }


    // Adicione outros métodos para lidar com a criação/edição/detalhes de projetos, conforme necessário.
    @FXML
    private void initialize(){

    }
}