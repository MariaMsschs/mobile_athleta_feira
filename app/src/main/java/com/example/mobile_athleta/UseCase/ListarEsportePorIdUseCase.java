package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.models.Anuncio;
import com.example.mobile_athleta.models.Esporte;
import com.example.mobile_athleta.service.AnuncioResponse;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.EsporteResponse;
import com.example.mobile_athleta.service.RetrofitClientSql;

import retrofit2.Call;
import retrofit2.Callback;

public class ListarEsportePorIdUseCase {
    public interface ListarEsportePorIdCallBack {
        void onEsporteRetornado(Esporte esporte);
    }

    public void listarEsportePorId(String token, Long esporteId, ListarEsportePorIdCallBack callback){
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<EsporteResponse> call = service.listarEsportePorId(token, esporteId);

        call.enqueue(new Callback<EsporteResponse>() {
            @Override
            public void onResponse(Call<EsporteResponse> call, retrofit2.Response<EsporteResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Esporte esporte = response.body().getEsportes().get(0);

                    Log.d("SUCESSO ListarEsportePorId", response.body().toString());

                    callback.onEsporteRetornado(esporte);

                } else {
                    Log.e("ERRO ListarEsportePorId", "Response is not successful or body is null. Code: " + response.code() + ", Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<EsporteResponse> call, Throwable throwable) {
                Log.e("Erro ListarEsportePorId", throwable.getMessage());
            }
        });
    }
}
