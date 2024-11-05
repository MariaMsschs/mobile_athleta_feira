package com.example.mobile_athleta.UseCase;

import com.example.mobile_athleta.models.Forum;
import com.example.mobile_athleta.service.AnuncioResponse;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.ForumResponse;
import com.example.mobile_athleta.service.RetrofitClientSql;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarForumPorNomeUseCase {

    public interface ListarCallback {
        void onListarSuccess(List<Forum> forum);
        void onListarFailure(String errorMessage);
    }

    public void listarForumPorNome(String token,String nome, ListarCallback callback) {
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<ForumResponse> call = service.listarForumPorNome(token,nome);

        call.enqueue(new Callback<ForumResponse>() {
            @Override
            public void onResponse(Call<ForumResponse> call, Response<ForumResponse> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        callback.onListarSuccess(response.body().getObject());
                    } else if (response.code() == 400) {
                        callback.onListarFailure("Está faltando algo");
                    }
                } else {
                    callback.onListarFailure("Erro ao listar forum:"+response.code());
                }
            }

            @Override
            public void onFailure(Call<ForumResponse> call, Throwable throwable) {
                callback.onListarFailure("Falha na chamada da API: " + throwable.getMessage());
            }
        });
        }
}