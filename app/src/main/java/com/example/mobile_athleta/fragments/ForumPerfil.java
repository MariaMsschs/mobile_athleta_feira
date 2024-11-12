package com.example.mobile_athleta.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.TelaForum;
import com.example.mobile_athleta.UseCase.ListarForumPorIdUseCase;
import com.example.mobile_athleta.UseCase.ListarForumPorOrganizador;
import com.example.mobile_athleta.adapter.ForumAdapter;
import com.example.mobile_athleta.models.Evento;
import com.example.mobile_athleta.models.Forum;

import java.util.ArrayList;
import java.util.List;

public class ForumPerfil extends Fragment {
    private ForumAdapter forumAdapter;
    private List<Forum> forumList;
    private TextView textViewNoResults;
    private ImageView imageNoResults;
    private Button btnLoadMore;
    int pagina = 0;
    private ListarForumPorIdUseCase listarForumPorIdUseCase = new ListarForumPorIdUseCase();

    private ListarForumPorOrganizador listarForumPorOrganizador = new ListarForumPorOrganizador();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum_perfil, container, false);

        RecyclerView recyclerViewForum = view.findViewById(R.id.recycler_forum_perfil);
        textViewNoResults = view.findViewById(R.id.textViewNoResultsForumPerfil);
        imageNoResults = view.findViewById(R.id.erro_rosto_triste);
        btnLoadMore = view.findViewById(R.id.btnLoadMore);

        String token = getContext().getSharedPreferences("login", MODE_PRIVATE).getString("token", "");
        Long usuarioId = getContext().getSharedPreferences("perfil", Context.MODE_PRIVATE).getLong("idPerfil", 0L);

        forumList = new ArrayList<>();
        recyclerViewForum.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        forumAdapter = new ForumAdapter(forumList, forum -> {
            Intent intent = new Intent(getContext(), TelaForum.class);
            Bundle bundle = new Bundle();
            bundle.putString("nome", forum.getNome());
            bundle.putLong("idForum", forum.getId());
            intent.putExtras(bundle);
            startActivity(intent);
        });
        recyclerViewForum.setAdapter(forumAdapter);


        listarForumPorOrganizador.listarForumPorOrganizador(token, usuarioId, new ListarForumPorOrganizador.ListarForumPorOrganizadorCallBack() {
            @Override
            public void onForumRetornado(List<Forum> forumList) {
                if (forumList.isEmpty()) {
                    textViewNoResults.setVisibility(View.VISIBLE);
                    imageNoResults.setVisibility(View.VISIBLE);
                } else {
                    textViewNoResults.setVisibility(View.GONE);
                    imageNoResults.setVisibility(View.GONE);
                    recyclerViewForum.setVisibility(View.VISIBLE);
                    forumAdapter.setListaFiltrada(forumList);
                }
            }

            @Override
            public void onForumErro(String error) {
                Log.d("ERRO", error);
                textViewNoResults.setVisibility(View.VISIBLE);
                imageNoResults.setVisibility(View.VISIBLE);
                btnLoadMore.setVisibility(View.GONE);
            }
        });

        return view;
    }

    public void mudarPagina() {
        int numero = pagina + 1;
        pagina = numero;
    }
}