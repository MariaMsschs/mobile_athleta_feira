package com.example.mobile_athleta.UseCase;

import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RelacionamentoForum;
import com.example.mobile_athleta.service.RelacionamentoForumResponse;
import com.example.mobile_athleta.service.RelacionamentoResponse;
import com.example.mobile_athleta.service.RetrofitClientSql;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeguirForumUseCase {

    public interface SeguirForumCallback {
        void onSeguirForumSuccess(RelacionamentoForum relacionamentoForum);
        void onSeguirForumError(String message);
    }

    public void seguir(String token, RelacionamentoForum relacionamentoForum, SeguirForumCallback callback) {
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<RelacionamentoForumResponse> call = service.seguirForum(token, relacionamentoForum);

        call.enqueue(new Callback<RelacionamentoForumResponse>() {
            @Override
            public void onResponse(Call<RelacionamentoForumResponse> call, Response<RelacionamentoForumResponse> response) {
                if (response.isSuccessful()) {
                    RelacionamentoForum relacionamentoResponse = response.body().getObject().get(0);
                    callback.onSeguirForumSuccess(relacionamentoResponse);
                }else{
                    callback.onSeguirForumError("Conexão");
                }
            }

            @Override
            public void onFailure(Call<RelacionamentoForumResponse> call, Throwable throwable) {
                callback.onSeguirForumError(throwable.getMessage());
            }
        });
    }
}
