package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClientSql;

import retrofit2.Call;
import retrofit2.Callback;

public class ChecarVendedorUseCase {
    public interface ChecarVendedorCallBack {
        void onVendedorCheck(Boolean bol);
    }

    public void checarVendedor(String token, Long usuarioId, ChecarVendedorCallBack callback){
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<Boolean> call = service.checarVendedor(token, usuarioId);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, retrofit2.Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("SUCESSO ChecarVendedor", "Vendor exists: " + response.body());
                    callback.onVendedorCheck(response.body());
                } else {
                    Log.e("ERRO ChecarVendedor", "Failed response: Code " + response.code() + ", Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable throwable) {
                Log.e("Erro ListarAnuncioPorId", throwable.getMessage());
            }
        });
    }
}
