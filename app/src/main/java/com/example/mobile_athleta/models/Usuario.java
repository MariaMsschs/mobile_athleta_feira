package com.example.mobile_athleta.models;

import java.util.Date;

public class Usuario {
    private Long idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String dtNasc;
    private String username;
    private String fotoPerfil;

    public Usuario() {

    }
    public Usuario(Long idUsuario, String nome, String email, String senha, String dtNasc, String username, String fotoPerfil) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dtNasc = dtNasc;
        this.username = username;
        this.fotoPerfil = fotoPerfil;
    }

    public Usuario(String nome, String email, String dtNasc, String username, String fotoPerfil) {
        this.nome = nome;
        this.email = email;
        this.dtNasc = dtNasc;
        this.username = username;
        this.fotoPerfil = fotoPerfil;
    }
    public Usuario(String nome, String email, String senha, String dtNasc, String username, String fotoPerfil) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dtNasc = dtNasc;
        this.username = username;
        this.fotoPerfil = fotoPerfil;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getDtNasc() {
        return dtNasc;
    }

    public String getUsername() {
        return username;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }
}
