package com.example.mobile_athleta.UseCase;


import com.example.mobile_athleta.models.Comentario;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClientMongo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarComentariosUseCase {

    public interface VerificarCallback {
        void onVerificarSuccess(List<Comentario> comentarios, String message);
        void onVerificarFailure(String errorMessage);
    }

    public void listarComentarios(String id, VerificarCallback callback) {
        AthletaService service = RetrofitClientMongo.getAthletaService();
        Call<List<Comentario>> call = service.listarComentarios(id);

        call.enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        List<Comentario> commentsList = response.body();
                        callback.onVerificarSuccess(commentsList, "");
                    } else if (response.code() == 400) {
                        callback.onVerificarFailure("Esta faltando algo");
                    }
                } else {
                    callback.onVerificarFailure("Erro ao obter postagens");
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable throwable) {
                callback.onVerificarFailure("Falha na chamada da API: " + throwable.getMessage());
            }
        });
    }
}
