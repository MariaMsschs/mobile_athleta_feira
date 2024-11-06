package com.example.mobile_athleta.service;

import com.example.mobile_athleta.models.Forum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApiResponse {
    private boolean responseSucessfull;
    private String description;
    private List<Object> object;
    private String aditionalInformation;

    public ApiResponse(boolean responseSucessfull, String description, List<Object> object, String aditionalInformation) {
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

    public List<Object> getObject() {
        return object;
    }

    public String getAditionalInformation() {
        return aditionalInformation;
    }
}