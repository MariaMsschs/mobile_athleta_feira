package com.example.mobile_athleta.models;

public class Anuncio {
    private Long idAnuncio;
    private double preco;
    private String descricao;
    private String nome;
    private int quant;
    private Long idUsuario;
    private Long idEstado;

    private Anuncio(){
    }

    public Anuncio(double preco, String descricao, String nome, int quant){
        this.preco = preco;
        this.descricao = descricao;
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
}
