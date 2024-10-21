package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.mobile_athleta.databinding.ActivityTelaVerificacaoBinding;

public class TelaVerificacao extends AppCompatActivity {
    private ActivityTelaVerificacaoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_verificacao);

        binding = ActivityTelaVerificacaoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.botaoVoltarVerificacao.setOnClickListener(view -> {
            Intent intent = new Intent(TelaVerificacao.this, TelaCadastro.class);
            startActivity(intent);
            finish();
        });

        binding.botaoVerificacao.setOnClickListener(v -> {
            verificar(binding.codigoVerificacao);
        });
    }

    public void verificar(EditText codigoVerificacao) {
        checkField();
        if(binding.codigoVerificacao.getError() == null) {
            // chamada da api
        }
    }

    public void checkField(){
        if(binding.codigoVerificacao.getText().toString().isEmpty()){
            binding.codigoVerificacao.setError("Este campo é obrigatório");
        }
    }
}