package com.example.mobile_athleta.UseCase;
import android.util.Log;
import com.example.mobile_athleta.models.Usuario;
import com.example.mobile_athleta.service.ApiResponse;
import com.example.mobile_athleta.service.AthletaService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AtualizarUsuarioUseCase {
    private String URL = "https://api-spring-z2b5.onrender.com/api/usuario/";

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public void atualizarUsuario(Usuario usuario, Long id){
        AthletaService service = retrofit.create(AthletaService.class);
        Call<ApiResponse> call = service.atualizarUsuario(usuario, id);
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