package com.example.mobile_athleta.UseCase;

import android.content.Context;
import android.util.Log;
import com.example.mobile_athleta.models.Usuario;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListarUsuarioUseCase {
    public interface ListarUsuarioCallBack {
        void onUsernameRetrieved(String username);
    }

    public void listarUsuarioPorEmail(String email, Context context, ListarUsuarioCallBack callback) {
        AthletaService service = RetrofitClient.getAthletaService();
        Call<Usuario> call = service.listarUsuarioPorEmail(email);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = response.body();
                    String username = usuario.getUsername();

                    context.getSharedPreferences("login", Context.MODE_PRIVATE)
                            .edit()
                            .putString("username", username)
                            .apply();

                    if (callback != null) {
                        callback.onUsernameRetrieved(username);
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable throwable) {
                Log.e("Erro ao listar usuário", throwable.getMessage());
                if (callback != null) {
                    callback.onUsernameRetrieved("");
                }
            }
        });
    }

}