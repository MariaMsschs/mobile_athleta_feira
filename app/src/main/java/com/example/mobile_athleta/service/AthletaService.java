package com.example.mobile_athleta.service;

import com.example.mobile_athleta.models.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AthletaService {
    @PUT("/atualizar/{id}")
    Call<ApiResponse> atualizarUsuario(@Body Usuario usuario, @Path("id") Long id);

    @PUT("/mudarSenha")
    Call<ApiResponse> alterarSenha(@Body UserLogin login);

    @GET("/listar/{username}")
    Call<ApiResponse> listarUsuarioPorUsername(@Path("username") String username);

    @POST("/login")
    Call<ApiResponse> login(@Body UserLogin login);

    @POST("/adicionar")
    Call<ApiResponse> cadastrarUsuario(@Body Usuario usuario);

//    @PostMapping("/adicionar")
//    public ResponseEntity<ApiResponseAthleta> adicionarUsuario(@Valid @RequestBody Usuario usuario) {
//        try {
//            ApiResponseAthleta savedUserRole = usuarioService.cadastrarUsuario(usuario);
//            return ResponseEntity.status(HttpStatus.CREATED).body(savedUserRole);
//        } catch (DataIntegrityViolationException dive) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body( new ApiResponseAthleta(false, "Error", null, null));
//        }
//    }
}