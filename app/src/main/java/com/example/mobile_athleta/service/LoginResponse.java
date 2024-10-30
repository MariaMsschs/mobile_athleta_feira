package com.example.mobile_athleta.service;

import com.example.mobile_athleta.models.Usuario;

public class LoginResponse {
    private String token;
    private Usuario usuario;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
