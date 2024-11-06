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
import android.widget.ImageView;
import com.example.mobile_athleta.R;
import com.example.mobile_athleta.TelaEsporte;
import com.example.mobile_athleta.TelaForum;
import com.example.mobile_athleta.UseCase.ListarEsportesUseCase;
import com.example.mobile_athleta.UseCase.ListarForunsUseCase;
import com.example.mobile_athleta.UseCase.ListarPostagensUseCase;
import com.example.mobile_athleta.adapter.EsporteCardAdapter;
import com.example.mobile_athleta.adapter.ForumAdapter;
import com.example.mobile_athleta.adapter.PostAdapter;
import com.example.mobile_athleta.models.Esporte;
import com.example.mobile_athleta.models.Forum;
import com.example.mobile_athleta.models.Post;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ListarEsportesUseCase listarEsportesUseCase = new ListarEsportesUseCase();
    private RecyclerView recyclerViewCards, recyclerViewPosts, recyclerViewForum;
    private EsporteCardAdapter esporteAdapter;
    private PostAdapter postAdapter;
    private ForumAdapter forumAdapter;
    private List<Esporte> esporteList;
    private List<Post> postList;
    private ListarPostagensUseCase listarPostagensUseCase = new ListarPostagensUseCase();
    private ListarForunsUseCase listarForunsUseCase = new ListarForunsUseCase();
    private List<Forum> forumList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Cards esporte
        recyclerViewCards = view.findViewById(R.id.cards_recycler);

        esporteList = new ArrayList<>();

        String token = getContext().getSharedPreferences("login", MODE_PRIVATE).getString("token", "");

        listarEsportesUseCase.listarEsportes(token, esportes ->  {
            esporteList = esportes;


            recyclerViewCards.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            esporteAdapter = new EsporteCardAdapter(esporteList, esporte -> {
                Intent infos = new Intent(getContext(), TelaEsporte.class);
                infos.putExtra("esporteId", esporte.getIdEsporte());
                startActivity(infos);
            });

            recyclerViewCards.setAdapter(esporteAdapter);
        });


        //recycler posts
        recyclerViewPosts = view.findViewById(R.id.recycler_posts);

        postList = new ArrayList<>();

        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        postAdapter = new PostAdapter(postList, post -> {

        });

        recyclerViewPosts.setAdapter(postAdapter);
        Long id = getContext().getSharedPreferences("login", Context.MODE_PRIVATE).getLong("idUsuario", 0L);
        String username = getContext().getSharedPreferences("login", Context.MODE_PRIVATE).getString("username", "");

        listarPostagensUseCase.listarPostagens(new ListarPostagensUseCase.VerificarCallback() {
            @Override
            public void onVerificarSuccess(List<Post> postList, List<Post> liked) {
                if (postList.isEmpty()) {
                    Log.e("Erro", "Sem Posts");
                } else {
                    postAdapter.updatePostList(postList);

                    for (Post post : postList) {
                        boolean isLiked = liked.contains(post);
                        postAdapter.curtir(post, isLiked);
                    }
                }
            }
            @Override
            public void onVerificarFailure(String errorMessage) {
            }

        }, 0, 5, username);

        //foruns
        recyclerViewForum = view.findViewById(R.id.recycler_forum);

        forumList = new ArrayList<>();

        recyclerViewForum.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        forumAdapter = new ForumAdapter(forumList, forum -> {
            Intent intent = new Intent(getContext(), TelaForum.class);
            Bundle bundle = new Bundle();
            bundle.putLong("idForum", forum.getId());
            bundle.putString("nome", forum.getNome());
            intent.putExtras(bundle);
            startActivity(intent);
        });
        recyclerViewForum.setAdapter(forumAdapter);

        listarForunsUseCase.listarForuns(token, new ListarForunsUseCase.ListarForunsCallback() {
            @Override
            public void onListarForunsSuccess(List<Forum> forumList) {
                forumAdapter.setListaFiltrada(forumList);
            }

            @Override
            public void onListarForunsFailure(String errorMessage) {
            }
        },0,5);
        return view;
    }
}