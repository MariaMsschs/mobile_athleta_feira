package com.example.mobile_athleta.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.TelaPerfil;
import com.example.mobile_athleta.TelaPostagem;
import com.example.mobile_athleta.UseCase.ListarPostagensUseCase;
import com.example.mobile_athleta.UseCase.ListarUsuarioPorUsernameUsecase;
import com.example.mobile_athleta.adapter.PostAdapter;
import com.example.mobile_athleta.models.Post;
import com.example.mobile_athleta.models.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PostSocial extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public PostSocial() {
    }
    public static PostSocial newInstance(String param1, String param2) {
        PostSocial fragment = new PostSocial();
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

    private PostAdapter postAdapter;
    private List<Post> postList;
    private ListarPostagensUseCase listarPostagensUseCase = new ListarPostagensUseCase();
    private ListarUsuarioPorUsernameUsecase listarUsuarioPorUsernameUsecase = new ListarUsuarioPorUsernameUsecase();
    private RecyclerView recyclerViewPost;
    private Button btnLoadMore;
    private TextView textViewNoResults;
    private ImageView imageNoResults;
    private FrameLayout frameLayout;
    private FloatingActionButton floatingActionButton;
    int pagina = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_social, container, false);

        recyclerViewPost = view.findViewById(R.id.recycler_post_social);
        textViewNoResults = view.findViewById(R.id.textViewNoResults);
        imageNoResults = view.findViewById(R.id.erro_rosto_triste);
        btnLoadMore = view.findViewById(R.id.btnLoadMore);
        frameLayout = view.findViewById(R.id.frameLayoutSocial);
        floatingActionButton = view.findViewById(R.id.postar);

        frameLayout.setVisibility(ProgressBar.VISIBLE);
        postList = new ArrayList<>();


        recyclerViewPost.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        postAdapter = new PostAdapter(postList, new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Post post) {
            }

            @Override
            public void onFotoClick(Post post) {
                frameLayout.setVisibility(ProgressBar.VISIBLE);
                Intent intent = new Intent(getContext(), TelaPerfil.class);
                String token = getContext().getSharedPreferences("login", MODE_PRIVATE).getString("token", "");

                listarUsuarioPorUsernameUsecase.listarUsuarioPorUsername(token,post.getUsername(), new ListarUsuarioPorUsernameUsecase.ListarUsuarioPorUsernameCallBack() {
                    @Override
                    public void onListarUsuarioSuccess(Usuario response) {
                        Bundle bundle = new Bundle();
                        bundle.putString("nome",response.getNome());
                        bundle.putString("username", post.getUsername());
                        bundle.putString("url",post.getUserFoto());
                        bundle.putLong("userId",response.getIdUsuario());
                        intent.putExtras(bundle);
                        frameLayout.setVisibility(ProgressBar.GONE);
                        startActivity(intent);
                    }

                    @Override
                    public void onListarUsuarioFailure(String errorMessage) {

                    }
                });


            }
        });

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

//        String token = getContext().getSharedPreferences("login", Context.MODE_PRIVATE).getString("token", "");
//        Long id = getContext().getSharedPreferences("login", Context.MODE_PRIVATE).getLong("idUsuario", 0L);
        String username = getContext().getSharedPreferences("login", Context.MODE_PRIVATE).getString("username", "");

        listarPostagensUseCase.listarPostagens(new ListarPostagensUseCase.VerificarCallback() {
            @Override
            public void onVerificarSuccess(List<Post> postList, List<Post> liked) {
                if (postList.isEmpty()) {
                    frameLayout.setVisibility(View.GONE);
                    textViewNoResults.setVisibility(View.VISIBLE);
                    imageNoResults.setVisibility(View.VISIBLE);
                } else {
                    frameLayout.setVisibility(View.GONE);
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

        }, pagina, 15, username);



        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoadMore.setClickable(false);
                listarPostagensUseCase.listarPostagens(new ListarPostagensUseCase.VerificarCallback() {
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
                }, pagina, 15, username);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TelaPostagem.class);
                startActivity(intent);
            }
        });

        return view;
    }
  
    public void mudarPagina() {
        int numero = pagina + 1;
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