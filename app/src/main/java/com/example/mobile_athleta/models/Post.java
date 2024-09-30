package com.example.mobile_athleta.models;

public class Post {
    private int id;
    private String legenda;
    private int imagem;
    private String usuario;

    private int usuario_perfil;

    public Post(int id, String legenda, int imagem, String usuario, int usuario_perfil) {
        this.id = id;
        this.legenda = legenda;
        this.imagem = imagem;
        this.usuario = usuario;
        this.usuario_perfil = usuario_perfil;
    }

    public int getId() {
        return id;
    }

    public String getLegenda() {
        return legenda;
    }

    public int getImagem() {
        return imagem;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getUsuario_perfil(){
        return usuario_perfil;
    }
}
