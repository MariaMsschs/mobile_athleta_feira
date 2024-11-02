package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.models.Anuncio;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.ListarAnuncioResponse;
import com.example.mobile_athleta.service.RetrofitClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;

public class ListarAnuncioPorIdUseCase {
    public interface ListarAnuncioPorIdCallBack {
        void onAnuncioRetornado(Anuncio anuncio);
    }

    public void listarAnuncioPorId(String token, Long anuncioId, ListarAnuncioPorIdCallBack callback){
        AthletaService service = RetrofitClient.getAthletaService();
        Call<ListarAnuncioResponse> call = service.listarAnuncioPorId(token, anuncioId);

        call.enqueue(new Callback<ListarAnuncioResponse>() {
            @Override
            public void onResponse(Call<ListarAnuncioResponse> call, retrofit2.Response<ListarAnuncioResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Anuncio anuncio = response.body().getAnuncio();

                    Log.d("SUCESSO ListarAnuncioPorId", new Gson().toJson(response));

                    callback.onAnuncioRetornado(anuncio);

                } else {
                    Log.e("ERRO ListarAnuncioPorId", "Response is not successful or body is null. Code: " + response.code() + ", Message: " + response.message());
                    Log.d("teste", response.body().getDescription());
                }
            }

            @Override
            public void onFailure(Call<ListarAnuncioResponse> call, Throwable throwable) {
                Log.e("Erro ListarAnuncioPorId", throwable.getMessage());
            }
        });
    }
}
