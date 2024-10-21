package com.example.mobile_athleta.UseCase;

import android.util.Log;

import com.example.mobile_athleta.models.Usuario;
import com.example.mobile_athleta.service.ApiResponse;
import com.example.mobile_athleta.service.AthletaService;
import com.example.mobile_athleta.service.UserLogin;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListarUsuarioUseCase {
    private String URL = "https://api-spring-z2b5.onrender.com/api/usuario/";

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public String listarUsuarioPorEmail(String email) {
        AthletaService service = retrofit.create(AthletaService.class);
        Response<Usuario> response = service.listarUsuarioPorEmail(email);
        Usuario usuario = response.body();
        if (usuario != null) {
            return usuario.getUsername();
        }else {
            return null;
        }
    }
}
