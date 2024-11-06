package com.example.mobile_athleta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.mobile_athleta.UseCase.ExcluirForumUseCase;
import com.example.mobile_athleta.UseCase.ListarForumPorIdUseCase;
import com.example.mobile_athleta.UseCase.ListarForumPorNomeUseCase;
import com.example.mobile_athleta.UseCase.ListarPostagensPorForumUseCase;
import com.example.mobile_athleta.adapter.PostAdapter;
import com.example.mobile_athleta.databinding.ActivityTelaForumBinding;
import com.example.mobile_athleta.models.Forum;
import com.example.mobile_athleta.models.Post;
import com.example.mobile_athleta.service.FotoFirebaseImpl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TelaForum extends AppCompatActivity {

    private ActivityTelaForumBinding binding;
    private ListarForumPorNomeUseCase listarForumPorNomeUseCase = new ListarForumPorNomeUseCase();
    private ListarPostagensPorForumUseCase listarPostagensPorForumUseCase = new ListarPostagensPorForumUseCase();
    private ListarForumPorIdUseCase listarForumPorIdUseCase = new ListarForumPorIdUseCase();
    private ExcluirForumUseCase excluirForumUseCase = new ExcluirForumUseCase();
    private FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();

    private PostAdapter postAdapter;
    Long criadorId;
    String fotoPerfil, fotoHeader;
    private List<Post> postList;
    int pagina = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaForumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.botaoExcluirForum.setVisibility(View.GONE);

        postList = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();

        String token = getSharedPreferences("login", MODE_PRIVATE).getString("token", null);
        String forum = bundle.getString("nome");
        String username = getSharedPreferences("login", MODE_PRIVATE).getString("username", null);
        Long forumId = bundle.getLong("idForum");
        Log.d("recuperacao??", forumId.toString());
        binding.recyclerViewForum.setLayoutManager(new LinearLayoutManager(TelaForum.this));

        listarForumPorId(token, forumId);

        postAdapter = new PostAdapter(postList, new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Post post) {

            }

            @Override
            public void onFotoClick(Post post) {

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

    public void excluirForum(String token, Long forumId){
        excluirForumUseCase.excluirForum(token, forumId, new ExcluirForumUseCase.ExcluirForumCallBack() {
            @Override
            public void onExcluirForumSuccess() {
                fotoFirebaseImpl.deletarFoto(fotoPerfil);
                fotoFirebaseImpl.deletarFoto(fotoHeader);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.anuncio_inserido_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(TelaForum.this);
                TextView texto = dialogView.findViewById(R.id.text);
                texto.setText("Forúm excluído com sucesso!");
                View botao = dialogView.findViewById(R.id.botao_inserido);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();
                botao.setOnClickListener(v2 -> {
                    Intent intent = new Intent(TelaForum.this, TelaHome.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                });
            }

            @Override
            public void onExcluirForumError() {
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.anuncio_erro_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(TelaForum.this);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();
                View botao_alterado = dialogView.findViewById(R.id.botao_erro_inserir);
                botao_alterado.setOnClickListener(v -> {
                    Intent intent = new Intent(TelaForum.this, TelaHome.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                });
            }
        });
    }

    public void listarForumPorId(String token, Long forumId){
        listarForumPorIdUseCase.listarForumPorId(token, forumId, forum -> {
            Long usuarioAtualId = getSharedPreferences("login", MODE_PRIVATE).getLong("idUsuario", 0L);
            criadorId = forum.getUsuarioResp();

            fotoPerfil = forum.getImgForum();
            fotoHeader = forum.getImgFundo();

            if (criadorId == usuarioAtualId) {
                binding.botaoExcluirForum.setVisibility(View.VISIBLE);

                binding.botaoExcluirForum.setOnClickListener(v ->
                        excluirForum(token, forumId)
                );
            }
            else{
                binding.botaoExcluirForum.setVisibility(View.GONE);
            }

            fotoFirebaseImpl.recuperarImagem(binding.forumImagemFrente, forum.getImgForum());
            fotoFirebaseImpl.recuperarImagem(binding.forumImagemTras, forum.getImgFundo());
            binding.titulo.setText(forum.getNome());
            binding.descricao.setText(forum.getDescricao());
        });
    }

    public void mudarPagina() {
        int numero = pagina + 1;
        pagina = numero;
    }
}