package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.mobile_athleta.UseCase.MandarEmailUseCase;
import com.example.mobile_athleta.UseCase.VerificarOTPUseCase;
import com.example.mobile_athleta.databinding.ActivityTelaVerificacaoBinding;

public class TelaVerificacao extends AppCompatActivity {
    private ActivityTelaVerificacaoBinding binding;

    private VerificarOTPUseCase verificarOTPUseCase = new VerificarOTPUseCase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_verificacao);

        binding = ActivityTelaVerificacaoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.botaoVoltarVerificacao.setOnClickListener(view -> {
            Bundle bundle = getIntent().getExtras();
            Intent intent = new Intent(TelaVerificacao.this, TelaCadastro.class);
            intent.putExtras(bundle);
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
            Bundle bundle = getIntent().getExtras();
            verificarOTPUseCase.verificar(getSharedPreferences("key", MODE_PRIVATE).getString("key", ""),
                    codigoVerificacao.getText().toString(), this, new VerificarOTPUseCase.VerificarCallback() {
                        @Override
                        public void onVerificarSuccess() {
                            Intent intent = new Intent(TelaVerificacao.this, TelaFoto.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onVerificarFailure(String errorMessage) {
                            Log.d("ERRO", errorMessage);
                            binding.codigoVerificacao.setError("Código inválido");
                        }
                    });
        }
    }

    public void checkField(){
        if(binding.codigoVerificacao.getText().toString().isEmpty()){
            binding.codigoVerificacao.setError("Este campo é obrigatório");
        }
    }
}