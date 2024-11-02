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
import android.widget.ProgressBar;
import com.bumptech.glide.Glide;
import com.example.mobile_athleta.UseCase.CadastrarUsuarioFireUseCase;
import com.example.mobile_athleta.UseCase.CadastrarUsuarioUseCase;
import com.example.mobile_athleta.UseCase.ListarUsuarioUseCase;
import com.example.mobile_athleta.UseCase.LoginFireUseCase;
import com.example.mobile_athleta.UseCase.LoginUseCase;
import com.example.mobile_athleta.databinding.ActivityTelaFotoBinding;
import com.example.mobile_athleta.models.Usuario;
import com.example.mobile_athleta.service.FotoFirebaseImpl;
import com.example.mobile_athleta.service.UserLogin;
import com.example.mobile_athleta.service.ValidacaoCadastroImpl;
import com.google.firebase.auth.FirebaseAuth;

public class TelaFoto extends AppCompatActivity {
    private ActivityTelaFotoBinding binding;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri imageUri;
    private FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();
    private ValidacaoCadastroImpl validacaoCadastroImpl = new ValidacaoCadastroImpl();
    private CadastrarUsuarioUseCase cadastrarUsuarioUseCase = new CadastrarUsuarioUseCase();
    private CadastrarUsuarioFireUseCase cadastrarUsuarioFireUseCase = new CadastrarUsuarioFireUseCase();

    private ListarUsuarioUseCase listarUsuarioUseCase = new ListarUsuarioUseCase();
    private LoginUseCase loginUseCase = new LoginUseCase();
    private LoginFireUseCase loginFireUseCase = new LoginFireUseCase();

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

        binding.cadastroUsuario.setOnClickListener(v -> {
            binding.frameLayoutFoto.setVisibility(ProgressBar.VISIBLE);

            Bundle bundle = getIntent().getExtras();
            String username = bundle.getString("username");
            String email = bundle.getString("email");
            String senha = bundle.getString("senha");

            cadastrarUsuarioFireUseCase.salvarLoginFirebase(username, email,
                    senha, this);
            cadastrarUsuario(bundle);
        });
    }

    private void cadastrarUsuario(Bundle bundle){
        String data =  validacaoCadastroImpl.converterDataCadastro(bundle.getString("dataNasc"));

        String caminho = getSharedPreferences("foto", MODE_PRIVATE).getString("caminho_imagem","");

        Usuario usuario = new Usuario(bundle.getString("nome"), bundle.getString("email"),
                bundle.getString("senha"), data,
                bundle.getString("username"), caminho);

        UserLogin userLogin = new UserLogin(usuario.getUsername(), usuario.getSenha());

        cadastrarUsuarioUseCase.cadastrarUsuario(usuario, () -> {

            loginFireUseCase.loginFirebase(usuario.getEmail(), usuario.getSenha());
            loginUseCase.login(userLogin, this, new LoginUseCase.LoginCallback() {

                @Override
                public void onLoginSuccess() {
                    Intent home = new Intent(TelaFoto.this, TelaHome.class);
                    binding.frameLayoutFoto.setVisibility(ProgressBar.GONE);
                    startActivity(home);
                    finish();
                }

                @Override
                public void onLoginFailure(String errorMessage) {
                    binding.frameLayoutFoto.setVisibility(ProgressBar.GONE);
                    Log.e("LOGIN", "Falha no login: " + errorMessage);
                    FirebaseAuth.getInstance().signOut();
                }
            });
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
                        .circleCrop()
                        .into(binding.camera);

                binding.cadastroUsuario.setBackgroundResource(R.drawable.button_design);
                binding.cadastroUsuario.setTextColor(getResources().getColor(R.color.white));
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
                    binding.cadastroUsuario.setBackgroundResource(R.drawable.button_design);
                    binding.cadastroUsuario.setTextColor(getResources().getColor(R.color.white));

                    fotoFirebaseImpl.uploadImage(imageUri, this);
                }
            }
    );
}