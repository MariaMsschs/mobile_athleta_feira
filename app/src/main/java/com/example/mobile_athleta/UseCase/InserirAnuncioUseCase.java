package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.models.Anuncio;
import com.example.mobile_athleta.service.ApiResponse;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InserirAnuncioUseCase {
    public interface InserirAnuncioCallBack{
        void onInserirAnuncioSuccess();
        void onInserirAnuncioFailure();
    }

    public void inserirAnuncio(String token, Anuncio anuncio, InserirAnuncioCallBack callback){
        AthletaService service = RetrofitClient.getAthletaService();
        Call<ApiResponse> call = service.inserirAnuncio(token, anuncio);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    Log.d("INSERIR ANUNCIO SUCESSO", new Gson().toJson(anuncio));
                    callback.onInserirAnuncioSuccess();
                }
                else{
                    String errorMessage = "Erro na listagem de anuncios. Código: " + response.code() + ", Mensagem: " + response.message();
                    Log.e("LISTAR ANUNCIOS ERRO", errorMessage);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                Log.e("INSERIR ANUNCIO ERRO", throwable.getMessage());
                callback.onInserirAnuncioFailure();
            }
        });


    }
}
