package com.example.mobile_athleta.models;

public class Esporte {
    private String title;
    private String description;
    private String imageResource;

    public Esporte(String title, String description, String imageResource) {
        this.title = title;
        this.description = description;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImagem() {
        return imageResource;
    }
}
