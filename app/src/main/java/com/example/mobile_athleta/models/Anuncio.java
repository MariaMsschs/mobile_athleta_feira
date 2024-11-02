package com.example.mobile_athleta.models;

public class Anuncio {
    private Long idAnuncio;
    private String nome;
    private String descricao;
    private String img;
    private double preco;
    private int quant;
    private Long idUsuario;
    private Long idEstado;

    private Anuncio(){
    }

    public Anuncio(String nome, String descricao, double preco, int quant, String img, Long idUsuario, Long idEstado){
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quant = quant;
        this.img = img;
        this.idUsuario = idUsuario;
        this.idEstado = idEstado;
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
        return img;
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
