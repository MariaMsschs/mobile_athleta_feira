package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mobile_athleta.UseCase.ListarComentariosUseCase;
import com.example.mobile_athleta.adapter.ComentarioAdapter;
import com.example.mobile_athleta.models.Comentario;

import java.util.ArrayList;
import java.util.List;

public class TelaComentarios extends AppCompatActivity {
    private RecyclerView recyclerViewComentarios;
    private ComentarioAdapter comentarioAdapter;
    private List<Comentario> comentariosList;
    private ListarComentariosUseCase listarComentariosUseCase = new ListarComentariosUseCase();
    private ImageButton botaoVoltar;

    private TextView textViewComentario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_comentarios);
        recyclerViewComentarios = findViewById(R.id.recycler_view_comentarios);
        textViewComentario = findViewById(R.id.textViewNoResults);
        botaoVoltar = findViewById(R.id.botao_voltar);

        recyclerViewComentarios.setLayoutManager(new LinearLayoutManager(this));
        comentariosList = new ArrayList<>();

        comentarioAdapter = new ComentarioAdapter(comentariosList, new ComentarioAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Comentario comentario) {

            }
        });

        recyclerViewComentarios.setAdapter(comentarioAdapter);

        Bundle bundle = getIntent().getExtras();
        String postId = bundle.getString("idPost");

        listarComentariosUseCase.listarComentarios(postId, new ListarComentariosUseCase.VerificarCallback() {
            @Override
            public void onVerificarSuccess(List<Comentario> comentarios, String message) {
                if (comentarios.isEmpty()) {
                    textViewComentario.setVisibility(View.VISIBLE);
                }
                else {
                    textViewComentario.setVisibility(View.GONE);
                    comentarioAdapter.updateComentariosList(comentarios);
                }
            }

            @Override
            public void onVerificarFailure(String errorMessage) {

            }
        });

        botaoVoltar.setOnClickListener(v -> finish());
    }
}