package com.example.mobile_athleta.models;

public class Esporte {
    private Long idEsporte;
    private String nome;
    private String descricao;
    private String comoPratica;
    private String img;

    public Esporte(String nome, String descricao, String comoPratica) {
        this.nome = nome;
        this.descricao = descricao;
        this.comoPratica = comoPratica;
    }

//    public Esporte(String nome, String descricao, String img) {
//        this.nome = nome;
//        this.descricao = descricao;
//        this.img = img;
//    }

    public String getNome() {
        return nome;
    }

    public String getComoPratica(){
        return comoPratica;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getImagem() {
        return img;
    }
}
