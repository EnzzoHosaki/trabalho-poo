package com.trabalhoPoo.model;

public class Responsavel {
    private int usuarioId;
    private Integer projetoId;
    private Integer atividadeId;

    public Responsavel() {}

    public Responsavel(int usuarioId, Integer projetoId, Integer atividadeId){
        this.usuarioId = usuarioId;
        this.projetoId = projetoId;
        this.atividadeId = atividadeId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(Integer projetoId) {
        this.projetoId = projetoId;
    }

    public Integer getAtividadeId() {
        return atividadeId;
    }

    public void setAtividadeId(Integer atividadeId) {
        this.atividadeId = atividadeId;
    }
}