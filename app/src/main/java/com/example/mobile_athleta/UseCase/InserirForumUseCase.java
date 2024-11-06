package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.models.Anuncio;
import com.example.mobile_athleta.models.Forum;
import com.example.mobile_athleta.service.ApiResponse;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClientSql;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InserirForumUseCase {
    public interface InserirForumCallBack{
        void onInserirForumSuccess();
        void onInserirForumFailure();
    }

    public void inserirForum(String token, Forum forum, InserirForumCallBack callback){
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<ApiResponse> call = service.inserirForum(token, forum);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    Log.d("INSERIR FORUM SUCESSO", new Gson().toJson(forum));
                    callback.onInserirForumSuccess();
                }
                else{
                    String errorMessage = "Erro ao inserir forum. Código: " + response.code() + ", Mensagem: " + response.message();
                    Log.e("LISTAR FORUM ERRO", errorMessage);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                Log.e("INSERIR FORUM ERRO", throwable.getMessage());
                callback.onInserirForumFailure();
            }
        });


    }
}
