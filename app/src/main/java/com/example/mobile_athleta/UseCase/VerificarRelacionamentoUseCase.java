package com.example.mobile_athleta.UseCase;

import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RelacionamentoUsuario;
import com.example.mobile_athleta.service.RetrofitClientSql;
import com.example.mobile_athleta.service.SeguidoresResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificarRelacionamentoUseCase {

    public interface VerificarRelacionamentoCallback {
        void onSucess();
        void onFailure(String errorMessage);
    }

    public void verificar(String token, RelacionamentoUsuario relacionamentoUsuario, VerificarRelacionamentoCallback callback) {
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<SeguidoresResponse> call = service.verificarRelacionamento(token, relacionamentoUsuario);

        call.enqueue(new Callback<SeguidoresResponse>() {
            @Override
            public void onResponse(Call<SeguidoresResponse> call, Response<SeguidoresResponse> response) {
                if (response.isSuccessful()) {
                        SeguidoresResponse seguidoresResponse = response.body();
                        if (seguidoresResponse.getExiste()) {
                            callback.onSucess();
                        } else {
                            callback.onFailure("Não");
                        }
                    } else if (response.code() == 400) {
                        callback.onFailure("Conexão");
                    }
            }

            @Override
            public void onFailure(Call<SeguidoresResponse> call, Throwable throwable) {
                callback.onFailure(throwable.getMessage());
            }
        });
    }
}
