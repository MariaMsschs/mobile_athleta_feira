package com.example.mobile_athleta.models;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.util.Date;

public class Notificacao {
    private Long idNotificacao;
    private String tipo;
    private Date dtNotificacao;
    private String conteudo;
    private Long idUsuario;
    private int imagemNotificacao;

    public Notificacao(Long idNotificacao, String tipo, Date dtNotificacao, String conteudo, Long idUsuario) {
        this.idNotificacao = idNotificacao;
        this.tipo = tipo;
        this.dtNotificacao = dtNotificacao;
        this.conteudo = conteudo;
        this.idUsuario = idUsuario;
    }

    public Notificacao(Long idNotificacao, String tipo, Date dtNotificacao, String conteudo, Long idUsuario, int imagemNotificacao) {
        this.idNotificacao = idNotificacao;
        this.tipo = tipo;
        this.dtNotificacao = dtNotificacao;
        this.conteudo = conteudo;
        this.idUsuario = idUsuario;
        this.imagemNotificacao = imagemNotificacao;
    }

    public Long getIdNotificacao() {
        return idNotificacao;
    }

    public String getTipo() {
        return tipo;
    }

    public Date getDtNotificacao() {
        return dtNotificacao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public int getImagemNotificacao() {
        return imagemNotificacao;
    }

    public void setImagemNotificacao(int imagemNotificacao) {
        this.imagemNotificacao = imagemNotificacao;
    }
}
