package com.example.mobile_athleta.service;

import com.example.mobile_athleta.models.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AthletaService {
    @PUT("/atualizar/{id}")
    Call<ApiResponse> atualizarUsuario(@Body Usuario usuario, @Path("id") Long id);

    @PUT("/mudarSenha")
    Call<ApiResponse> alterarSenha(@Body UserLogin login);
}