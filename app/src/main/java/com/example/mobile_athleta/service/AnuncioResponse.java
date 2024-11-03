package com.example.mobile_athleta.service;

import com.example.mobile_athleta.models.Anuncio;

import java.util.List;

public class AnuncioResponse {

    private boolean responseSucessfull;
    private String description;
    private List<Anuncio> object;
    private String aditionalInformation;

    public AnuncioResponse(boolean responseSucessfull, String description, List<Anuncio> object, String aditionalInformation) {
        this.responseSucessfull = responseSucessfull;
        this.description = description;
        this.object = object;
        this.aditionalInformation = aditionalInformation;
    }

    public List<Anuncio> getAnuncios() {
        return object;
    }
}

