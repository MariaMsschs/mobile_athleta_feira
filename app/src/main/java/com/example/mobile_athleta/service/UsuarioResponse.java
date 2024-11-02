package com.example.mobile_athleta.service;

import com.example.mobile_athleta.models.Usuario;

import java.util.List;

public class UsuarioResponse {
    private boolean responseSucessfull;
    private String description;
    private Usuario usuario;
    private String aditionalInformation;

    public UsuarioResponse(boolean responseSucessfull, String description, Usuario usuario, String aditionalInformation) {
        this.responseSucessfull = responseSucessfull;
        this.description = description;
        this.usuario = usuario;
        this.aditionalInformation = aditionalInformation;
    }

    public boolean getResponseSucessfull() {
        return responseSucessfull;
    }

    public String getDescription() {
        return description;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getAditionalInformation() {
        return aditionalInformation;
    }
}
