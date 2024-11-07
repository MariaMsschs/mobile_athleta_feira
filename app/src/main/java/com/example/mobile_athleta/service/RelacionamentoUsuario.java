package com.example.mobile_athleta.service;

public class RelacionamentoUsuario {
    private Long idUsuarioSeguidor;
    private Long idUsuarioSeguido;
    private Long idRelacionamento;

    public RelacionamentoUsuario(Long idUsuarioSeguidor, Long idUsuarioSeguido) {
        this.idUsuarioSeguidor = idUsuarioSeguidor;
        this.idUsuarioSeguido = idUsuarioSeguido;
    }

    public Long getIdUsuarioSeguidor() {
        return idUsuarioSeguidor;
    }
    public void setIdUsuarioSeguidor(Long idUsuarioSeguidor) {
        this.idUsuarioSeguidor = idUsuarioSeguidor;
    }
    public Long getIdUsuarioSeguido() {
        return idUsuarioSeguido;
    }
    public void setIdUsuarioSeguido(Long idUsuarioSeguido) {
        this.idUsuarioSeguido = idUsuarioSeguido;
    }

    public Long getIdRelacionamento() {
        return idRelacionamento;
    }
}
