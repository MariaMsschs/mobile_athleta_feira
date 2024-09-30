package com.example.mobile_athleta.models;

public class Forum {
    private int id;
    private String nome;
    private String descricao;
    private String usuario;
    private int imagem_perfil;
    private int  imagem_fundo;
    private int seguidores = 0;


    public Forum(int id, String nome, String descricao, String usuario, int imagem_perfil, int imagem_fundo, int seguidores) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.usuario = usuario;
        this.imagem_perfil = imagem_perfil;
        this.imagem_fundo = imagem_fundo;
        this.seguidores = seguidores;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getImagem_perfil() {
        return imagem_perfil;
    }

    public int getImagem_fundo() {
        return imagem_fundo;
    }

    public int getSeguidores() {
        return seguidores;
    }
}
