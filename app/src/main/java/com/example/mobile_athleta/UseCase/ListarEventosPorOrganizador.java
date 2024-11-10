package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.models.Evento;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.EventoResponse;
import com.example.mobile_athleta.service.RetrofitClientSql;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarEventosPorOrganizador {

    public interface ListarEventosCallBack{

        void onSuccess(List<Evento> eventoList);
        void onError(String error);
    }

    public void listarEventos(String token, ListarEventosCallBack callback, int organizador, int pagina, int tamanho){

        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<EventoResponse> call = service.listarEventosPorOrganizador(token, organizador,pagina,tamanho);

        call.enqueue(new Callback<EventoResponse>() {
            @Override
            public void onResponse(Call<EventoResponse> call, Response<EventoResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.d("Sucesso ListarEventos", response.body().toString());
                        callback.onSuccess(response.body().getEventos());
                    } else if (response.code() == 400) {
                        callback.onError("Está faltando algo");
                    }
                } else {
                    if (response.code() == 400) {
                        callback.onError("Vazio");
                    }
                    callback.onError("Erro ao listar forum");
                }
            }

            @Override
            public void onFailure(Call<EventoResponse> call, Throwable throwable) {
                callback.onError("Falha na chamada da API: " + throwable.getMessage());
            }
        });

    }

}
