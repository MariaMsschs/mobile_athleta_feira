package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.models.Anuncio;
import com.example.mobile_athleta.service.AnuncioResponse;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClientSql;
import retrofit2.Call;
import retrofit2.Callback;

public class ListarAnuncioPorIdUseCase {
    public interface ListarAnuncioPorIdCallBack {
        void onAnuncioRetornado(Anuncio anuncio);
    }

    public void listarAnuncioPorId(String token, Long anuncioId, ListarAnuncioPorIdCallBack callback){
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<AnuncioResponse> call = service.listarAnuncioPorId(token, anuncioId);

        call.enqueue(new Callback<AnuncioResponse>() {
            @Override
            public void onResponse(Call<AnuncioResponse> call, retrofit2.Response<AnuncioResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Anuncio anuncio = response.body().getAnuncios().get(0);

                    Log.d("SUCESSO ListarAnuncioPorId", response.body().toString());

                    callback.onAnuncioRetornado(anuncio);

                } else {
                    Log.e("ERRO ListarAnuncioPorId", "Response is not successful or body is null. Code: " + response.code() + ", Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<AnuncioResponse> call, Throwable throwable) {
                Log.e("Erro ListarAnuncioPorId", throwable.getMessage());
            }
        });
    }
}
