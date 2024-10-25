package com.example.mobile_athleta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.mobile_athleta.UseCase.AlterarSenhaUseCase;
import com.example.mobile_athleta.databinding.ActivityTelaAlterarSenhaBinding;
import com.example.mobile_athleta.databinding.ActivityTelaCadastroBinding;
import com.example.mobile_athleta.service.UserLogin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TelaAlterarSenha extends AppCompatActivity {
    private ActivityTelaAlterarSenhaBinding binding;

    AlterarSenhaUseCase alterarSenhaUseCase = new AlterarSenhaUseCase();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaAlterarSenhaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String senha = binding.novaSenha.getText().toString();
        UserLogin userLogin = new UserLogin(user.getEmail(), senha);

        binding.botaoAlterarSenha.setOnClickListener(v -> {
            alterarSenhaFire();
            alterarSenhaUseCase.alterarSenha(userLogin);
        });
    }

    private void alterarSenhaFire(){
        checkAllFields();
        if (user != null) {
            String novaSenha = binding.novaSenha.getText().toString();
            user.updatePassword(novaSenha)
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
    }

    public void checkAllFields() {
        if(binding.novaSenha.getText().toString().trim().isEmpty()){
            binding.novaSenha.setError("Este campo é obrigatório");
        }
    }

}