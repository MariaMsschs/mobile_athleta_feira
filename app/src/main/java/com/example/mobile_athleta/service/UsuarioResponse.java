package com.example.mobile_athleta.service;

import com.example.mobile_athleta.models.Usuario;

import java.util.List;

public class UsuarioResponse {
    private boolean responseSucessfull;
    private String description;
    private List<Usuario> object;
    private String aditionalInformation;

    public UsuarioResponse(boolean responseSucessfull, String description, List<Usuario> object, String aditionalInformation) {
        this.responseSucessfull = responseSucessfull;
        this.description = description;
        this.object = object;
        this.aditionalInformation = aditionalInformation;
    }

    public boolean getResponseSucessfull() {
        return responseSucessfull;
    }

    public String getDescription() {
        return description;
    }

    public List<Usuario> getUsuario() {
        return object;
    }

    public String getAditionalInformation() {
        return aditionalInformation;
    }
}
