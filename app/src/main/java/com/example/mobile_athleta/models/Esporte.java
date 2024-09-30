package com.example.mobile_athleta.models;

public class Esporte {
    private String title;
    private String description;
    private int imageResource;

    public Esporte(String title, String description, int imageResource) {
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

    public int getImageResource() {
        return imageResource;
    }
}
