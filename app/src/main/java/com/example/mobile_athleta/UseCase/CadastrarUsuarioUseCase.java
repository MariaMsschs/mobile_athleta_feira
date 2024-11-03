package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.models.Usuario;
import com.example.mobile_athleta.service.ApiResponse;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClientSql;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;

public class CadastrarUsuarioUseCase {
    public interface CadastroCallBack {
        void onCadastroSuccess();
    }

    public void cadastrarUsuario(Usuario usuario, CadastroCallBack callback) {
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<ApiResponse> call = service.cadastrarUsuario(usuario);
      
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("SUCESSO CADASTRO", new Gson().toJson(usuario));
                    callback.onCadastroSuccess();

                } else {
                    Log.e("ERRO CADASTRO", "Response is not successful or body is null. Code: " + response.code() + ", Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                Log.e("Erro ao cadastrar usuário", throwable.getMessage());
            }
        });
    }
}
