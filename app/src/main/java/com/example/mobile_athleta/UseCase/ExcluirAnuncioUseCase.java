package com.example.mobile_athleta.UseCase;

import android.content.Context;
import android.util.Log;

import com.example.mobile_athleta.models.Vendedor;
import com.example.mobile_athleta.service.ApiResponse;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClientSql;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExcluirAnuncioUseCase {
    public interface ExcluirAnuncioCallBack {
        void onExcluirAnuncioSuccess();
        void onExcluirAnuncioError();
    }

    public void excluirAnuncio(String token, Long id, ExcluirAnuncioCallBack callback) {
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<ApiResponse> call = service.excluirAnuncio(token, id);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("SUCESSO ExcluirAnuncio", "Sucesso ao excluir: " + id.toString());

                    callback.onExcluirAnuncioSuccess();
                }
                else {
                    Log.d("Erro ExcluirAnuncio", "code: " + response.code());
                    callback.onExcluirAnuncioError();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                Log.e("Erro ao excluir anúncio", throwable.getMessage());
                callback.onExcluirAnuncioError();
            }
        });
    }
}
