package com.example.mobile_athleta.UseCase;

import android.content.Context;
import android.util.Log;

import com.example.mobile_athleta.models.Usuario;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClientSql;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarTelefonePorIdUseCase {
    public interface ListarTelefoneCallBack {
        void onTelefoneRetornado(String telefone);
    }

//    public void listarTelefonePorId(String token, Long id, Context context, ListarUsuarioUseCase.ListarUsuarioCallBack callback) {
//        AthletaService service = RetrofitClientSql.getAthletaService();
//        Call<Usuario> call = service.listarTelef(id);
//
//        call.enqueue(new Callback<Usuario>() {
//            @Override
//            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Usuario usuario = response.body();
//                    String username = usuario.getUsername();
//
//                    context.getSharedPreferences("login", Context.MODE_PRIVATE)
//                            .edit()
//                            .putString("username", username)
//                            .apply();
//
//                    if (callback != null) {
//                        callback.onUsernameRetrieved(username);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Usuario> call, Throwable throwable) {
//                Log.e("Erro ao listar usuário", throwable.getMessage());
//                if (callback != null) {
//                    callback.onUsernameRetrieved("");
//                }
//            }
//        });
//    }
}
