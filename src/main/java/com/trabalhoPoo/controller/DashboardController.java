package com.trabalhoPoo.controller;

import com.trabalhoPoo.model.Usuario; // Importante: Importar a classe Usuario
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML
    private Label welcomeLabel; // Label para exibir mensagem de boas-vindas

    private Usuario usuarioLogado; // Variável para armazenar o usuário logado

    // Método para definir o usuário logado.  Este é o método que estava faltando.
    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        welcomeLabel.setText("Bem-vindo, " + usuario.getNome() + "!"); // Exibe o nome do usuário
    }

    // Você pode adicionar outros métodos e funcionalidades ao Dashboard aqui.
}