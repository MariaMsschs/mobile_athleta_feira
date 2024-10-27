package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginFireUseCase {
    public void loginFirebase(String email, String senha) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener( task -> {
                    String msg="Você esqueceu de preencher algum dado";
                    if (task.isSuccessful()) {
                        Log.d("LOGIN FIRE", "login com sucesso");
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
                    }
                });
    }
}
