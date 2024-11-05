package com.example.mobile_athleta.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
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
//import com.example.mobile_athleta.UseCase.ListarForumPorNomeUseCase;
import com.example.mobile_athleta.TelaForum;
import com.example.mobile_athleta.UseCase.ListarForumPorNomeUseCase;
import com.example.mobile_athleta.UseCase.ListarForunsUseCase;
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
    private ListarForunsUseCase listarForunsUseCase = new ListarForunsUseCase();
    private ListarForumPorNomeUseCase listarForumPorNomeUseCase = new ListarForumPorNomeUseCase();
    private Button btnLoadMore;
    int pagina =0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum_social, container, false);

        recyclerViewForum = view.findViewById(R.id.recycler_forum_social);
        searchView = view.findViewById(R.id.searchView);
        textViewNoResults = view.findViewById(R.id.textViewNoResults);
        imageNoResults = view.findViewById(R.id.erro_rosto_triste);
        btnLoadMore = view.findViewById(R.id.btnLoadMore);

        forumList = new ArrayList<>();

        recyclerViewForum.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        forumAdapter = new ForumAdapter(forumList, new ForumAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Forum forum) {
                Intent intent = new Intent(getContext(), TelaForum.class);
                intent.putExtra("forum", forum.getNome());
                startActivity(intent);
            }
        });
        recyclerViewForum.setAdapter(forumAdapter);
        recyclerViewForum.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager.findLastVisibleItemPosition() == forumList.size() - 1) {
                    btnLoadMore.setVisibility(View.VISIBLE);
                } else {
                    btnLoadMore.setVisibility(View.GONE);
                }
            }
        });



        searchView.setQueryHint("Buscar fóruns");
        String token = view.getContext().getSharedPreferences("login", Context.MODE_PRIVATE).getString("token", "");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listarForumPorNomeUseCase.listarForumPorNome(token, query, new ListarForumPorNomeUseCase.ListarCallback() {
                    @Override
                    public void onListarSuccess(List<Forum> forum) {
                        textViewNoResults.setVisibility(View.GONE);
                        imageNoResults.setVisibility(View.GONE);
                        recyclerViewForum.setVisibility(View.VISIBLE);
                        forumAdapter.setListaFiltrada(forum);
                    }

                    @Override
                    public void onListarFailure(String errorMessage) {
                        recyclerViewForum.setVisibility(View.GONE);
                        textViewNoResults.setVisibility(View.VISIBLE);
                        imageNoResults.setVisibility(View.VISIBLE);
                    }
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    listarForunsUseCase.listarForuns(token, new ListarForunsUseCase.ListarForunsCallback() {
                        @Override
                        public void onListarForunsSuccess(List<Forum> forumList) {
                            textViewNoResults.setVisibility(View.GONE);
                            imageNoResults.setVisibility(View.GONE);
                            recyclerViewForum.setVisibility(View.VISIBLE);
                            forumAdapter.setListaFiltrada(forumList);
                        }

                        @Override
                        public void onListarForunsFailure(String errorMessage) {
                            recyclerViewForum.setVisibility(View.GONE);
                            textViewNoResults.setVisibility(View.VISIBLE);
                            imageNoResults.setVisibility(View.VISIBLE);
                        }
                    },pagina,15);
                }
                return false;
            }
        });

        listarForunsUseCase.listarForuns(token, new ListarForunsUseCase.ListarForunsCallback() {
            @Override
            public void onListarForunsSuccess(List<Forum> forumList) {
                textViewNoResults.setVisibility(View.GONE);
                imageNoResults.setVisibility(View.GONE);
                recyclerViewForum.setVisibility(View.VISIBLE);
                forumAdapter.setListaFiltrada(forumList);
                mudarPagina();
            }

            @Override
            public void onListarForunsFailure(String errorMessage) {
                recyclerViewForum.setVisibility(View.GONE);
                textViewNoResults.setVisibility(View.VISIBLE);
                imageNoResults.setVisibility(View.VISIBLE);
            }
        },pagina,15);

        btnLoadMore.setOnClickListener(v -> {
            btnLoadMore.setClickable(false);
            listarForunsUseCase.listarForuns(token, new ListarForunsUseCase.ListarForunsCallback() {
                @Override
                public void onListarForunsSuccess(List<Forum> forumList) {
                    textViewNoResults.setVisibility(View.GONE);
                    imageNoResults.setVisibility(View.GONE);
                    recyclerViewForum.setVisibility(View.VISIBLE);
                    btnLoadMore.setVisibility(View.GONE);
                    forumAdapter.setListaFiltrada(forumList);
                    btnLoadMore.setClickable(true);
                    mudarPagina();
                }

                @Override
                public void onListarForunsFailure(String errorMessage) {
                    recyclerViewForum.setVisibility(View.GONE);
                    textViewNoResults.setVisibility(View.VISIBLE);
                    imageNoResults.setVisibility(View.VISIBLE);
                    btnLoadMore.setVisibility(View.GONE);
                    btnLoadMore.setClickable(true);
                }
            },pagina,15);
        });


        return view;
    }

    public void mudarPagina() {
        int numero = pagina + 1;
        pagina = numero;
    }
}