package com.example.mobile_athleta.models;

public class Forum {
    private int idForum;
    private String nome;
    private String descricao;
    private String  imgFundo;
    private Long usuarioResp;
    private String imgForum;
    private int seguidores = 0;


    public Forum(int id, String nome, String descricao, String imagem_perfil, String imagem_fundo, Long usuarioResp, int seguidores) {
        this.idForum = id;
        this.nome = nome;
        this.descricao = descricao;
        this.imgFundo = imagem_fundo;
        this.usuarioResp = usuarioResp;
        this.imgForum = imagem_perfil;
        this.seguidores = seguidores;
    }
    public Forum(String nome, String descricao, String imagem_perfil, String imagem_fundo, Long usuarioResp) {
        this.nome = nome;
        this.descricao = descricao;
        this.imgForum = imagem_perfil;
        this.imgFundo = imagem_fundo;
        this.usuarioResp = usuarioResp;
        this.seguidores = 0;
    }

    public Forum() {
    }

    public int getId() {
        return idForum;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getUsuarioResp() {
        return usuarioResp;
    }

    public String getImgForum() {
        return imgForum;
    }

    public String getImgFundo() {
        return imgFundo;
    }

    public int getSeguidores() {
        return seguidores;
    }
}
