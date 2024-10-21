package com.example.mobile_athleta.models;

import java.util.Date;

public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Date dtNasc;
    private String username;
    private String fotoPerfil;

    public Usuario() {

    }
    public Usuario(Long id, String nome, String email, String senha, Date dtNasc, String username, String fotoPerfil) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dtNasc = dtNasc;
        this.username = username;
        this.fotoPerfil = fotoPerfil;
    }

    public Usuario(String nome, String email, Date dtNasc, String username) {
        this.nome = nome;
        this.email = email;
        this.dtNasc = dtNasc;
        this.username = username;
    }
    public Usuario(String nome, String email, String senha, Date dtNasc, String username, String fotoPerfil) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dtNasc = dtNasc;
        this.username = username;
    }

    public Long getId() {
        return id;
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

    public Date getDtNasc() {
        return dtNasc;
    }

    public String getUsername() {
        return username;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }
}
