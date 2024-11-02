package com.example.mobile_athleta.models;

import java.util.Date;
import java.util.List;

public class Post {
    private String id;
    private String legenda;
    private String imagem;
    private String username;
    private String userFoto;
    private List<String> curtidas;
    private List<String> compartilhamento;
    private String usuarioId;
    private Date data;
    private boolean liked;

    // getters e setters


    public Post() {}

    public Post(String id, String legenda, String imagem, String username, String userFoto, List<String> curtidas, List<String> compartilhamento, String usuarioId, Date data, boolean liked) {
        this.id = id;
        this.legenda = legenda;
        this.imagem = imagem;
        this.username = username;
        this.userFoto = userFoto;
        this.curtidas = curtidas;
        this.compartilhamento = compartilhamento;
        this.usuarioId = usuarioId;
        this.data = data;
        this.liked = liked;
    }

    public String getId() {
        return id;
    }

    public String getLegenda() {
        return legenda;
    }

    public String getImagem() {
        return imagem;
    }

    public String getUsername() {
        return username;
    }

    public String getUserFoto() {
        return userFoto;
    }

    public List<String> getCurtidas() {
        return curtidas;
    }

    public List<String> getCompartilhamento() {
        return compartilhamento;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public Date getData() {
        return data;
    }
    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
