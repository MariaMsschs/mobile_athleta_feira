package com.example.mobile_athleta.UseCase;

import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RelacionamentoUsuario;
import com.example.mobile_athleta.service.RetrofitClientSql;
import com.example.mobile_athleta.service.SeguidoresResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeixarDeSeguirUsuarioUseCase {

    public interface VerificarRelacionamentoCallback {
        void onSucess(boolean response);
        void onFailure(String errorMessage);
    }

    public void deixarDeSeguir(String token, Long id, VerificarRelacionamentoCallback callback) {

        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<SeguidoresResponse> call = service.pararDeSeguir(token, id);

        call.enqueue(new Callback<SeguidoresResponse>() {
            @Override
            public void onResponse(Call<SeguidoresResponse> call, Response<SeguidoresResponse> response) {

                if (response.isSuccessful()) {
                    SeguidoresResponse seguidoresResponse = response.body();
                    if (!seguidoresResponse.getExiste()) {
                        callback.onSucess(seguidoresResponse.getExiste());
                    } else {
                        callback.onFailure("Não");
                    }
                } else if (response.code() == 400) {
                    callback.onFailure("Conexão");
                }
            }

            @Override
            public void onFailure(Call<SeguidoresResponse> call, Throwable throwable) {

            }
        });
    }
}
