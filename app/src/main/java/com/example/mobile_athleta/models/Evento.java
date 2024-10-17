package com.example.mobile_athleta.models;

import java.util.Date;

public class Evento {
    private int idEvento;

    private String nome;

    private String descricao;

    private Date dtEvento;

    private String img;

    private int organizador;

    public Evento(int idEvento, String nome, String descricao, Date dtEvento, String img) {
        this.idEvento = idEvento;
        this.nome = nome;
        this.descricao = descricao;
        this.dtEvento = dtEvento;
        this.img = img;
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

    public Date getDtEvento() {
        return dtEvento;
    }

    public String getImg() {
        return img;
    }

    public int getOrganizador() {
        return organizador;
    }
}
