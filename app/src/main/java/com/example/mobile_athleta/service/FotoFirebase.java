package com.example.mobile_athleta.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

public interface FotoFirebase {
    Uri recuperarImageUri(Context context, Bitmap bitmap);

    void uploadImage(Uri imagem, Context context);

    void uploadImageForum(Uri imagem, String chave, Context context);

    void trocarFoto(Uri imagem, Context context);

    void recuperarImagem(ImageView imageView, String caminho);

    void deletarFoto(String caminho);
}
