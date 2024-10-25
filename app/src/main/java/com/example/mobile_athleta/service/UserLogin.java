package com.example.mobile_athleta.service;

public class UserLogin {
    private String username;
    private String senha;

    public UserLogin(String username, String senha){
        this.username = username;
        this.senha = senha;
    }
    public String getSenha(){
        return senha;
    }

    public String getUsername(){
        return username;
    }
}
