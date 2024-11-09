package com.example.mobile_athleta.UseCase;

import com.example.mobile_athleta.models.Notificacao;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.NotificacaoResponse;
import com.example.mobile_athleta.service.RetrofitClientSql;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarNotificacoesUseCase {

    public interface OnListarNotificacoesCallback {
        void onListarNotificacoesSuccess(List<Notificacao> notificacaoList);
        void onListarNotificacoesError(String message);
    }

    public void listarNotificacoes(String token,Long id, OnListarNotificacoesCallback onListarNotificacoesCallback) {
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<NotificacaoResponse> call = service.listarNotificacoes(token, id);

        call.enqueue(new Callback<NotificacaoResponse>() {
            @Override
            public void onResponse(Call<NotificacaoResponse> call, Response<NotificacaoResponse> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 201) {
                        List<Notificacao> notificacaoList = response.body().getObject();
                        onListarNotificacoesCallback.onListarNotificacoesSuccess(notificacaoList);
                    } else if (response.code() == 400) {
                        onListarNotificacoesCallback.onListarNotificacoesError("Está faltando algo");
                    }
                } else {
                    onListarNotificacoesCallback.onListarNotificacoesError("Erro ao obter notificacoes");
                }
            }

            @Override
            public void onFailure(Call<NotificacaoResponse> call, Throwable throwable) {
                onListarNotificacoesCallback.onListarNotificacoesError("Falha na chamada da API: " + throwable.getMessage());
            }
        });
    }
}
