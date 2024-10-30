package com.example.mobile_athleta.UseCase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.mobile_athleta.models.Usuario;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.LoginResponse;
import com.example.mobile_athleta.service.UserLogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginUseCase {

    String URL = "https://api-sql-gbb8.onrender.com/api/auth/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public interface LoginCallback {
        void onLoginSuccess();
        void onLoginFailure(String errorMessage);
    }

    public void login(UserLogin login, Context context, LoginCallback callback) {
        AthletaService service = retrofit.create(AthletaService.class);
        Call<LoginResponse> call = service.login(login);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null) {
                        Usuario usuario = loginResponse.getUsuario();
                        String token = loginResponse.getToken();

                        SharedPreferences.Editor editor = context.getSharedPreferences("login", Context.MODE_PRIVATE).edit();
                        editor.putLong("idUsuario", usuario.getIdUsuario());
                        editor.putString("nome", usuario.getNome());
                        editor.putString("email", usuario.getEmail());
                        editor.putString("data_nascimento", usuario.getDtNasc());
                        editor.putString("caminho", usuario.getFotoPerfil());
                        editor.putString("senha", login.getSenha());
                        editor.putString("token", token);
                        editor.apply();

                        Log.d("LOGIN SUCCESS", loginResponse.toString());

                        callback.onLoginSuccess();
                    } else {
                        String errorMessage = "Resposta vazia do servidor.";
                        Log.e("LOGIN ERROR", errorMessage);
                        callback.onLoginFailure(errorMessage);
                    }
                } else {
                    String errorMessage = "Erro no login. Código: " + response.code() + ", Mensagem: " + response.message();
                    Log.e("LOGIN ERROR", errorMessage);
                    callback.onLoginFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                String errorMessage = throwable.getMessage();
                Log.e("LOGIN ERROR", errorMessage);
                callback.onLoginFailure(errorMessage);
            }
        });
    }
}

