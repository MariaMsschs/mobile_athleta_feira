package com.example.mobile_athleta.UseCase;

import android.util.Log;
import com.example.mobile_athleta.models.Usuario;
import com.example.mobile_athleta.service.ApiResponse;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;

public class AtualizarUsuarioUseCase {

    public void atualizarUsuario(String token, Usuario usuario, Long id){
        AthletaService service = RetrofitClient.getAthletaService();
        Call<ApiResponse> call = service.atualizarUsuario(token, usuario, id);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                if(response.isSuccessful()){
                    Log.e("ALTERAR SUCESSO", "sucesso");
                }
                else{
                    Log.e("ALTERAR ERRO", "Response is not successful. Code: " + response.code() + ", Message: " + response.message());

                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                Log.e("ALTERAR ERRO", throwable.getMessage());
            }
        });
    }
}