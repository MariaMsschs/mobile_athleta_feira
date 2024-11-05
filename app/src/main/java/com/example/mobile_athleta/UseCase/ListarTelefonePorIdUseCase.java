package com.example.mobile_athleta.UseCase;

import android.content.Context;
import android.util.Log;

import com.example.mobile_athleta.models.Usuario;
import com.example.mobile_athleta.models.Vendedor;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.RetrofitClientSql;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarTelefonePorIdUseCase {
    public interface ListarTelefoneCallBack {
        void onTelefoneRetornado();
        void onTelefoneError();
    }

    public void listarTelefonePorId(String token, Long id, Context context, ListarTelefoneCallBack callback) {
        AthletaService service = RetrofitClientSql.getAthletaService();
        Call<Vendedor> call = service.listarTelefonePorId(token, id);

        call.enqueue(new Callback<Vendedor>() {
            @Override
            public void onResponse(Call<Vendedor> call, Response<Vendedor> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Vendedor vendedor = response.body();
                    String telefone = vendedor.getTelefone();

                    Log.d("SUCESSO LISTARTELEFONE", "tel: " + telefone);

                    context.getSharedPreferences("anuncios", Context.MODE_PRIVATE)
                            .edit()
                            .putString("telefone", telefone)
                            .apply();

                    callback.onTelefoneRetornado();
                }
                else {
                    Log.d("Erro ao listar telefone", "code: " + response.code());
                    callback.onTelefoneError();
                }
            }

            @Override
            public void onFailure(Call<Vendedor> call, Throwable throwable) {
                Log.e("Erro ao listar telefone", throwable.getMessage());
                callback.onTelefoneError();
            }
        });
    }
}
