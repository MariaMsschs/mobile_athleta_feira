package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.models.Evento;
import com.example.mobile_athleta.models.Forum;
import com.example.mobile_athleta.service.ApiResponse;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClientSql;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InserirEventoUseCase {
    public interface InserirEventoCallBack{
        void onInserirEventoSuccess();
        void onInserirEventoFailure();
    }

    public void inserirEvento(String token, Evento evento, InserirEventoCallBack callback){
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<ApiResponse> call = service.inserirEvento(token, evento);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful() && response.body() != null && response.body().getResponseSucessfull() == true){
                    Log.d("INSERIR EVENTO SUCESSO", new Gson().toJson(evento));
                    callback.onInserirEventoSuccess();
                }
                else{
                    Log.d("token", token);
                    Log.d("evento", new Gson().toJson(evento));
                    String errorMessage = "Erro ao inserir evento. Código: " + response.code() + ", Mensagem: " + response.message();
                    Log.e("INSERIR EVENTO ERRO", errorMessage);
                    callback.onInserirEventoFailure();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                Log.e("INSERIR EVENTO ERRO", throwable.getMessage());
                callback.onInserirEventoFailure();
            }
        });


    }
}
