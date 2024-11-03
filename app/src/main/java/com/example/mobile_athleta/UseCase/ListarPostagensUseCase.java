package com.example.mobile_athleta.UseCase;

import com.example.mobile_athleta.models.Post;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClientMongo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarPostagensUseCase {

    public interface VerificarCallback {
        void onVerificarSuccess(List<Post> postList, List<Post> postListComCurtida);
        void onVerificarFailure(String errorMessage);
    }


    public void listarPostagens(VerificarCallback callback, int pagina, int tamanho, String username) {
        AthletaService service = RetrofitClientMongo.getAthletaService();
        Call<List<Post>> call = service.listarPostagens(pagina, tamanho);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        List<Post> postList = response.body();
                        List<Post> postsComCurtida = new ArrayList<>();
                        for (Post post : postList) {
                            boolean liked = post.getCurtidas().contains(username);
                            post.setLiked(liked);
                            postsComCurtida.add(post);
                        }
                        callback.onVerificarSuccess(postList,postsComCurtida);
                    } else if (response.code() == 400) {
                        callback.onVerificarFailure("Está faltando algo");
                    }
                } else {
                    callback.onVerificarFailure("Erro ao obter postagens");
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable throwable) {
                callback.onVerificarFailure("Falha na chamada da API: " + throwable.getMessage());
            }
        });
    }

}
