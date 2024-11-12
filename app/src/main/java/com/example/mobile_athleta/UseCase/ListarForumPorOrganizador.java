package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.models.Forum;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.ForumResponse;
import com.example.mobile_athleta.service.RetrofitClientSql;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ListarForumPorOrganizador {
    public interface ListarForumPorOrganizadorCallBack {
        void onForumRetornado(List<Forum> forumList);

        void onForumErro(String error);
    }

    public void listarForumPorOrganizador(String token, Long organizadorId, ListarForumPorOrganizadorCallBack callback){
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<ForumResponse> call = service.listarForumPorOrganizador(token, organizadorId);

        call.enqueue(new Callback<ForumResponse>() {
            @Override
            public void onResponse(Call<ForumResponse> call, retrofit2.Response<ForumResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isResponseSucessfull() == true) {
                    Log.d("SUCESSO ListarForumPorOrganizador", response.body().toString());
                    callback.onForumRetornado(response.body().getObject());

                    if (response.code() == 200) {
                        Log.d("SUCESSO ListarForumPorOrganizador", response.body().toString());
                        callback.onForumRetornado(response.body().getObject());
                    } else if (response.code() == 400) {
                        callback.onForumErro("Está faltando algo");
                    }

                } else {
                    if (response.code() == 400) {
                        callback.onForumErro("Vazio");
                    }
                    callback.onForumErro("onresponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ForumResponse> call, Throwable throwable) {
                callback.onForumErro("Falha na chamada da API: " + throwable.getMessage());
            }
        });
    }
}
