package com.example.mobile_athleta.service;

import com.example.mobile_athleta.models.Esporte;
import java.util.List;

public class EsporteResponse {
    private boolean responseSucessfull;
    private String description;
    private List<Esporte> object;
    private String aditionalInformation;

    public EsporteResponse(boolean responseSucessfull, String description, List<Esporte> object, String aditionalInformation) {
        this.responseSucessfull = responseSucessfull;
        this.description = description;
        this.object = object;
        this.aditionalInformation = aditionalInformation;
    }

    public List<Esporte> getEsportes() {
        return object;
    }
}
