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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginUseCase {

    String URL = "https://api-sql-gbb8.onrender.com/api/auth/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public void login(UserLogin login, Context context){
        AthletaService service = retrofit.create(AthletaService.class);
        Call<LoginResponse> call = service.login(login);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (response.isSuccessful() && loginResponse != null) {
                        Usuario usuario = loginResponse.getUsuario();
                        String token = loginResponse.getToken();

                        Long idUsuario = usuario.getIdUsuario();
                        String nome = usuario.getNome();
                        String email = usuario.getEmail();
                        String dataNascimento = usuario.getDtNasc();
                        String caminho = usuario.getFotoPerfil();

                        SharedPreferences.Editor editor = context.getSharedPreferences("login", context.MODE_PRIVATE).edit();
                        editor.putLong("idUsuario", idUsuario);
                        editor.putString("nome", nome);
                        editor.putString("email", email);
                        editor.putString("data_nascimento", dataNascimento);
                        editor.putString("caminho", caminho);
                        editor.putString("senha", login.getSenha());
                        editor.putString("token", token);
                        editor.apply();

                        Log.d("LOGIN SUCCESS", loginResponse.toString());
                    }
                    else {
                        Log.e("LOGIN ERROR", "Response is not successful or body is null. Code: " + response.code() + ", Message: " + response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                Log.e("LOGIN ERROR", throwable.getMessage());
            }
        });
    }

}
