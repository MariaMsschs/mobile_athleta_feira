package com.example.mobile_athleta.service;

import java.util.List;

public class RelacionamentoForumResponse {
    private boolean responseSucessfull;
    private String description;
    private List<RelacionamentoForum> object;
    private String aditionalInformation;

    public RelacionamentoForumResponse(boolean responseSucessfull, String description, List<RelacionamentoForum> object, String aditionalInformation) {
        this.responseSucessfull = responseSucessfull;
        this.description = description;
        this.object = object;
        this.aditionalInformation = aditionalInformation;
    }

    public boolean isResponseSucessfull() {
        return responseSucessfull;
    }

    public void setResponseSucessfull(boolean responseSucessfull) {
        this.responseSucessfull = responseSucessfull;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RelacionamentoForum> getObject() {
        return object;
    }

    public void setObject(List<RelacionamentoForum> object) {
        this.object = object;
    }

    public String getAditionalInformation() {
        return aditionalInformation;
    }

    public void setAditionalInformation(String aditionalInformation) {
        this.aditionalInformation = aditionalInformation;
    }
}
