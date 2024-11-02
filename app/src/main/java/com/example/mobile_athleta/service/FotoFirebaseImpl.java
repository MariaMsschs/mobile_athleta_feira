package com.example.mobile_athleta.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import androidx.core.content.FileProvider;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.squareup.picasso.Picasso;

public class FotoFirebaseImpl implements FotoFirebase {
    @Override
    public Uri recuperarImageUri(Context context, Bitmap bitmap) {
        File cachePath = new File(context.getCacheDir(), "images");
        cachePath.mkdirs();

        FileOutputStream stream = null;
        try {
            File imageFile = new File(cachePath, "image.png");
            stream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.flush();
            stream.close();

            return FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", imageFile);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void uploadImage(Uri imagem, Context context){
        String username = context.getSharedPreferences("login", context.MODE_PRIVATE).getString("username","");
        String caminho = ("users/" + username +"/"+ System.currentTimeMillis());
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(caminho);
        context.getSharedPreferences("foto", context.MODE_PRIVATE).edit().putString("caminho_imagem", caminho).apply();
        storageReference.putFile(imagem).addOnSuccessListener(taskSnapshot -> {
            Log.d("SUCESSO IMAGE UPLOAD", "Imagem enviada");
        });
    }

    @Override
    public void recuperarImagem(ImageView imageView, String caminho){
        if(caminho != null){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(caminho);
            storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                Picasso.get()
                        .load(uri)
                        .into(imageView);
            }).addOnFailureListener(exception -> {
                Log.d("ERRO RECUPERAR IMAGEM", exception.getMessage());
            });
        }
        else{
            Log.d("ERRO RECUPERAR IMAGEM", "Caminho vazio");
        }
    }
}