package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mobile_athleta.databinding.ActivityTelaConfiguracaoBinding;
import com.example.mobile_athleta.fragments.PerfilFragment;
import com.google.firebase.auth.FirebaseAuth;

public class TelaConfiguracao extends AppCompatActivity {
    private ActivityTelaConfiguracaoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityTelaConfiguracaoBinding binding = ActivityTelaConfiguracaoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.botaoVoltar.setOnClickListener(v -> {
            Intent perfil = new Intent(this, PerfilFragment.class);
            startActivity(perfil);
            finish();
        });

        binding.logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent login = new Intent(this, Login.class);
            getSharedPreferences("login", MODE_PRIVATE).edit().remove("idUsuario").apply();
            getSharedPreferences("login", MODE_PRIVATE).edit().remove("nome").apply();
            getSharedPreferences("login", MODE_PRIVATE).edit().remove("email").apply();
            getSharedPreferences("login", MODE_PRIVATE).edit().remove("data_nascimento").apply();
            getSharedPreferences("login", MODE_PRIVATE).edit().remove("caminho").apply();
            getSharedPreferences("login", MODE_PRIVATE).edit().remove("username").apply();
            login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(login);
        });

        binding.alterarSenha.setOnClickListener(v -> {
            Intent alterarSenha = new Intent(this, TelaAlterarSenha.class);
            startActivity(alterarSenha);
            finish();
        });

        binding.infoConta.setOnClickListener(v -> {
            Intent infosConta = new Intent(this, TelaInfosConta.class);
            startActivity(infosConta);
            finish();
        });
    }
}