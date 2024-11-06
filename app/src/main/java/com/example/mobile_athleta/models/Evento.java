package com.example.mobile_athleta.models;

import java.util.Date;

public class Evento {
    private int idEvento;

    private String nome;

    private String descricao;

    private String dtEvento;

    private String img;

    private int organizador;

    private Long idLocal;
    public Evento(int idEvento, String nome, String descricao, String dtEvento, String img, int organizador, Long idLocal) {
        this.idEvento = idEvento;
        this.nome = nome;
        this.descricao = descricao;
        this.dtEvento = dtEvento;
        this.img = img;
        this.organizador = organizador;
        this.idLocal = idLocal;
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

    public int getOrganizador() {
        return organizador;
    }

    public Long getIdLocal() {
        return idLocal;
    }
}
