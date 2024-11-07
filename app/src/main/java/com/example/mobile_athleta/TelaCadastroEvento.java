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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobile_athleta.databinding.ActivityTelaCadastroEventoBinding;
import com.example.mobile_athleta.databinding.ActivityTelaCadastroForumBinding;
import com.example.mobile_athleta.service.FotoFirebaseImpl;
import com.example.mobile_athleta.service.ValidacaoCadastroImpl;

public class TelaCadastroEvento extends AppCompatActivity {
    private ActivityTelaCadastroEventoBinding binding;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri imageUri;

    private EditText nomeEdit;
    private EditText descricaoEdit;
    private EditText dataNascimentoEdit;

    private FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();
    private ValidacaoCadastroImpl validacaoCadastroImpl = new ValidacaoCadastroImpl();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaCadastroEventoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nomeEdit = findViewById(R.id.nome_evento);
        descricaoEdit = findViewById(R.id.descricao_evento);
        dataNascimentoEdit = findViewById(R.id.data_nascimento);

        binding.botaoVoltar.setOnClickListener(v -> {
            finish();
        });

        binding.imagemEvento.setOnClickListener(view -> {
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

        binding.calendario.setOnClickListener(v -> {
            validacaoCadastroImpl.definirDataEvento(dataNascimentoEdit, this);
        });

        dataNascimentoEdit.setOnClickListener(v -> {
            validacaoCadastroImpl.definirDataEvento(dataNascimentoEdit, this);
        });

        binding.cadastroEvento.setOnClickListener(v -> {
            checkAllFields();

            if(nomeEdit.getError() == null && descricaoEdit.getError() == null) {

                binding.frameLayoutEvento.setVisibility(ProgressBar.VISIBLE);

                String nome = binding.nomeEvento.getText().toString();
                String descricao = binding.descricaoEvento.getText().toString();
                String data = binding.dataNascimento.getText().toString();
                String imagem = getSharedPreferences("foto", MODE_PRIVATE).getString("caminho_imagem", "");
                Long idUsuario = getSharedPreferences("login", MODE_PRIVATE).getLong("idUsuario", 0L);

                Bundle bundle = new Bundle();

                bundle.putString("tela", "evento");
                bundle.putString("nome", nome);
                bundle.putString("descricao", descricao);
                bundle.putString("data", data);
                bundle.putString("imagem", imagem);
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
                        .into(binding.imagemEvento);

                binding.cadastroEvento.setBackgroundResource(R.drawable.button_design);
                binding.cadastroEvento.setTextColor(getResources().getColor(R.color.white));
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
                            .into(binding.imagemEvento);

                    binding.cadastroEvento.setBackgroundResource(R.drawable.button_design);
                    binding.cadastroEvento.setTextColor(getResources().getColor(R.color.white));

                    fotoFirebaseImpl.uploadImage(imageUri, this);
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