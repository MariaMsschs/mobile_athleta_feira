package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.service.ApiResponse;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClientSql;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExcluirForumUseCase {
    public interface ExcluirForumCallBack {
        void onExcluirForumSuccess();
        void onExcluirForumError();
    }

    public void excluirForum(String token, Long id, ExcluirForumCallBack callback) {
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<ApiResponse> call = service.excluirForum(token, id);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("SUCESSO ExcluirForum", "Sucesso ao excluir: " + id.toString());

                    callback.onExcluirForumSuccess();
                }
                else {
                    Log.d("Erro ExcluirForum", "code: " + response.code());
                    callback.onExcluirForumError();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                Log.e("Erro ao excluir forum", throwable.getMessage());
                callback.onExcluirForumError();
            }
        });
    }
}
