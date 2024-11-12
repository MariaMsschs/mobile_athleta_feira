package com.example.mobile_athleta.UseCase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.mobile_athleta.models.RedisResponse;
import com.example.mobile_athleta.service.AthletaService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MandarEmailUseCase {

    String URL = "https://api-redis.onrender.com/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public void mandarEmail(String email, Context context) {
        AthletaService service = retrofit.create(AthletaService.class);
        Call<RedisResponse> call = service.mandarEmail(email);
        call.enqueue(new Callback<RedisResponse>() {
            @Override
            public void onResponse(Call<RedisResponse> call, Response<RedisResponse> response) {
                if(response.isSuccessful()) {
                    Log.d("SUCESSO MandarEmail", "Sucesso ao mandar email: " + email);
                    RedisResponse redisResponse = response.body();
                    String key = redisResponse.getKey();
                    context.getSharedPreferences("key", Context.MODE_PRIVATE).edit().putString("key", key).apply();
                }
            }

            @Override
            public void onFailure(Call<RedisResponse> call, Throwable throwable) {
                Toast.makeText(context, "Não foi possível enviar o email.", Toast.LENGTH_SHORT).show();
                Log.d("ERRO MandarEmail", throwable.getMessage());
            }
        });
    }
}
