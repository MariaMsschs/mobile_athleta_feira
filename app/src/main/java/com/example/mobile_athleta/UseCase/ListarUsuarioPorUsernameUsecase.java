package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.models.Usuario;
import com.example.mobile_athleta.service.ApiResponse;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClientSql;
import com.example.mobile_athleta.service.UsuarioResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarUsuarioPorUsernameUsecase {

    public interface ListarUsuarioPorUsernameCallBack {
        void onListarUsuarioSuccess(Usuario  response);
        void onListarUsuarioFailure(String errorMessage);
    }

    public void listarUsuarioPorUsername(String token,String username, ListarUsuarioPorUsernameCallBack callback) {
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<UsuarioResponse> call = service.listarUsuarioPorUsername(token,username);

        call.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if(response.isSuccessful()){
                    Usuario usuario = response.body().getUsuario().get(0);
                    callback.onListarUsuarioSuccess(usuario);
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable throwable) {
                callback.onListarUsuarioFailure(throwable.getMessage());
            }
        });
    }
}
