package com.example.mobile_athleta.UseCase;

import com.example.mobile_athleta.models.Evento;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.EventoResponse;
import com.example.mobile_athleta.service.RetrofitClientSql;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarEventosPorNomeUseCase {

    public interface ListarEventosCallback {
        void onListarEventosSuccess(List<Evento> postList);
        void onListarEventosFailure(String errorMessage);
    }

    public void listarEventos(String token, ListarEventosCallback callback, String nome) {
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<EventoResponse> call = service.listarEventosPorNome(token, nome);

        call.enqueue(new Callback<EventoResponse>() {
            @Override
            public void onResponse(Call<EventoResponse> call, Response<EventoResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        callback.onListarEventosSuccess(response.body().getEventos());
                    } else if (response.code() == 400) {
                        callback.onListarEventosFailure("Está faltando algo");
                    }
                } else {
                    callback.onListarEventosFailure("Erro ao listar forum");
                }
            }

            @Override
            public void onFailure(Call<EventoResponse> call, Throwable throwable) {
                callback.onListarEventosFailure("Falha na chamada da API: " + throwable.getMessage());
            }
        });
    }
}
