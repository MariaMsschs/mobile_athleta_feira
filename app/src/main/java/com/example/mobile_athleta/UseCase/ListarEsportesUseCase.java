package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.models.Anuncio;
import com.example.mobile_athleta.models.Esporte;
import com.example.mobile_athleta.service.AnuncioResponse;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.EsporteResponse;
import com.example.mobile_athleta.service.RetrofitClientSql;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ListarEsportesUseCase {
    public interface ListarEsportesCallBack {
        void onEsportesRetornados(List<Esporte> esportes);
    }
    public void listarEsportes(String token, ListarEsportesCallBack callback) {
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<EsporteResponse> call = service.listarEsportes(token);

        call.enqueue(new Callback<EsporteResponse>() {
            @Override
            public void onResponse(Call<EsporteResponse> call, retrofit2.Response<EsporteResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Log.d("LISTAR ESPORTES SUCCESS", response.body().toString());
                    List<Esporte> esportes = response.body().getEsportes();
                    callback.onEsportesRetornados(esportes);
                }
                else{
                    String errorMessage = "Erro na listagem de esportes. Código: " + response.code() + ", Mensagem: " + response.message();
                    Log.e("LISTAR ESPORTES ERRO", errorMessage);
                }
            }
            @Override
            public void onFailure(Call<EsporteResponse> call, Throwable throwable) {
                Log.e("LISTAR ESPORTES ERRO", throwable.getMessage());
            }
        });
    }
}
