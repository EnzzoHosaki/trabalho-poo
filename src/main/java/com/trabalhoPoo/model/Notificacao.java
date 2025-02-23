package com.trabalhoPoo.model;

public class Notificacao{
    private int id;
    private String mensagem;
    private int usuarioId;
    private boolean lida;

    //Construtores, Getters, Setters...
    public Notificacao() {}

    public Notificacao(int id, String mensagem, int usuarioId, boolean lida){
        this.id = id;
        this.mensagem = mensagem;
        this.usuarioId = usuarioId;
        this.lida = lida;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public boolean isLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }
}