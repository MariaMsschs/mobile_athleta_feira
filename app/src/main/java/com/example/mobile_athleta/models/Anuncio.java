package com.example.mobile_athleta.models;

public class Anuncio {
    private Long idAnuncio;
    private String nome;
    private String descricao;
    private String imagem;
    private double preco;
    private int quant;
    private Long idUsuario;
    private Long idEstado;

    private Anuncio(){
    }

    public Anuncio( String nome, String descricao, double preco, int quant){
        this.preco = preco;
        this.descricao = descricao;
        this.nome = nome;
        this.quant = quant;
    }

    public Anuncio( String nome, String descricao, String imagem, double preco, int quant){
        this.preco = preco;
        this.descricao = descricao;
        this.imagem = imagem;
        this.nome = nome;
        this.quant = quant;
    }

    public Long getIdAnuncio() {
        return idAnuncio;
    }

    public double getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public String getNome() {
        return nome;
    }

    public int getQuant() {
        return quant;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public Anuncio getAnuncio() {
        return this;
    }
}
