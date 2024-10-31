package com.example.mobile_athleta.UseCase;

import android.content.Context;
import android.util.Log;

import com.example.mobile_athleta.models.RedisResponse;
import com.example.mobile_athleta.service.AthletaService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerificarOTPUseCase {

    String URL = "https://api-redis.onrender.com/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public interface VerificarCallback {
        void onVerificarSuccess();
        void onVerificarFailure(String errorMessage);
    }

    public void verificar(String email, String codigo, Context context, VerificarCallback callback) {

        AthletaService service = retrofit.create(AthletaService.class);
        Call<RedisResponse> call = service.deletarEmail(email, codigo);

        call.enqueue(new Callback<RedisResponse>() {
            @Override
            public void onResponse(Call<RedisResponse> call, Response<RedisResponse> response) {
                if(response.isSuccessful()) {
                    if (response.code() == 200) {
                        context.getSharedPreferences("key", Context.MODE_PRIVATE).edit().clear().apply();
                        callback.onVerificarSuccess();
                    }
                } else if (response.code() == 404) {
                    Log.e("Erro API", "Código inválido, status: 404");
                    callback.onVerificarFailure("Código inválido");
                }
            }

            @Override
            public void onFailure(Call<RedisResponse> call, Throwable throwable) {
                Log.e("erro log", throwable.getMessage());
                callback.onVerificarFailure("Erro na api");
            }
        });
    }
}
