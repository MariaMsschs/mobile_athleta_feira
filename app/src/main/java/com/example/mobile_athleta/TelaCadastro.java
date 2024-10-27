package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.example.mobile_athleta.UseCase.CadastrarUsuarioUseCase;
import com.example.mobile_athleta.databinding.ActivityTelaCadastroBinding;
import com.example.mobile_athleta.service.ValidacaoCadastroImpl;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.mobile_athleta.models.Usuario;

public class TelaCadastro extends AppCompatActivity {
    private ActivityTelaCadastroBinding binding;
    private EditText nomeEdit;
    private EditText emailEdit;
    private EditText senhaEdit;
    private EditText dataNascimentoEdit;
    private EditText usernameEdit;

    private FrameLayout frameLayout;

    private ValidacaoCadastroImpl validacaoCadastroImpl = new ValidacaoCadastroImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nomeEdit = findViewById(R.id.nome);
        emailEdit = findViewById(R.id.cad_email);
        senhaEdit = findViewById(R.id.cad_senha);
        dataNascimentoEdit = findViewById(R.id.data_nascimento);
        usernameEdit = findViewById(R.id.nome_usuario);
        frameLayout = findViewById(R.id.frameLayoutCadastro);
        frameLayout.setVisibility(ProgressBar.GONE);

        binding.botaoVoltar.setOnClickListener(v -> {
            Intent login = new Intent(this, Login.class);
            startActivity(login);
            finish();
        });

        binding.calendario.setOnClickListener(v -> {
            validacaoCadastroImpl.definirData(dataNascimentoEdit, this);
        });

        dataNascimentoEdit.setOnClickListener(v -> {
            validacaoCadastroImpl.definirData(dataNascimentoEdit, this);
        });

        binding.botaoCadastro.setOnClickListener(v -> {
            checkAllFields();
            if(emailEdit.getError() == null && nomeEdit.getError() == null && senhaEdit.getError() == null
                    && usernameEdit.getError() == null && dataNascimentoEdit.getError() == null) {

                frameLayout.setVisibility(ProgressBar.VISIBLE);

                Bundle bundle = new Bundle();
                bundle.putString("nome", nomeEdit.getText().toString());
                bundle.putString("email", emailEdit.getText().toString());
                bundle.putString("senha", senhaEdit.getText().toString());
                bundle.putString("username", usernameEdit.getText().toString());
                bundle.putString("dataNasc", dataNascimentoEdit.getText().toString());

                salvarLoginFirebase(usernameEdit.getText().toString(), emailEdit.getText().toString(),
                        senhaEdit.getText().toString());

                frameLayout.setVisibility(ProgressBar.GONE);
                Intent foto = new Intent(this, TelaFoto.class);
                foto.putExtras(bundle);
                startActivity(foto);
                finish();
            }
        });
    }
    private void salvarLoginFirebase(String username, String email, String senha) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener( task -> {
            if (task.isSuccessful()) {
                getSharedPreferences("login", MODE_PRIVATE).edit().putString("username", username).apply();
            }
        });
    }

    public void checkAllFields() {
        if(nomeEdit.getText().toString().trim().isEmpty()){
            nomeEdit.setError("Este campo é obrigatório");
        } if (emailEdit.getText().toString().trim().isEmpty()) {
            emailEdit.setError("Este campo é obrigatório");
        } if(senhaEdit.getText().toString().trim().isEmpty()){
            senhaEdit.setError("Este campo é obrigatório");
        } if(dataNascimentoEdit.getText().toString().trim().isEmpty()){
            dataNascimentoEdit.setError("Este campo é obrigatório");
        } if(usernameEdit.getText().toString().trim().isEmpty()){
            usernameEdit.setError("Este campo é obrigatório");
        }
    }
}