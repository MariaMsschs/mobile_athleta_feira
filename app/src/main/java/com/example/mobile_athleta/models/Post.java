package com.example.mobile_athleta.models;

public class Post {
    private int id;
    private String legenda;
    private String imagem;
    private String usuario;

    private String usuario_perfil;

    public Post(int id, String legenda, String imagem, String usuario, String usuario_perfil) {
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

    public String getImagem() {
        return imagem;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getUsuarioPerfil(){
        return usuario_perfil;
    }
}
