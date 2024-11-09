package com.example.mobile_athleta.service;

import com.example.mobile_athleta.models.Notificacao;

import java.util.List;

public class NotificacaoResponse {
    private boolean responseSucessfull;
    private String description;
    private List<Notificacao> object;
    private String aditionalInformation;

    public NotificacaoResponse(boolean responseSucessfull, String description, List<Notificacao> object, String aditionalInformation) {
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

    public List<Notificacao> getObject() {
        return object;
    }

    public String getAditionalInformation() {
        return aditionalInformation;
    }
}
