package com.example.mobile_athleta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mobile_athleta.UseCase.ListarForumPorNomeUseCase;
import com.example.mobile_athleta.UseCase.ListarPostagensPorForumUseCase;
import com.example.mobile_athleta.adapter.PostAdapter;
import com.example.mobile_athleta.databinding.ActivityTelaForumBinding;
import com.example.mobile_athleta.models.Forum;
import com.example.mobile_athleta.models.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TelaForum extends AppCompatActivity {

    private ActivityTelaForumBinding binding;
    private ListarForumPorNomeUseCase listarForumPorNomeUseCase = new ListarForumPorNomeUseCase();
    private ListarPostagensPorForumUseCase listarPostagensPorForumUseCase = new ListarPostagensPorForumUseCase();
    private PostAdapter postAdapter;
    private List<Post> postList;
    int pagina = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_forum);
        postList = new ArrayList<>();

        binding = ActivityTelaForumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String token = getSharedPreferences("login", MODE_PRIVATE).getString("token", null);
        String forum = getIntent().getStringExtra("forum");
        String username = getSharedPreferences("login", MODE_PRIVATE).getString("username", null);
        binding.recyclerViewForum.setLayoutManager(new LinearLayoutManager(TelaForum.this));

        postAdapter = new PostAdapter(postList, new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Post post) {

            }
        });

        binding.recyclerViewForum.setAdapter(postAdapter);
        binding.recyclerViewForum.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager.findLastVisibleItemPosition() == postList.size() - 1) {
                    binding.btnLoadMore.setVisibility(View.VISIBLE);
                } else {
                    binding.btnLoadMore.setVisibility(View.GONE);
                }
            }
        });

        listarForumPorNomeUseCase.listarForumPorNome(token, forum, new ListarForumPorNomeUseCase.ListarCallback() {
            @Override
            public void onListarSuccess(List<Forum> forum) {
                binding.titulo.setText(forum.get(0).getNome());
                binding.descricao.setText(forum.get(0).getDescricao());
                Picasso.get()
                        .load(forum.get(0).getImgForum())
                        .into(binding.forumImagemFrente);
                Picasso.get()
                        .load(forum.get(0).getImgFundo())
                        .into(binding.forumImagemTras);
            }

            @Override
            public void onListarFailure(String errorMessage) {

            }
        });

        listarPostagensPorForumUseCase.listarPostagensPorForum(forum, new ListarPostagensPorForumUseCase.VerificarCallback() {
            @Override
            public void onVerificarSuccess(List<Post> postList, List<Post> postListComCurtida) {
                if (postList.isEmpty()) {
                    binding.textViewNoResults.setVisibility(View.VISIBLE);
                    binding.erroRostoTriste.setVisibility(View.VISIBLE);
                } else {
                    binding.textViewNoResults.setVisibility(View.GONE);
                    binding.erroRostoTriste.setVisibility(View.GONE);
                    postAdapter.updatePostList(postList);

                    for (Post post : postList) {
                        boolean isLiked = postListComCurtida.contains(post);
                        postAdapter.curtir(post, isLiked);
                    }
                    mudarPagina();
                }
            }

            @Override
            public void onVerificarFailure(String errorMessage) {
                binding.textViewNoResults.setVisibility(View.VISIBLE);
                binding.erroRostoTriste.setVisibility(View.VISIBLE);
                binding.textViewNoResults.setText("Erro: " + errorMessage);
            }
        },pagina,15,username);

        binding.postar.setOnClickListener( v -> {
            Intent intent = new Intent(TelaForum.this, TelaPostagem.class);
            startActivity(intent);
        });
    }

    public void mudarPagina() {
        int numero = pagina + 1;
        pagina = numero;
    }
}