package com.example.mobile_athleta.UseCase;

import com.example.mobile_athleta.models.Post;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClientMongo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InserirPostagemUseCase {

    public interface InserirCallback {
        void onInserirSuccess(Post post);
        void onInserirFailure(String errorMessage);
    }

    public void inserir(Post post, InserirCallback callback) {
        AthletaService service = RetrofitClientMongo.getAthletaService();
        Call<Post> call = service.inserirPostagem(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        callback.onInserirSuccess(response.body());
                    } else if (response.code() == 400) {
                        callback.onInserirFailure("Está faltando algo");
                    }
                } else {
                    callback.onInserirFailure("Erro ao inserir postagem");
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable throwable) {
                callback.onInserirFailure("Falha na chamada da API: " + throwable.getMessage());
            }
        });
    }
}
