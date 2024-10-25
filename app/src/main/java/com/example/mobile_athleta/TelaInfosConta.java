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
import com.bumptech.glide.Glide;
import com.example.mobile_athleta.UseCase.AtualizarUsuarioUseCase;
import com.example.mobile_athleta.databinding.ActivityTelaInfosContaBinding;
import com.example.mobile_athleta.models.Usuario;
import com.example.mobile_athleta.service.FotoFirebaseImpl;
import com.example.mobile_athleta.service.ValidacaoCadastroImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class TelaInfosConta extends AppCompatActivity{

    private ActivityTelaInfosContaBinding binding;
    private EditText nome;
    private EditText email;
    private EditText dataNascimento;
    private EditText username;
    private Uri imageUri;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private AtualizarUsuarioUseCase atualizarUsuarioUseCase = new AtualizarUsuarioUseCase();
    private ValidacaoCadastroImpl validacaoCadastroImpl = new ValidacaoCadastroImpl();
    private FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_infos_conta);

        binding = ActivityTelaInfosContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nome = findViewById(R.id.info_nome);
        email = findViewById(R.id.info_email);
        dataNascimento = findViewById(R.id.info_data);
        username = findViewById(R.id.info_user);

        String nomeAtual = getSharedPreferences("login", MODE_PRIVATE).getString("nome", "");
        String emailAtual = getSharedPreferences("login", MODE_PRIVATE).getString("email", "");
        String usernameAtual = getSharedPreferences("login", MODE_PRIVATE).getString("username", "");
        String dataNascimentoAtual = getSharedPreferences("login", MODE_PRIVATE).getString("data_nascimento", "");
        String caminhoAtual = getSharedPreferences("login", MODE_PRIVATE).getString("caminho","");

        nome.setText(nomeAtual);
        email.setText(emailAtual);
        username.setText(usernameAtual);
        dataNascimento.setText(dataNascimentoAtual);

        if (!caminhoAtual.isEmpty()) {
            fotoFirebaseImpl.recuperarImagem(binding.camera, caminhoAtual);
            Log.d("IMAGEM!", "Caminho da imagem encontrado: " + caminhoAtual);
        } else {
            Log.d("IMAGEM!", "Caminho da imagem não encontrado");
        }

        binding.botaoVoltar.setOnClickListener(v -> {
            Intent config = new Intent(this, TelaConfiguracao.class);
            startActivity(config);
            finish();
        });

        binding.calendario.setOnClickListener(v -> {
            validacaoCadastroImpl.definirData(dataNascimento, this);
        });

        dataNascimento.setOnClickListener(v -> {
            validacaoCadastroImpl.definirData(dataNascimento, this);
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
                resultLauncherGaleria.launch(galeria);
                dialog.dismiss();
            });
        });

        binding.infoSalvar.setOnClickListener(v -> {
            checkAllFields();
            if(nome.getText().toString() != null && email.getText().toString() != null &&
                    dataNascimento.getText().toString() != null &&
                    username.getText().toString() != null) {

                String dataConvertida = validacaoCadastroImpl.converterData(dataNascimento.getText().toString());

                Usuario usuario = new Usuario(nome.getText().toString(), email.getText().toString(),
                        dataConvertida, username.getText().toString(), caminhoAtual);

                Long userId = getSharedPreferences("login", MODE_PRIVATE).getLong("idUsuario", 0L);

                atualizarUsuarioUseCase.atualizarUsuario(usuario, userId);
                atualizarUsuarioFire();
            }
        });
    }

    private void atualizarUsuarioFire(){
        String novoEmail = email.getText().toString();
        user.updateEmail(novoEmail)
        .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alterado_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();
                View botao_alterado = dialogView.findViewById(R.id.botao_alterado);
                botao_alterado.setOnClickListener(v -> {
                    Intent config = new Intent(this, TelaConfiguracao.class);
                    startActivity(config);
                    finish();
                    dialog.dismiss();
                });

            } else {
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.erro_alterar_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();
                View botao_erro_alterar = dialogView.findViewById(R.id.botao_erro_alterar);
                botao_erro_alterar.setOnClickListener(v -> {
                    Intent config = new Intent(this, TelaConfiguracao.class);
                    startActivity(config);
                    finish();
                    dialog.dismiss();
                });
            }
        });
    }

    public void checkAllFields() {
        if(nome.getText().toString().trim().isEmpty()){
            nome.setError("Este campo é obrigatório");
        } if (email.getText().toString().trim().isEmpty()) {
            email.setError("Este campo é obrigatório");
        } if(dataNascimento.getText().toString().trim().isEmpty()){
            dataNascimento.setError("Este campo é obrigatório");
        } if(username.getText().toString().trim().isEmpty()){
            username.setError("Este campo é obrigatório");
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

                fotoFirebaseImpl.uploadImage(imageUri, this);
            }
        }
    }
    private final ActivityResultLauncher<Intent> resultLauncherGaleria = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    Glide.with(this)
                            .load(imageUri)
                            .circleCrop()
                            .into(binding.camera);

                    fotoFirebaseImpl.uploadImage(imageUri, this);
                }
            }
    );
}