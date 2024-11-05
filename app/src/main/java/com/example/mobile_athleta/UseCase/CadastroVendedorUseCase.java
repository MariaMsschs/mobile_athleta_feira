package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.models.Usuario;
import com.example.mobile_athleta.models.Vendedor;
import com.example.mobile_athleta.service.ApiResponse;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClientSql;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;

public class CadastroVendedorUseCase {
    public interface CadastroVendedorCallBack {
        void onCadastroVendedorSuccess();
        void onCadastroVendedorErro();
    }

    public void cadastrarVendedor(String token, Vendedor vendedor, CadastroVendedorCallBack callback) {
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<ApiResponse> call = service.cadastrarVendedor(token, vendedor);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("SUCESSO CADASTRO VENDEDOR", new Gson().toJson(vendedor));
                    callback.onCadastroVendedorSuccess();

                } else {
                    Log.e("ERRO CADASTRO VENDEDOR", "Response is not successful or body is null. Code: " + response.code() + ", Message: " + response.message());
                    callback.onCadastroVendedorErro();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                Log.e("ERRO ao cadastrar vendedor", throwable.getMessage());
                callback.onCadastroVendedorErro();
            }
        });
    }
}
