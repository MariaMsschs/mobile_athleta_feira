package com.example.mobile_athleta.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.UseCase.ListarForumPorNomeUseCase;
import com.example.mobile_athleta.adapter.ForumAdapter;
import com.example.mobile_athleta.models.Forum;

import java.util.ArrayList;
import java.util.List;

public class ForumSocial extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public ForumSocial() {
    }

    public static ForumSocial newInstance(String param1, String param2) {
        ForumSocial fragment = new ForumSocial();
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
    private RecyclerView recyclerViewForum;
    private SearchView searchView;
    private TextView textViewNoResults;
    private ImageView imageNoResults;
    private ForumAdapter forumAdapter;
    private List<Forum> forumList;
    private ListarForumPorNomeUseCase listarForumPorNomeUseCase = new ListarForumPorNomeUseCase();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum_social, container, false);

        recyclerViewForum = view.findViewById(R.id.recycler_forum_social);
        searchView = view.findViewById(R.id.searchView);
        textViewNoResults = view.findViewById(R.id.textViewNoResults);
        imageNoResults = view.findViewById(R.id.erro_rosto_triste);

        forumList = new ArrayList<>();

        forumList.add(new Forum(1, "PingPros", "Comunidade de ping pong.", "user2","https://lastfm.freetls.fastly.net/i/u/avatar170s/c009cbba6eb44dfb9ba4081f30bfe46b", "https://lastfm.freetls.fastly.net/i/u/avatar170s/3dad5639665ad1b040cfb4071e95fb7a", 0));
        forumList.add(new Forum(2, "PingPros", "Comunidade de ping pong.", "user2", "https://lastfm.freetls.fastly.net/i/u/avatar170s/3dad5639665ad1b040cfb4071e95fb7a", "https://lastfm.freetls.fastly.net/i/u/avatar170s/c009cbba6eb44dfb9ba4081f30bfe46b", 0));
        forumList.add(new Forum(3, "PingPros", "Comunidade de ping pong.", "user2", "https://lastfm.freetls.fastly.net/i/u/avatar170s/c009cbba6eb44dfb9ba4081f30bfe46b", "https://lastfm.freetls.fastly.net/i/u/avatar170s/3dad5639665ad1b040cfb4071e95fb7a", 0));

        recyclerViewForum.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        forumAdapter = new ForumAdapter(forumList);
        recyclerViewForum.setAdapter(forumAdapter);

        searchView.setQueryHint("Buscar fóruns");
        String token = view.getContext().getSharedPreferences("login", Context.MODE_PRIVATE).getString("token", "");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listarForumPorNomeUseCase.verificarForuns(token, query, new ListarForumPorNomeUseCase.VerificarCallback() {
                    @Override
                    public void onVerificarSuccess(List<Forum> foruns, String message) {
                        forumAdapter.setListaFiltrada(foruns);
                        textViewNoResults.setVisibility(View.GONE);
                        imageNoResults.setVisibility(View.GONE);
                        recyclerViewForum.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onVerificarFailure(String errorMessage) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        return view;
    }

    private void filterList(String text) {
        List<Forum> listaFiltrada = new ArrayList<>();
        for (Forum forum : forumList) {
            if (forum.getNome().toLowerCase().contains(text.toLowerCase())) {
                listaFiltrada.add(forum);
            }
        }

        if (listaFiltrada.isEmpty()) {
            textViewNoResults.setVisibility(View.VISIBLE);
            imageNoResults.setVisibility(View.VISIBLE);
            recyclerViewForum.setVisibility(View.GONE);
        } else {
            textViewNoResults.setVisibility(View.GONE);
            imageNoResults.setVisibility(View.GONE);
            recyclerViewForum.setVisibility(View.VISIBLE);
            forumAdapter.setListaFiltrada(listaFiltrada);
        }
    }
}