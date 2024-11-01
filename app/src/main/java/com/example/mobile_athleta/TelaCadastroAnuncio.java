package com.example.mobile_athleta;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.example.mobile_athleta.databinding.ActivityTelaCadastroAnuncioBinding;
import com.example.mobile_athleta.models.Anuncio;
import com.example.mobile_athleta.service.FotoFirebaseImpl;

public class TelaCadastroAnuncio extends AppCompatActivity {
    private ActivityTelaCadastroAnuncioBinding binding;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri imageUri;
    private FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaCadastroAnuncioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.botaoVoltar.setOnClickListener(v -> {
            finish();
        });

        binding.imagemAnuncio.setOnClickListener(view -> {
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

        String nome = binding.nomeAnuncio.getText().toString();
        String descricao = binding.descricaoAnuncio.getText().toString();
        double preco = Double.parseDouble(binding.precoAnuncio.getText().toString());
        int quant = binding.quantidadeAnuncio.getText().toString());

        binding.cadastroAnuncio.setOnClickListener(v -> {
            binding.frameLayoutAnuncio.setVisibility(ProgressBar.VISIBLE);
//            Anuncio anuncio = new Anuncio();
//            cadastrarAnuncio();
        });

    }

    public void cadastrarAnuncio(Anuncio anuncio) {
        //ok
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                imageUri = fotoFirebaseImpl.recuperarImageUri(this, imageBitmap);
                Glide.with(this)
                        .load(imageBitmap)
                        .circleCrop()
                        .into(binding.imagemAnuncio);

                binding.cadastroAnuncio.setBackgroundResource(R.drawable.button_design);
                binding.cadastroAnuncio.setTextColor(getResources().getColor(R.color.white));
                fotoFirebaseImpl.uploadImage(imageUri, this);
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
                            .into(binding.imagemAnuncio);

//                    if checke all
                    binding.cadastroAnuncio.setBackgroundResource(R.drawable.button_design);
                    binding.cadastroAnuncio.setTextColor(getResources().getColor(R.color.white));

                    fotoFirebaseImpl.uploadImage(imageUri, this);
                }
            }
    );
//    public void checkAllFields() {
//        if(nomeEdit.getText().toString().trim().isEmpty()){
//            nomeEdit.setError("Este campo é obrigatório");
//        } if (emailEdit.getText().toString().trim().isEmpty()) {
//            emailEdit.setError("Este campo é obrigatório");
//        } if(senhaEdit.getText().toString().trim().isEmpty()){
//            senhaEdit.setError("Este campo é obrigatório");
//        } if(dataNascimentoEdit.getText().toString().trim().isEmpty()){
//            dataNascimentoEdit.setError("Este campo é obrigatório");
//        } if(usernameEdit.getText().toString().trim().isEmpty()){
//            usernameEdit.setError("Este campo é obrigatório");
//        }
//    }
}