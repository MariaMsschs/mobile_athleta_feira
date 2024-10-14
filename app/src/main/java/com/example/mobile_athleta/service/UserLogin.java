package com.example.mobile_athleta.service;

public class UserLogin {
    private String email;
    private String senha;

    public UserLogin(String email, String senha){
        this.email = email;
        this.senha = senha;
    }
    public String getSenha(){
        return senha;
    }

    public String getEmail(){
        return email;
    }
}
