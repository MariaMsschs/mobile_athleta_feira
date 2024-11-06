package com.example.mobile_athleta.service;

import com.example.mobile_athleta.models.Evento;

import java.util.List;

public class EventoResponse {
    private boolean responseSucessfull;
    private String description;
    private List<Evento> eventos;
    private String aditionalInformation;

    public EventoResponse(boolean responseSucessfull, String description, List<Evento> eventos, String aditionalInformation) {
        this.responseSucessfull = responseSucessfull;
        this.description = description;
        this.eventos = eventos;
        this.aditionalInformation = aditionalInformation;
    }

    public boolean isResponseSucessfull() {
        return responseSucessfull;
    }

    public String getDescription() {
        return description;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public String getAditionalInformation() {
        return aditionalInformation;
    }
}
