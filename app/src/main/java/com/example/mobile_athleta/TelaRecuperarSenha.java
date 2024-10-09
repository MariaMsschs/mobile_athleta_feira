package com.example.mobile_athleta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.mobile_athleta.databinding.ActivityTelaRecuperarSenhaBinding;

public class TelaRecuperarSenha extends AppCompatActivity {
    private ActivityTelaRecuperarSenhaBinding binding;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaRecuperarSenhaBinding.inflate(getLayoutInflater());
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

                        LayoutInflater inflater = getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.senha_dialog, null);
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setView(dialogView);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        View botao_ok = dialogView.findViewById(R.id.botao_ok);
                        botao_ok.setOnClickListener(v -> {
                            Intent login = new Intent(this, Login.class);
                            startActivity(login);
                            finish();
                            dialog.dismiss();
                        });

                    } else {
                        Toast.makeText(this, "Erro ao enviar e-mail de redefinição de senha!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}