package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.models.Anuncio;
import com.example.mobile_athleta.service.AnuncioResponse;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ListarAnunciosUseCase {
    public interface ListarAnunciosCallBack {
        void onAnunciosRetornados(List<Anuncio> anuncios);
    }
    public void listarAnuncios(String token, ListarAnunciosCallBack callback) {
        AthletaService service = RetrofitClient.getAthletaService();
        Call<AnuncioResponse> call = service.listarAnuncios(token);

        call.enqueue(new Callback<AnuncioResponse>() {
            @Override
            public void onResponse(Call<AnuncioResponse> call, retrofit2.Response<AnuncioResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Log.d("LISTAR ANUNCIOS SUCCESS", response.body().toString());
                    List<Anuncio> anuncios = response.body().getAnuncios();

                    callback.onAnunciosRetornados(anuncios);
                }
                else{
                    String errorMessage = "Erro na listagem de anuncios. Código: " + response.code() + ", Mensagem: " + response.message();
                    Log.e("LISTAR ANUNCIOS ERRO", errorMessage);
                }
            }
            @Override
            public void onFailure(Call<AnuncioResponse> call, Throwable throwable) {
                Log.e("LISTAR ANUNCIOS ERRO", throwable.getMessage());
            }
        });
    }
}
