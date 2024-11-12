package com.example.mobile_athleta.service;

public class RelacionamentoForum {
    private Long idRelacionamento;
    private Long idForum;
    private Long idUsuario;

    public RelacionamentoForum(Long idRelacionamento, Long idForum, Long idUsuario) {
        this.idRelacionamento = idRelacionamento;
        this.idForum = idForum;
        this.idUsuario = idUsuario;
    }

    public Long getIdRelacionamento() {
        return idRelacionamento;
    }

    public void setIdRelacionamento(Long idRelacionamento) {
        this.idRelacionamento = idRelacionamento;
    }

    public Long getIdForum() {
        return idForum;
    }

    public void setIdForum(Long idForum) {
        this.idForum = idForum;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
