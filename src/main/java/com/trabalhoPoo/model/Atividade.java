package com.trabalhoPoo.model;

import java.time.LocalDate;
import java.util.List;

public class Atividade {
    private int id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private String status;
    private int porcentagemConclusao;
    private int projetoId; // FK para Projeto
    private String justificativa;

    public Atividade() {
    }

    public Atividade(int id, String nome, String descricao, LocalDate dataInicio, LocalDate dataTermino,
                     String status, int porcentagemConclusao, int projetoId, String justificativa) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.status = status;
        this.porcentagemConclusao = porcentagemConclusao;
        this.projetoId = projetoId;
        this.justificativa = justificativa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPorcentagemConclusao() {
        return porcentagemConclusao;
    }

    public void setPorcentagemConclusao(int porcentagemConclusao) {
        this.porcentagemConclusao = porcentagemConclusao;
    }

    public int getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(int projetoId) {
        this.projetoId = projetoId;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public String getStatus() {
        LocalDate hoje = LocalDate.now();
        if (this.status != null && this.status.equals("Concluído")) {
            return "Concluído";
        } else if (dataTermino.isBefore(hoje)) {
            return "Atrasado";
        } else {
            return "Dentro do Prazo";
        }
    }
}