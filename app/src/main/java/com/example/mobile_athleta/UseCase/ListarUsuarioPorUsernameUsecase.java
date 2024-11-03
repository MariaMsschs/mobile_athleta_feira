package com.example.mobile_athleta.UseCase;

import android.util.Log;
import com.example.mobile_athleta.service.ApiResponse;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClientSql;
import retrofit2.Call;
import retrofit2.Callback;

public class ListarUsuarioPorUsernameUsecase {

    public void listarUsuarioPorUsername(String username){
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<ApiResponse> call = service.listarUsuarioPorUsername(username);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                Log.e("AlterarUsuarioUseCase", response.body().toString());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                Log.e("ERRO", throwable.getMessage());
            }
    });
    }
}
