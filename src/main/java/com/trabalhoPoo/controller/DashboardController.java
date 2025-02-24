package com.trabalhoPoo.controller;

import com.trabalhoPoo.dao.ProjetoDAO;
import com.trabalhoPoo.model.Projeto;
import com.trabalhoPoo.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.sql.SQLException;
import java.util.List;

public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Label totalProjetosLabel;
    @FXML private Label projetosEmAndamentoLabel;
    @FXML private Label projetosAtrasadosLabel;
    @FXML private Label projetosConcluidosLabel;

    private Usuario usuarioLogado;
    private ProjetoDAO projetoDAO = new ProjetoDAO();

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        // welcomeLabel.setText("Bem-vindo, " + usuarioLogado.getNome() + "!"); // <--  NÃO MAIS AQUI
        // carregarResumo(); // Carrega as informações de resumo // <-- NÃO AQUI TAMBÉM
    }

    private void carregarResumo() {
        try {
            List<Projeto> projetos = projetoDAO.listarProjetos();
            int total = projetos.size();
            int emAndamento = 0;
            int atrasados = 0;
            int concluidos = 0;

            for (Projeto projeto : projetos) {
                switch (projeto.getStatus()) {
                    case "Dentro do Prazo":
                        emAndamento++;
                        break;
                    case "Atrasado":
                        atrasados++;
                        break;
                    case "Concluído":
                        concluidos++;
                        break;
                }
            }

            totalProjetosLabel.setText("Total de Projetos: " + total);
            projetosEmAndamentoLabel.setText("Projetos em Andamento: " + emAndamento);
            projetosAtrasadosLabel.setText("Projetos Atrasados: " + atrasados);
            projetosConcluidosLabel.setText("Projetos Concluídos: " + concluidos);

        } catch (SQLException e) {
            // Tratar a exceção (ex: exibir uma mensagem de erro)
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize(){
        //Agora carrega os dados aqui, DEPOIS que o FXML for inicializado.
        if(usuarioLogado != null){
            welcomeLabel.setText("Bem-vindo, " + usuarioLogado.getNome() + "!");
            carregarResumo();
        }

    }
}