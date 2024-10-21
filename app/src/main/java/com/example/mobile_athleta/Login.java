package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.mobile_athleta.UseCase.ListarUsuarioUseCase;
import com.example.mobile_athleta.databinding.ActivityLoginBinding;
import com.example.mobile_athleta.models.Usuario;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.LoginResponse;
import com.example.mobile_athleta.service.UserLogin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private ListarUsuarioUseCase listarUsuarioUseCase = new ListarUsuarioUseCase();

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

            loginFirebase(email, senha, auth);

            String username = listarUsuarioUseCase.listarUsuarioPorEmail(email);
            UserLogin userLogin = new UserLogin(username, senha);
            login(userLogin);
        });
    }

    public void login(UserLogin login){
        String URL = "https://api-spring-z2b5.onrender.com/api/auth/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AthletaService service = retrofit.create(AthletaService.class);
        Call<LoginResponse> call = service.login(login);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null) {
                        Usuario usuario = loginResponse.getUsuario();
                        Long idUsuario = usuario.getId();
                        getSharedPreferences("login", MODE_PRIVATE).edit().putLong("idUsuario", idUsuario).apply();
                        Intent home = new Intent(Login.this, TelaHome.class);
                        startActivity(home);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                Log.e("ERRO", throwable.getMessage());
            }
        });
    }

    private void loginFirebase(String email, String senha, FirebaseAuth auth) {
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
    }
}