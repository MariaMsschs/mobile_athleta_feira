package com.example.mobile_athleta.service;

import com.example.mobile_athleta.models.Anuncio;
import com.example.mobile_athleta.models.Comentario;
import com.example.mobile_athleta.models.Post;
import com.example.mobile_athleta.models.RedisResponse;
import com.example.mobile_athleta.models.Usuario;
import com.example.mobile_athleta.models.Vendedor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @GET("api/postagem/listar")
    Call<List<Post>> listarPostagens(
            @Query("pagina") int pagina,
            @Query("tamanho") int tamanho
    );
  
    @GET("api/postagem/listarPorId")
    Call<List<Post>> listarPostagemPorId(
            @Query("pagina") int pagina,
            @Query("tamanho") int tamanho,
            @Query("id") int id
    );

    @POST("api/postagem/like/{id}/{username}")
    Call<Post> interacaoCurtida(@Path("id") String id, @Path("username") String username);

    @GET("api/comentario/listar/{idPost}")
    Call<List<Comentario>> listarComentarios(@Path("idPost") String idPost);

    @POST("api/anuncio/inserir")
    Call<ApiResponse> inserirAnuncio(@Header("Authorization") String token, @Body Anuncio anuncio);

    @GET("api/postagem/listar/{forum}")
    Call<List<Post>> listarPostagensPorForum(@Path("forum") String forum, @Query("pagina") int pagina, @Query("tamanho") int tamanho);

    @POST("api/postagem/adicionar")
    Call<Post> inseririrPostagem(@Body Post post);

    @GET("api/forum/listar/{nome}")
    Call<ForumResponse> listarForumPorNome(@Header("Authorization") String token, @Path("nome") String nome);

    @GET("api/forum/listar")
    Call<ForumResponse> listarForuns(@Header("Authorization") String token,
                                     @Query("pagina") int pagina,
                                     @Query("tamanho") int tamanho);
    @GET("api/usuario/listarporid/{id}")
    Call<UsuarioResponse> listarUsuarioPorId(@Header("Authorization") String token, @Path("id") Long usuarioId);

    @GET("api/anuncio/listar/{id}")
    Call<AnuncioResponse> listarAnuncioPorId(@Header("Authorization") String token, @Path("id") Long anuncioId);

    @GET("api/esporte/listar")
    Call<EsporteResponse> listarEsportes(@Header("Authorization") String token);

    @GET("api/esporte/listar/{id}")
    Call<EsporteResponse> listarEsportePorId(@Header("Authorization") String token, @Path("id") Long idEsporte);

    @POST("api/vendedor/adicionar")
    Call<ApiResponse> cadastrarVendedor(@Header("Authorization") String token, @Body Vendedor vendedor);

    @GET("api/vendedor/existe/{id}")
    Call<Boolean> checarVendedor(@Header("Authorization") String token, @Path("id") Long idUsuario);

    @GET("api/vendedor/listar/telefone/{id}")
    Call<Vendedor> listarTelefonePorId(@Header("Authorization") String token, @Path("id") Long id);
}