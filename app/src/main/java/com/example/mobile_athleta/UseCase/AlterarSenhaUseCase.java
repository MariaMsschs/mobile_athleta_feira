package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.service.ApiResponse;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.EmailLogin;
import com.example.mobile_athleta.service.RetrofitClientSql;

import retrofit2.Call;
import retrofit2.Callback;

public class AlterarSenhaUseCase {

    public void alterarSenha(String token, EmailLogin login){
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<ApiResponse> call = service.alterarSenha(token, login);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                if(response.isSuccessful()){
                    Log.e("SUCESSO ALTERAR SENHA", response.body().toString());
                }
                else{
                    Log.e("ERRO ALTERAR SENHA", "Response is not successful. Code: " + response.code() + ", Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                Log.e("ERRO ALTERAR SENHA", throwable.getMessage());
            }
        });
    }
}
