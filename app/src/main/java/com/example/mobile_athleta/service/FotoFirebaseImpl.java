package com.example.mobile_athleta.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import androidx.core.content.FileProvider;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
    public void uploadImageForum(Uri imagem, String chave, Context context){
        String username = context.getSharedPreferences("login", context.MODE_PRIVATE).getString("username","");
        String caminho = ("users/" + username +"/foruns/"+ System.currentTimeMillis());
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(caminho);
        context.getSharedPreferences("forum", context.MODE_PRIVATE).edit().putString(chave, caminho).apply();
        storageReference.putFile(imagem).addOnSuccessListener(taskSnapshot -> {
            Log.d("SUCESSO IMAGE ESPORTE UPLOAD", "Imagem enviada");
        });
    }

    @Override
    public void trocarFoto(Uri imagem, Context context){
        String username = context.getSharedPreferences("login", context.MODE_PRIVATE).getString("username","");
        String caminho = ("users/" + username +"/"+ System.currentTimeMillis());
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(caminho);
        context.getSharedPreferences("login", context.MODE_PRIVATE).edit().putString("caminho", caminho).apply();
        storageReference.putFile(imagem).addOnSuccessListener(taskSnapshot -> {
            Log.d("SUCESSO TROCAR FOTO", "Imagem enviada");
        });
    }

    @Override
    public void recuperarImagem(ImageView imageView, String caminho){
        if(caminho != null){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(caminho);
            storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                int rotateImage = getCameraPhotoOrientation(imageView.getContext(), uri, caminho);
                Picasso.get()
                        .load(uri)
                        .transform(new RotateTransformation(rotateImage))
                        .into(imageView);
            }).addOnFailureListener(exception -> {
                Log.d("ERRO RECUPERAR IMAGEM", exception.getMessage());
            });
        }
        else{
            Log.d("ERRO RECUPERAR IMAGEM", "Caminho vazio");
        }
    }

    public File createFileFromUri(Context context, Uri uri) throws Exception {
        // Cria um arquivo temporário no diretório cache
        File file = new File(context.getCacheDir(), "temp_image_" + System.currentTimeMillis() + ".jpg");

        try (InputStream inputStream = context.getContentResolver().openInputStream(uri);
             OutputStream outputStream = new FileOutputStream(file)) {

            // Lê os dados da Uri e escreve no arquivo
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

        } catch (Exception e) {
            throw new Exception("Erro ao criar arquivo a partir da URI", e);
        }

        return file;
    }

    public int getCameraPhotoOrientation(Context context, Uri imageUri,
                                         String imagePath) {
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                Log.e("RotateImage", "Arquivo de imagem não encontrado: " + imagePath);
                return rotate;
            }
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            Log.i("RotateImage", "Exif orientation: " + orientation);
            Log.i("RotateImage", "Rotate value: " + rotate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }
      
    public void deletarFoto(String caminhoFoto) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference fotoRef = storage.getReference().child(caminhoFoto);

        fotoRef.delete().addOnSuccessListener(aVoid -> {
            Log.d("FirebaseStorage", "Foto deletada com sucesso: " + caminhoFoto);
        }).addOnFailureListener(exception -> {
            Log.e("FirebaseStorage", "Erro ao deletar foto: " + exception.getMessage());
        });
      
    }
}