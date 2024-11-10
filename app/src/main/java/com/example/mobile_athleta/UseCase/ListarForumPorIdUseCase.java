package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.models.Anuncio;
import com.example.mobile_athleta.models.Forum;
import com.example.mobile_athleta.service.AnuncioResponse;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.ForumResponse;
import com.example.mobile_athleta.service.RetrofitClientSql;

import retrofit2.Call;
import retrofit2.Callback;

public class ListarForumPorIdUseCase {
    public interface ListarForumPorIdCallBack {
        void onForumRetornado(Forum forum);
    }

    public void listarForumPorId(String token, Long forumId, ListarForumPorIdCallBack callback){
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<ForumResponse> call = service.listarForumPorId(token, forumId);

        call.enqueue(new Callback<ForumResponse>() {
            @Override
            public void onResponse(Call<ForumResponse> call, retrofit2.Response<ForumResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isResponseSucessfull() == true) {
                    Forum forum = response.body().getObject().get(0);

                    Log.d("SUCESSO ListarForumPorId", response.body().toString());

                    callback.onForumRetornado(forum);

                } else {
                    Log.e("ERRO ListarForumPorId", "Response is not successful or body is null. Code: " + response.code() + ", Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ForumResponse> call, Throwable throwable) {
                Log.e("Erro ListarForumPorId", throwable.getMessage());
            }
        });
    }
}
