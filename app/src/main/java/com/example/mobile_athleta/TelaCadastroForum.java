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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobile_athleta.databinding.ActivityTelaCadastroForumBinding;
import com.example.mobile_athleta.service.FotoFirebaseImpl;

public class TelaCadastroForum extends AppCompatActivity {
    private ActivityTelaCadastroForumBinding binding;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri imageUri;

    private ImageView imagemBinding;
    private EditText nomeEdit;
    private EditText descricaoEdit;
    String imagemPerfil;
    String imagemHeader;

    private FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaCadastroForumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nomeEdit = findViewById(R.id.nome_forum);
        descricaoEdit = findViewById(R.id.descricao_forum);

        binding.botaoVoltar.setOnClickListener(v -> {
            finish();
        });

        binding.imagemPerfilForum.setOnClickListener(view -> {
            imagemBinding = binding.imagemPerfilForum;
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

        binding.imagemHeaderForum.setOnClickListener(view -> {
            imagemBinding = binding.imagemHeaderForum;
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

        binding.cadastroForum.setOnClickListener(v -> {
            checkAllFields();

            if(nomeEdit.getError() == null && descricaoEdit.getError() == null) {

                binding.frameLayoutForum.setVisibility(ProgressBar.VISIBLE);

                String nome = binding.nomeForum.getText().toString();
                String descricao = binding.descricaoForum.getText().toString();
                Long idUsuario = getSharedPreferences("login", MODE_PRIVATE).getLong("idUsuario", 0L);
                imagemHeader = getSharedPreferences("forum", MODE_PRIVATE).getString("header", "");
                imagemPerfil = getSharedPreferences("forum", MODE_PRIVATE).getString("perfil", "");

                Bundle bundle = new Bundle();

                bundle.putString("tela", "forum");
                bundle.putString("nome", nome);
                bundle.putString("descricao", descricao);
                bundle.putString("imagemPerfil", imagemPerfil);
                bundle.putString("imagemHeader", imagemHeader);
                bundle.putLong("idUsuario", idUsuario);

                Intent pagamento = new Intent(this, TelaPagamento.class);
                pagamento.putExtras(bundle);
                startActivity(pagamento);
            }
            else{
                Toast.makeText(this, "Existe um campo vazio!", Toast.LENGTH_SHORT).show();
            }
        });

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
                        .into(imagemBinding);

                if(imagemBinding == binding.imagemHeaderForum) {
                    binding.cadastroForum.setBackgroundResource(R.drawable.button_design);
                    binding.cadastroForum.setTextColor(getResources().getColor(R.color.white));
                    fotoFirebaseImpl.uploadImageForum(imageUri, "header",this);
                }
                else{
                    fotoFirebaseImpl.uploadImageForum(imageUri, "perfil",this);
                }
            }
        }
    }

    private final ActivityResultLauncher<Intent> resultLauncherGaleria = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    Glide.with(this)
                            .load(imageUri)
                            .into(imagemBinding);

                    if(imagemBinding == binding.imagemHeaderForum) {
                        binding.cadastroForum.setBackgroundResource(R.drawable.button_design);
                        binding.cadastroForum.setTextColor(getResources().getColor(R.color.white));
                        fotoFirebaseImpl.uploadImageForum(imageUri, "header",this);
                    }
                    else{
                        fotoFirebaseImpl.uploadImageForum(imageUri, "perfil",this);
                    }
                }
            }
    );

    public void checkAllFields() {
        if(nomeEdit.getText().toString().trim().isEmpty()){
            nomeEdit.setError("Este campo é obrigatório");
        } if (descricaoEdit.getText().toString().trim().isEmpty()) {
            descricaoEdit.setError("Este campo é obrigatório");
        }
    }
}