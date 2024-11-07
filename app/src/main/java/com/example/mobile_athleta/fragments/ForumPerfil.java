package com.example.mobile_athleta.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.TelaForum;
import com.example.mobile_athleta.UseCase.ListarForumPorIdUseCase;
import com.example.mobile_athleta.UseCase.ListarForunsUseCase;
import com.example.mobile_athleta.adapter.ForumAdapter;
import com.example.mobile_athleta.adapter.PostAdapter;
import com.example.mobile_athleta.models.Forum;
import com.example.mobile_athleta.models.Post;

import java.util.ArrayList;
import java.util.List;

public class ForumPerfil extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ForumPerfil() {
    }

    public static ForumPerfil newInstance(String param1, String param2) {
        ForumPerfil fragment = new ForumPerfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ForumAdapter forumAdapter;
    private List<Forum> forumList;
    private TextView textViewNoResults;
    private ImageView imageNoResults;
    private Button btnLoadMore;
    int pagina = 0;
    private ListarForunsUseCase listarForunsUseCase = new ListarForunsUseCase();
    private ListarForumPorIdUseCase listarForumPorIdUseCase = new ListarForumPorIdUseCase();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum_perfil, container, false);

        RecyclerView recyclerViewForum = view.findViewById(R.id.recycler_forum_perfil);
        textViewNoResults = view.findViewById(R.id.textViewNoResultsForumPerfil);
        imageNoResults = view.findViewById(R.id.erro_rosto_triste);
        btnLoadMore = view.findViewById(R.id.btnLoadMore);

        forumList = new ArrayList<>();

        String token = getContext().getSharedPreferences("login", MODE_PRIVATE).getString("token", "");
        Long usuarioId = getContext().getSharedPreferences("perfil", Context.MODE_PRIVATE).getLong("idPerfil", 0L);

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

        listarForumPorIdUseCase.listarForumPorId(token, usuarioId, new ListarForumPorIdUseCase.ListarForumPorIdCallBack() {
            @Override
            public void onForumRetornado(Forum forum) {
                textViewNoResults.setVisibility(View.GONE);
                imageNoResults.setVisibility(View.GONE);
                recyclerViewForum.setVisibility(View.VISIBLE);
                forumAdapter.setListaFiltrada(forumList);
                mudarPagina();
            }
        });

        return view;
    }

    public void mudarPagina() {
        int numero = pagina + 1;
        pagina = numero;
    }
}