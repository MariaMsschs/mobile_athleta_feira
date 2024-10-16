package com.example.mobile_athleta.models;

public class Produto {
    private int id;
    private String title;
    private String description;
    private double price;
    private String image;

    public Produto (int id, String title, String description, double price, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

}
