package com.example.mobile_athleta.UseCase;

import com.example.mobile_athleta.service.ApiResponse;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RelacionamentoResponse;
import com.example.mobile_athleta.service.RelacionamentoUsuario;
import com.example.mobile_athleta.service.RetrofitClientSql;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeguirUsuarioUseCase {

    public interface VerificarRelacionamentoCallback {
        void onSucess(RelacionamentoUsuario relacionamentoUsuario);
        void onFailure(String errorMessage);
    }

    public void seguir(String token, RelacionamentoUsuario relacionamentoUsuario, VerificarRelacionamentoCallback callback) {

        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<RelacionamentoResponse> call = service.seguir(token, relacionamentoUsuario);

        call.enqueue(new Callback<RelacionamentoResponse>() {
            @Override
            public void onResponse(Call<RelacionamentoResponse> call, Response<RelacionamentoResponse> response) {

                if (response.isSuccessful()) {
                        RelacionamentoUsuario relacionamentoResponse = response.body().getObject().get(0);
                        callback.onSucess(relacionamentoResponse);{
                    }
                }else{
                    callback.onFailure("Conexão");
                }
            }

            @Override
            public void onFailure(Call<RelacionamentoResponse> call, Throwable throwable) {
                callback.onFailure(throwable.getMessage());
            }
        });
    }
}
