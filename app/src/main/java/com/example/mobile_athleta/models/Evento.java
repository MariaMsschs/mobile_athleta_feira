package com.example.mobile_athleta.models;

import java.util.Date;

public class Evento {
    private int idEvento;

    private String nome;

    private String descricao;

    private String dtEvento;

    private String img;

    private Long organizador;

    private Long idLocal;

    public Evento(String nome, String descricao, String dtEvento, String img, Long organizador) {
        this.nome = nome;
        this.descricao = descricao;
        this.dtEvento = dtEvento;
        this.organizador = organizador;
        this.img = img;
        this.idLocal = 1L;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDtEvento() {
        return dtEvento;
    }

    public String getImg() {
        return img;
    }

    public Long getOrganizador() {
        return organizador;
    }

    public Long getIdLocal() {
        return idLocal;
    }
}
