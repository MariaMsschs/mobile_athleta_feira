package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mobile_athleta.UseCase.LoginUseCase;
import com.example.mobile_athleta.databinding.ActivityLoginBinding;
import com.example.mobile_athleta.service.UserLogin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class Login extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginUseCase loginUseCase = new LoginUseCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseAuth auth = FirebaseAuth.getInstance();
        binding.frameLayoutLogin.setVisibility(View.GONE);

        if(auth.getCurrentUser() != null){
            auth.getCurrentUser().getEmail();
            Intent home = new Intent(Login.this, TelaHome.class);
            startActivity(home);
            finish();
        }

        binding.esqueciSenha.setOnClickListener(v -> {
            Intent recuperar = new Intent(this, TelaRecuperarSenha.class);
            startActivity(recuperar);
            finish();
        });

        binding.botaoCadastrese.setOnClickListener(v -> {
            Intent cadastro = new Intent(this, TelaCadastro.class);
            startActivity(cadastro);
            finish();
        });

        binding.botaoLogin.setOnClickListener(v -> {
            String email = ((EditText)findViewById(R.id.email)).getText().toString();
            String senha = ((EditText)findViewById(R.id.cad_senha)).getText().toString();
            UserLogin userLogin = new UserLogin(email, senha);
            binding.frameLayoutLogin.setVisibility(ProgressBar.VISIBLE);

            loginUseCase.login(userLogin);
            auth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener( task -> {
                        String msg="Você esqueceu de preencher algum dado";
                        if (task.isSuccessful()) {
                            Intent home = new Intent(Login.this, TelaHome.class);
                            binding.frameLayoutLogin.setVisibility(ProgressBar.GONE);
                            Toast.makeText(this, getSharedPreferences("login", MODE_PRIVATE).getString("username", ""), Toast.LENGTH_SHORT).show();
                            startActivity(home);
                            finish();
                        }else{
                            try{
                                throw task.getException();
                            }catch (FirebaseAuthInvalidUserException e){
                                msg = "Usuário não encontrado";
                            }catch (FirebaseAuthInvalidCredentialsException e) {
                                msg = "Senha invalida";
                            }catch (Exception e){
                                Log.e("ERRO",e.getMessage());
                            }
                            binding.frameLayoutLogin.setVisibility(ProgressBar.GONE);
                            Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}