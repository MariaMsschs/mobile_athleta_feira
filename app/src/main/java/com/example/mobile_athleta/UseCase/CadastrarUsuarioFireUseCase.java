package com.example.mobile_athleta.UseCase;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

public class CadastrarUsuarioFireUseCase {
    public void salvarLoginFirebase(String username, String email, String senha, Context context) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener( task -> {
            if (task.isSuccessful()) {
                context.getSharedPreferences("login", context.MODE_PRIVATE).edit().putString("username", username).apply();
                Log.d("CADASTRO FIRE", "Sucesso");
            }
        });
    }
}
