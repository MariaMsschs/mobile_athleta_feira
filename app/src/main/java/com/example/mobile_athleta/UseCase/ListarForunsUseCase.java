package com.example.mobile_athleta.UseCase;

import android.content.Context;

import com.example.mobile_athleta.models.Forum;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.ForumResponse;
import com.example.mobile_athleta.service.RetrofitClientSql;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarForunsUseCase {
    public interface ListarForunsCallback {
        void onListarForunsSuccess(List<Forum> forumList);
        void onListarForunsFailure(String errorMessage);
    }

    public void listarForuns(String token, ListarForunsCallback callback, int pagina, int tamanho) {
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<ForumResponse> call = service.listarForuns(token, pagina, tamanho);

        call.enqueue(new Callback<ForumResponse>() {
            @Override
            public void onResponse(Call<ForumResponse> call, Response<ForumResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        callback.onListarForunsSuccess(response.body().getObject());
                    } else if (response.code() == 400) {
                        callback.onListarForunsFailure("Está faltando algo");
                    }
                } else {
                    callback.onListarForunsFailure("Erro ao listar forum");
                }
            }

            @Override
            public void onFailure(Call<ForumResponse> call, Throwable throwable) {
                callback.onListarForunsFailure("Falha na chamada da API: " + throwable.getMessage());
            }
        });
    }
}
