package com.example.mobile_athleta.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.UseCase.ListarPostagensPorIdUseCase;
import com.example.mobile_athleta.adapter.PostAdapter;
import com.example.mobile_athleta.models.Post;
import java.util.ArrayList;
import java.util.List;

public class PostPerfil extends Fragment {

    private RecyclerView recyclerViewPost;
    private TextView textViewNoResults;
    private ImageView imageNoResults;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private Button btnLoadMore;
    private ListarPostagensPorIdUseCase listarPostagensPorIdUseCase = new ListarPostagensPorIdUseCase();
    int pagina = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_perfil, container, false);

        recyclerViewPost = view.findViewById(R.id.recycler_post_perfil);
        textViewNoResults = view.findViewById(R.id.textViewNoResults);
        imageNoResults = view.findViewById(R.id.erro_rosto_triste);
        btnLoadMore = view.findViewById(R.id.btnLoadMore);


        postList = new ArrayList<>();
        postAdapter = new PostAdapter(postList, new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Post post) {
            }
            @Override
            public void onFotoClick(Post post) {

            }
        });

        recyclerViewPost.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPost.setAdapter(postAdapter);

        recyclerViewPost.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager.findLastVisibleItemPosition() == postList.size() - 1) {
                    btnLoadMore.setVisibility(View.VISIBLE);
                } else {
                    btnLoadMore.setVisibility(View.GONE);
                }
            }
        });

        Long id = getContext().getSharedPreferences("perfil", Context.MODE_PRIVATE).getLong("idPerfil", 0L);
        String username = getContext().getSharedPreferences("login", Context.MODE_PRIVATE).getString("username", "");

        listarPostagensPorIdUseCase.listarPostagensPorId(id, new ListarPostagensPorIdUseCase.VerificarCallback() {
            @Override
            public void onVerificarSuccess(List<Post> postList, List<Post> liked) {
                if (postList.isEmpty()) {
                    textViewNoResults.setVisibility(View.VISIBLE);
                    imageNoResults.setVisibility(View.VISIBLE);
                } else {
                    textViewNoResults.setVisibility(View.GONE);
                    imageNoResults.setVisibility(View.GONE);
                    postAdapter.updatePostList(postList);

                    for (Post post : postList) {
                        boolean isLiked = liked.contains(post);
                        postAdapter.curtir(post, isLiked);
                    }
                    mudarPagina();
                }
            }
            @Override
            public void onVerificarFailure(String errorMessage) {
                textViewNoResults.setVisibility(View.VISIBLE);
                imageNoResults.setVisibility(View.VISIBLE);
                textViewNoResults.setText("Erro: " + errorMessage);
            }
        }, pagina,15,username);

        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoadMore.setClickable(false);
                listarPostagensPorIdUseCase.listarPostagensPorId(id, new ListarPostagensPorIdUseCase.VerificarCallback() {
                    @Override
                    public void onVerificarSuccess(List<Post> postList,List<Post> liked) {
                        if (postList.isEmpty()) {
                            checkSeVazio();
                        } else {
                            textViewNoResults.setVisibility(View.GONE);
                            imageNoResults.setVisibility(View.GONE);
                            btnLoadMore.setVisibility(View.GONE);
                            postAdapter.updatePostList(postList);
                            btnLoadMore.setClickable(true);
                            mudarPagina();
                            for (Post post : postList) {
                                boolean isLiked = liked.contains(post);
                                postAdapter.curtir(post, isLiked);
                            }
                        }
                    }

                    @Override
                    public void onVerificarFailure(String errorMessage) {
                        textViewNoResults.setVisibility(View.VISIBLE);
                        imageNoResults.setVisibility(View.VISIBLE);
                        btnLoadMore.setVisibility(View.GONE);
                        textViewNoResults.setText("Erro: " + errorMessage);
                    }
                }, pagina,15,username);
            }
        });
        return view;
    }

    public void mudarPagina() {
        int numero = pagina + 1;
        getContext().getSharedPreferences("posts", Context.MODE_PRIVATE).edit().putInt("pagina", numero).apply();
        pagina = numero;
    }

    private void checkSeVazio() {
        if (postList.isEmpty()) {
            textViewNoResults.setVisibility(View.VISIBLE);
            imageNoResults.setVisibility(View.VISIBLE);
            btnLoadMore.setClickable(true);
        } else {
            textViewNoResults.setVisibility(View.GONE);
            imageNoResults.setVisibility(View.GONE);
            btnLoadMore.setVisibility(View.GONE);
            btnLoadMore.setClickable(true);
            Toast.makeText(getContext(), "Não possuimos mais postagens", Toast.LENGTH_SHORT).show();
        }
    }
}
