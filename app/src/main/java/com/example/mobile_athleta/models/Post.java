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
    private List<String> foruns;

    // getters e setters


    public Post() {}

    public Post(String id, String legenda, String imagem, String username, String userFoto, List<String> curtidas, List<String> compartilhamento, String usuarioId, Date data, boolean liked, List<String> foruns) {
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
        this.foruns = foruns;
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

    public List<String> getForuns() {
        return foruns;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserFoto(String userFoto) {
        this.userFoto = userFoto;
    }

    public void setCurtidas(List<String> curtidas) {
        this.curtidas = curtidas;
    }

    public void setCompartilhamento(List<String> compartilhamento) {
        this.compartilhamento = compartilhamento;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public void setForuns(List<String> foruns) {
        this.foruns = foruns;
    }
}
