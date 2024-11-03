package com.example.mobile_athleta.service;

import com.example.mobile_athleta.models.Anuncio;
import com.example.mobile_athleta.models.RedisResponse;
import com.example.mobile_athleta.models.Usuario;
import com.google.android.gms.common.api.Api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @POST("api/anuncio/inserir")
    Call<ApiResponse> inserirAnuncio(@Header("Authorization") String token, @Body Anuncio anuncio);

    @GET("api/anuncio/listar/{id}")
    Call<AnuncioResponse> listarAnuncioPorId(@Header("Authorization") String token, @Path("id") Long anuncioId);

    @GET("api/usuario/listarporid/{id}")
    Call<UsuarioResponse> listarUsuarioPorId(@Header("Authorization") String token, @Path("id") Long usuarioId);
}