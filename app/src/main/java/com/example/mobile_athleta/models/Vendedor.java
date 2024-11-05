package com.example.mobile_athleta.models;

public class Vendedor {
    private Long idUsuario;
    private String cpf;
    private String endereco;
    private String cep;
    private int numero;
    private String fotoPerfil;
    private String telefone;

    public Vendedor(){
    }

    public Vendedor(Long idUsuario, String cpf, String endereco, String cep, int numero, String fotoPerfil, String telefone){
        this.idUsuario = idUsuario;
        this.cpf = cpf;
        this.endereco = endereco;
        this.cep = cep;
        this.numero = numero;
        this.fotoPerfil = fotoPerfil;
        this.telefone = telefone;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCep() {
        return cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public int getNumero() {
        return numero;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public String getTelefone() {
        return telefone;
    }
}
