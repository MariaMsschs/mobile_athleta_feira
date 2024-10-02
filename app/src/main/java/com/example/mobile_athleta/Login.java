package com.example.mobile_athleta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_athleta.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class Login extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            Intent home = new Intent(Login.this, TelaHome.class);
            startActivity(home);
            finish();
        }

        binding.botaoCadastrese.setOnClickListener(v -> {
            Intent cadastro = new Intent(Login.this, TelaCadastro.class);
            startActivity(cadastro);
            finish();
        });

        binding.botaoLogin.setOnClickListener(v -> {
            String email = ((EditText)findViewById(R.id.email)).getText().toString();
            String senha = ((EditText)findViewById(R.id.senha)).getText().toString();

            auth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener( task -> {

                        String msg="Você esqueceu de preencher algum dado";
                        if (task.isSuccessful()) {
                            Intent home = new Intent(Login.this, TelaHome.class);
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
                            Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}