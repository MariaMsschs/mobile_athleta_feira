package com.example.mobile_athleta.service;

import com.example.mobile_athleta.models.Anuncio;
import com.example.mobile_athleta.models.RedisResponse;
import com.example.mobile_athleta.models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AthletaService {
    @PUT("api/usuario/atualizar/{id}")
    Call<ApiResponse> atualizarUsuario(
            @Header("Authorization") String token,
            @Body Usuario usuario,
            @Path("id") Long id);

    @PUT("api/usuario/mudarSenha")
    Call<ApiResponse> alterarSenha(@Header("Authorization") String token, @Body EmailLogin login);

    @GET("api/usuario/listar/{username}")
    Call<ApiResponse> listarUsuarioPorUsername(@Path("username") String username);

    @POST("api/auth/login")
    Call<LoginResponse> login(@Body UserLogin login);

    @POST("api/usuario/adicionar")
    Call<ApiResponse> cadastrarUsuario(@Body Usuario usuario);

    @GET("api/usuario/listar/username/{email}")
    Call<Usuario> listarUsuarioPorEmail(@Path("email") String email);

    @GET("api/anuncio/listar")
    Call<AnuncioResponse> listarAnuncios(@Header("Authorization") String token);

    @POST("set/{email}")
    Call<RedisResponse> mandarEmail(@Path("email") String email);

    @DELETE("delete/{key}/{codigo}")
    Call<RedisResponse> deletarEmail(@Path("key") String key, @Path("codigo") String codigo);
}