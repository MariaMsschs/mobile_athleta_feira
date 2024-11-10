package com.example.mobile_athleta.UseCase;

import android.util.Log;
import com.example.mobile_athleta.models.Post;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClientSql;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetSeguidoresUseCase {
    public interface SeguidoresCallback {
        void onSeguidoresSuccess(String numero_seguidores);
        void onSeguidoresFailure(String errorMessage);
    }

    public void getSeguidores(String token, Long userId, SeguidoresCallback callback) {
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<Long> call = service.seguidores(token, userId);

        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        String numero_seguidores = String.valueOf(response.body());
                        callback.onSeguidoresSuccess(numero_seguidores);
                    } else if (response.code() == 400) {
                        callback.onSeguidoresFailure("Requisição inválida. Verifique os dados enviados.");
                    }
                } else {
                    callback.onSeguidoresFailure("Erro ao buscar seguidores");
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable throwable) {
                callback.onSeguidoresFailure("Falha na chamada da API: " + throwable.getMessage());
            }
        });
    }
}
