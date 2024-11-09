package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.service.ApiResponse;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClientSql;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExcluirEventoUseCase {
    public interface ExcluirEventoCallBack {
        void onExcluirEventoSuccess();
        void onExcluirEventoError();
    }

    public void excluirEvento(String token, Long eventoId, ExcluirEventoCallBack callback) {
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<ApiResponse> call = service.excluirEvento(token, eventoId);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResponseSucessfull() == true) {
                    Log.d("SUCESSO ExcluirEvento", "Sucesso ao excluir: " + eventoId.toString());

                    callback.onExcluirEventoSuccess();
                }
                else {
                    Log.d("Erro ExcluirEvento", "code: " + response.code());
                    callback.onExcluirEventoError();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                Log.e("Erro ao excluir evento", throwable.getMessage());
                callback.onExcluirEventoError();
            }
        });
    }
}
