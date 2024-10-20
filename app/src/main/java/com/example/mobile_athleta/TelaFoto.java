package com.example.mobile_athleta;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.example.mobile_athleta.databinding.ActivityTelaFotoBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TelaFoto extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ActivityTelaFotoBinding binding;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaFotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.frameLayoutFoto.setVisibility(ProgressBar.GONE);

        binding.botaoVoltar.setOnClickListener(view -> {
            Intent intent = new Intent(TelaFoto.this, TelaCadastro.class);
            startActivity(intent);
            finish();
        });
        binding.camera.setOnClickListener(view -> {

            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.alert_dialog, null);
            View tirarFoto = dialogView.findViewById(R.id.botao_ok);
            View abrirGaleria = dialogView.findViewById(R.id.abrir_galeria);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView);
            AlertDialog dialog = builder.create();
            dialog.show();

            tirarFoto.setOnClickListener(v -> {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (camera.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(camera, REQUEST_IMAGE_CAPTURE);
                }
                dialog.dismiss();
            });
            abrirGaleria.setOnClickListener(v -> {
                Intent galeria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galeria.setType("image/*");
                resultLauncherGaleria.launch(galeria);
                dialog.dismiss();
            });
        });

        binding.botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.frameLayoutFoto.setVisibility(ProgressBar.VISIBLE);
                uploadImage(imageUri,getSharedPreferences("login", MODE_PRIVATE).getString("username",""));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                imageUri = getImageUri(this, imageBitmap);
                Glide.with(this)
                        .load(imageBitmap)
                        .circleCrop()
                        .into(binding.camera);
                binding.botaoCadastro.setBackgroundResource(R.drawable.button_design);
                binding.botaoCadastro.setTextColor(getResources().getColor(R.color.white));
            }
        }
    }

    private final ActivityResultLauncher<Intent> resultLauncherGaleria = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    Glide.with(this)
                            .load(imageUri)
                            .circleCrop()
                            .into(binding.camera);
                    binding.botaoCadastro.setBackgroundResource(R.drawable.button_design);
                    binding.botaoCadastro.setTextColor(getResources().getColor(R.color.white));
                }
            }
    );

    public Uri getImageUri(Context context, Bitmap bitmap) {
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


    public void uploadImage(Uri imagem, String username){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference reference = storageReference.child("users/" + username +"/"+ System.currentTimeMillis());
        reference.putFile(imagem).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                binding.frameLayoutFoto.setVisibility(ProgressBar.GONE);
                startActivity(new Intent(TelaFoto.this, TelaHome.class));
                finish();
            }
        });

    }
}