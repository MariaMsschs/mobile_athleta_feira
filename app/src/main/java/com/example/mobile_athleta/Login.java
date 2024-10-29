package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.example.mobile_athleta.UseCase.ListarUsuarioUseCase;
import com.example.mobile_athleta.UseCase.LoginFireUseCase;
import com.example.mobile_athleta.UseCase.LoginUseCase;
import com.example.mobile_athleta.databinding.ActivityLoginBinding;
import com.example.mobile_athleta.models.Usuario;
import com.example.mobile_athleta.service.UserLogin;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private ListarUsuarioUseCase listarUsuarioUseCase = new ListarUsuarioUseCase();
    private LoginUseCase loginUseCase = new LoginUseCase();
    private LoginFireUseCase loginFireUseCase = new LoginFireUseCase();


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
            binding.frameLayoutLogin.setVisibility(ProgressBar.VISIBLE);

            String email = ((EditText)findViewById(R.id.email)).getText().toString();
            String senha = ((EditText)findViewById(R.id.cad_senha)).getText().toString();
            Intent home = new Intent(this, TelaHome.class);

            listarUsuarioUseCase.listarUsuarioPorEmail(email, this, username -> {
                UserLogin userLogin = new UserLogin(username, senha);
                loginFireUseCase.loginFirebase(email, senha);
                loginUseCase.login(userLogin, this, new LoginUseCase.LoginCallback() {
                    @Override
                    public void onLoginSuccess(Usuario usuario, String token) {
                        Log.d("LOGIN", "Login bem-sucedido para o usuário: " + usuario.getNome());
                        binding.frameLayoutLogin.setVisibility(ProgressBar.GONE);
                        startActivity(home);
                        finish();
                    }

                    @Override
                    public void onLoginFailure(String errorMessage) {
                        binding.frameLayoutLogin.setVisibility(ProgressBar.GONE);
                        Log.e("LOGIN", "Falha no login: " + errorMessage);
                        FirebaseAuth.getInstance().signOut();
                    }
                });
            });
        });

    }

}