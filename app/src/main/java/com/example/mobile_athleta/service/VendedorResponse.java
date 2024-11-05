package com.example.mobile_athleta.service;

import com.example.mobile_athleta.models.Vendedor;

import java.util.List;

public class VendedorResponse {
    private boolean responseSucessfull;
    private String description;
    private List<Vendedor> object;
    private String aditionalInformation;

    public VendedorResponse(boolean responseSucessfull, String description, List<Vendedor> object, String aditionalInformation) {
        this.responseSucessfull = responseSucessfull;
        this.description = description;
        this.object = object;
        this.aditionalInformation = aditionalInformation;
    }

    public List<Vendedor> getVendedor() {
        return object;
    }
}
