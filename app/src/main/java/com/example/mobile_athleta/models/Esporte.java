package com.example.mobile_athleta.models;

public class Esporte {
    private Long idEsporte;
    private String nome;
    private String descricao;
    private String comoPratica;
    private String imagem;

    public Esporte(String nome, String descricao, String comoPratica, String imagem) {
        this.nome = nome;
        this.descricao = descricao;
        this.comoPratica = comoPratica;
        this.imagem = imagem;
    }

    public Long getIdEsporte(){
        return idEsporte;
    }
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
        return imagem;
    }
}
