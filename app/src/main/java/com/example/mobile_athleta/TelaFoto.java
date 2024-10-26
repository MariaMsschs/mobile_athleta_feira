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
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.bumptech.glide.Glide;
import com.example.mobile_athleta.UseCase.CadastrarUsuarioUseCase;
import com.example.mobile_athleta.databinding.ActivityTelaFotoBinding;
import com.example.mobile_athleta.models.Usuario;
import com.example.mobile_athleta.service.FotoFirebaseImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaFoto extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ActivityTelaFotoBinding binding;
    private Uri imageUri;
    private FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();
    private CadastrarUsuarioUseCase cadastrarUsuarioUseCase = new CadastrarUsuarioUseCase();

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

        binding.botaoCadastro.setOnClickListener(v -> {
            binding.frameLayoutFoto.setVisibility(ProgressBar.VISIBLE);
            Bundle bundle = getIntent().getExtras();
            cadastrarUsuario(bundle);
            binding.frameLayoutFoto.setVisibility(ProgressBar.GONE);
            startActivity(new Intent(TelaFoto.this, TelaHome.class));
            finish();
        });
    }

    private void cadastrarUsuario(Bundle bundle){
        String data =  converterData(bundle.getString("dataNasc"));

        String caminho = getSharedPreferences("fotoPerfil", MODE_PRIVATE).getString("caminho_imagem","");

        Usuario usuario = new Usuario(bundle.getString("nome"), bundle.getString("email"),
                bundle.getString("senha"), data,
                bundle.getString("username"), caminho);

        cadastrarUsuarioUseCase.cadastrarUsuario(usuario);
    }

    private String converterData(String dataString) {
        final SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
        final SimpleDateFormat formatoSaida = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date data = formatoEntrada.parse(dataString);
            return formatoSaida.format(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return dataString;
        }
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
                        .into(binding.camera);

                binding.botaoCadastro.setBackgroundResource(R.drawable.button_design);
                binding.botaoCadastro.setTextColor(getResources().getColor(R.color.white));
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
                            .into(binding.camera);
                    binding.botaoCadastro.setBackgroundResource(R.drawable.button_design);
                    binding.botaoCadastro.setTextColor(getResources().getColor(R.color.white));

                    fotoFirebaseImpl.uploadImage(imageUri, this);
                }
            }
    );
}
