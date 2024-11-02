package com.example.mobile_athleta.models;

import java.util.Date;

public class Comentario {
    private String id;

    private String idPostReferente;
    private String idUsuario;
    private String username;
    private String userFoto;

    private String idUsuarioReferente;

    private String texto;

    private Date data;

    public Comentario(String id, String idPostReferente, String idUsuario, String username, String userFoto, String idUsuarioReferente, String texto, Date data) {
        this.id = id;
        this.idPostReferente = idPostReferente;
        this.idUsuario = idUsuario;
        this.username = username;
        this.userFoto = userFoto;
        this.idUsuarioReferente = idUsuarioReferente;
        this.texto = texto;
        this.data = data;
    }

    public Comentario() {}

    public String getId() {
        return id;
    }

    public String getIdPostReferente() {
        return idPostReferente;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public String getUserFoto() {
        return userFoto;
    }

    public String getIdUsuarioReferente() {
        return idUsuarioReferente;
    }

    public String getTexto() {
        return texto;
    }

    public Date getData() {
        return data;
    }
}
