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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.example.mobile_athleta.UseCase.CadastroVendedorUseCase;
import com.example.mobile_athleta.databinding.ActivityTelaCadastroVendedorBinding;
import com.example.mobile_athleta.models.Vendedor;
import com.example.mobile_athleta.service.FotoFirebaseImpl;

public class TelaCadastroVendedor extends AppCompatActivity {
    private ActivityTelaCadastroVendedorBinding binding;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri imageUri;
    private FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();
    private EditText cpfEdit;
    private EditText telefoneEdit;
    private EditText enderecoEdit;
    private EditText cepEdit;
    private EditText numeroEdit;
    private ImageButton imagemRosto;
    private FrameLayout frameLayout;
    private CadastroVendedorUseCase cadastroVendedorUseCase = new CadastroVendedorUseCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaCadastroVendedorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cpfEdit = findViewById(R.id.cpf);
        telefoneEdit = findViewById(R.id.telefone);
        enderecoEdit = findViewById(R.id.endereco);
        cepEdit = findViewById(R.id.cep);
        numeroEdit = findViewById(R.id.numero);
        imagemRosto = findViewById(R.id.imagem_rosto);

        frameLayout = findViewById(R.id.frameLayoutAnuncio);
        frameLayout.setVisibility(ProgressBar.GONE);

        binding.botaoVoltar.setOnClickListener(v -> {
            finish();
        });

        binding.imagemRosto.setOnClickListener(view -> {
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

        binding.cadastroVendedor.setOnClickListener(v -> {
            checkAllFields();
            if (camposValidos()) {
                frameLayout.setVisibility(ProgressBar.VISIBLE);

                String foto = getSharedPreferences("foto", MODE_PRIVATE).getString("caminho_imagem", "");
                int numero = Integer.parseInt(numeroEdit.getText().toString().trim());

                String token = getSharedPreferences("login", MODE_PRIVATE).getString("token", "");
                Long idUsuario = getSharedPreferences("login", MODE_PRIVATE).getLong("idUsuario", 0L);

                Vendedor vendedor = new Vendedor(idUsuario, cpfEdit.getText().toString(), enderecoEdit.getText().toString(),
                        cepEdit.getText().toString(), numero, foto, telefoneEdit.getText().toString());

                Log.d("params", foto + " " + token);
                Log.d("prama", String.valueOf(numero) + " " + String.valueOf(idUsuario));
                Log.d("PARAME", cpfEdit.getText().toString() + " " + enderecoEdit.getText().toString());
                Log.d("PARAMS", cepEdit.getText().toString() + " " + telefoneEdit.getText().toString());

                cadastroVendedorUseCase.cadastrarVendedor(token, vendedor, new CadastroVendedorUseCase.CadastroVendedorCallBack() {
                    @Override
                    public void onCadastroVendedorSuccess() {
                        Intent anuncio = new Intent(TelaCadastroVendedor.this, TelaCadastroAnuncio.class);
                        frameLayout.setVisibility(ProgressBar.GONE);
                        startActivity(anuncio);
                        finish();
                    }

                    @Override
                    public void onCadastroVendedorErro() {
                        frameLayout.setVisibility(ProgressBar.GONE);

                        LayoutInflater inflater = getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.erro_alterar_dialog, null);
                        AlertDialog.Builder builder = new AlertDialog.Builder(TelaCadastroVendedor.this);
                        View botao = dialogView.findViewById(R.id.botao_erro_alterar);
                        builder.setView(dialogView);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        botao.setOnClickListener(v2 -> {
                            Intent config = new Intent(TelaCadastroVendedor.this, TelaCadastroVendedor.class);
                            startActivity(config);
                            dialog.dismiss();
                        });
                    }
                });
            }
        });
    }

    public void checkAllFields() {
        if (cpfEdit.getText().toString().trim().isEmpty()) {
            cpfEdit.setError("Este campo é obrigatório");
        } if (telefoneEdit.getText().toString().trim().isEmpty()) {
            telefoneEdit.setError("Este campo é obrigatório");
        } if (enderecoEdit.getText().toString().trim().isEmpty()) {
            enderecoEdit.setError("Este campo é obrigatório");
        } if (cepEdit.getText().toString().trim().isEmpty()) {
            cepEdit.setError("Este campo é obrigatório");
        } if (numeroEdit.getText().toString().trim().isEmpty()) {
            numeroEdit.setError("Este campo é obrigatório");
        }
    }

    private boolean camposValidos() {
        return cpfEdit.getError() == null && telefoneEdit.getError() == null &&
                enderecoEdit.getError() == null && cepEdit.getError() == null &&
                numeroEdit.getError() == null;
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
                        .into(binding.imagemRosto);

                binding.cadastroVendedor.setBackgroundResource(R.drawable.button_design);
                binding.cadastroVendedor.setTextColor(getResources().getColor(R.color.white));
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
                            .into(binding.imagemRosto);
                    binding.cadastroVendedor.setBackgroundResource(R.drawable.button_design);
                    binding.cadastroVendedor.setTextColor(getResources().getColor(R.color.white));

                    fotoFirebaseImpl.uploadImage(imageUri, this);
                }
            }
    );
}