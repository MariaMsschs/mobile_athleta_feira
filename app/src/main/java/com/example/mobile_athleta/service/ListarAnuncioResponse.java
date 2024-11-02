package com.example.mobile_athleta.service;

import com.example.mobile_athleta.models.Anuncio;

import java.util.List;

public class ListarAnuncioResponse {
    private boolean responseSucessfull;
    private String description;
    private Anuncio anuncio;
    private String aditionalInformation;

    public ListarAnuncioResponse(boolean responseSucessfull, String description, Anuncio anuncio, String aditionalInformation) {
        this.responseSucessfull = responseSucessfull;
        this.description = description;
        this.anuncio = anuncio;
        this.aditionalInformation = aditionalInformation;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public String getDescription(){
        return description;
    }
}

