package com.trabalhoPoo.controller;

import com.trabalhoPoo.model.Usuario; // Importante: Importar a classe Usuario
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML
    private Label welcomeLabel;

    private Usuario usuarioLogado;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        welcomeLabel.setText("Bem-vindo, " + usuario.getNome() + "!");
    }

}