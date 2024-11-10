package com.example.mobile_athleta.UseCase;

import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClientSql;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetSeguindoUseCase {
    public interface SeguindoCallback {
        void onSeguindoSuccess(String numero_seguindo);
        void onSeguindoFailure(String errorMessage);
    }

    public void getSeguindo(String token, Long userId, SeguindoCallback callback) {
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<Long> call = service.seguindo(token, userId);

        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        String numero_seguindo = String.valueOf(response.body());
                        callback.onSeguindoSuccess(numero_seguindo);
                    } else if (response.code() == 400) {
                        callback.onSeguindoFailure("Requisição inválida. Verifique os dados enviados.");
                    }
                } else {
                    callback.onSeguindoFailure("Erro buscar seguindo");
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable throwable) {
                callback.onSeguindoFailure("Falha na chamada da API: " + throwable.getMessage());
            }
        });
    }
}
