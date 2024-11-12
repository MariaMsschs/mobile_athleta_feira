package com.example.mobile_athleta.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.TelaEsporte;
import com.example.mobile_athleta.TelaForum;
import com.example.mobile_athleta.TelaPerfil;
import com.example.mobile_athleta.UseCase.ListarEsportesUseCase;
import com.example.mobile_athleta.UseCase.ListarForunsUseCase;
import com.example.mobile_athleta.UseCase.ListarPostagensUseCase;
import com.example.mobile_athleta.UseCase.ListarUsuarioPorUsernameUsecase;
import com.example.mobile_athleta.adapter.EsporteCardAdapter;
import com.example.mobile_athleta.adapter.ForumAdapter;
import com.example.mobile_athleta.adapter.PostAdapter;
import com.example.mobile_athleta.models.Esporte;
import com.example.mobile_athleta.models.Forum;
import com.example.mobile_athleta.models.Post;
import com.example.mobile_athleta.models.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
    private ListarUsuarioPorUsernameUsecase listarUsuarioPorUsernameUsecase = new ListarUsuarioPorUsernameUsecase();

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

        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        postAdapter = new PostAdapter(postList, new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Post post) {

            }

            @Override
            public void onFotoClick(Post post) {
                Intent perfil = new Intent(getContext(), TelaPerfil.class);
                String token = getContext().getSharedPreferences("login", MODE_PRIVATE).getString("token", "");
                Long userIdAtual = getContext().getSharedPreferences("login", MODE_PRIVATE).getLong("idUsuario", 0L);

                Fragment novoFragment = new PerfilFragment();

                listarUsuarioPorUsernameUsecase.listarUsuarioPorUsername(token,post.getUsername(), new ListarUsuarioPorUsernameUsecase.ListarUsuarioPorUsernameCallBack() {
                    @Override
                    public void onListarUsuarioSuccess(Usuario response) {
                        Bundle bundle = new Bundle();
                        bundle.putString("nome",response.getNome());
                        bundle.putString("username", post.getUsername());
                        bundle.putString("url",post.getUserFoto());
                        bundle.putLong("userId",response.getIdUsuario());

                        if(response.getIdUsuario() == userIdAtual){
                            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_conteudo_home, novoFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();

                            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomviewnav);
                            bottomNavigationView.setSelectedItemId(R.id.perfil);
                        }
                        else{
                            perfil.putExtras(bundle);
                            startActivity(perfil);
                        }
                    }

                    @Override
                    public void onListarUsuarioFailure(String errorMessage) {
                        Log.d("Falha ao listar usuário", errorMessage);
                    }
                });
            }
        });

        recyclerViewPosts.setAdapter(postAdapter);
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