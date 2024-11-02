package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.models.Post;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClientMongo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurtidaUseCase {

    public interface VerificarCallback {
        void onVerificarSuccess(Post post,String message);
        void onVerificarFailure(String errorMessage);
    }

    public void interacaoCurtida(Post post, VerificarCallback callback, String username) {
        AthletaService service = RetrofitClientMongo.getAthletaService();
        Call<Post> call = service.interacaoCurtida(post.getId(), username);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Post updatedPost = response.body();
                        boolean liked = updatedPost.getCurtidas().contains(username);
                        callback.onVerificarSuccess(updatedPost, liked ? "Curtido" : "Descurtido");
                    } else if (response.code() == 400) {
                        callback.onVerificarFailure("Requisição inválida. Verifique os dados enviados.");
                    }
                } else {
                    Log.e("Erro",response.code() + " " + response.message());
                    callback.onVerificarFailure("Erro ao curtir a publicação.");
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable throwable) {
                callback.onVerificarFailure("Falha na chamada da API: " + throwable.getMessage());
            }
        });
    }
}
