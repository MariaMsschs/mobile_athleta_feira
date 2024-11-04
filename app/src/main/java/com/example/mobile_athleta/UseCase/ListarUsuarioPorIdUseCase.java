package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.models.Usuario;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClientSql;
import com.example.mobile_athleta.service.UsuarioResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarUsuarioPorIdUseCase {
    public interface ListarUsuarioPorIdCallBack {
        void onUsuarioRetrieved(Usuario usuario);
    }

    public void listarUsuarioPorId(String token, Long usuarioId, ListarUsuarioPorIdCallBack callback) {
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<UsuarioResponse> call = service.listarUsuarioPorId(token, usuarioId);

        call.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("SUCESSO listarUsuarioPorId", response.body().toString());

                    Usuario usuario = response.body().getUsuario().get(0);

                    callback.onUsuarioRetrieved(usuario);
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable throwable) {
                Log.e("Erro ao listar usuário por id", throwable.getMessage());
            }
        });
    }
}
