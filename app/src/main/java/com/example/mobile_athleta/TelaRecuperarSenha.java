package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mobile_athleta.databinding.ActivityTelaRecuperarSenhaBinding;

public class TelaRecuperarSenha extends AppCompatActivity {
    private ActivityTelaRecuperarSenhaBinding binding;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.botaoVoltar.setOnClickListener(v -> {
            Intent login = new Intent(this, Login.class);
            startActivity(login);
            finish();
        });

        binding.botaoRecuperar.setOnClickListener(v -> {
            String email = binding.emailRecuperar.getText().toString();
            if (!email.isEmpty()) {
                enviarEmailRedefinicaoSenha(email);
            }
        });
    }

    private void enviarEmailRedefinicaoSenha(String emailUsuario) {
        auth.sendPasswordResetEmail(emailUsuario)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "E-mail para redefinição de senha enviado!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Erro ao enviar e-mail de redefinição de senha!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}