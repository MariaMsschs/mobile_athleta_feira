package com.example.mobile_athleta.models;

public class Notificacao {
    private int id;
    private int imagemNotificacao;
    private String descricao;
    private String data;

    public Notificacao(int id, int imagemNotificacao, String descricao, String data) {
        this.id = id;
        this.imagemNotificacao = imagemNotificacao;
        this.descricao = descricao;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public int getImagemNotificacao() {
        return imagemNotificacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getData() {
        return data;
    }
}
