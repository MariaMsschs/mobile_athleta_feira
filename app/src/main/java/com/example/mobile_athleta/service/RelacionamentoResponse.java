package com.example.mobile_athleta.service;

import java.util.List;

public class RelacionamentoResponse {

    private boolean responseSucessfull;
    private String description;
    private List<RelacionamentoUsuario> object;
    private String aditionalInformation;


    public RelacionamentoResponse(boolean responseSucessfull, String description, List<RelacionamentoUsuario> object, String aditionalInformation) {
        this.responseSucessfull = responseSucessfull;
        this.description = description;
        this.object = object;
        this.aditionalInformation = aditionalInformation;
    }

    public boolean isResponseSucessfull() {
        return responseSucessfull;
    }

    public String getDescription() {
        return description;
    }

    public List<RelacionamentoUsuario> getObject() {
        return object;
    }

    public String getAditionalInformation() {
        return aditionalInformation;
    }
}
