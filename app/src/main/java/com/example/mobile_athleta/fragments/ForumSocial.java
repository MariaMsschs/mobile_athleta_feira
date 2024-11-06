package com.example.mobile_athleta.fragments;

import static android.content.Context.MODE_PRIVATE;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mobile_athleta.R;
import com.example.mobile_athleta.TelaCadastroForum;
import com.example.mobile_athleta.TelaCadastroVendedor;
import com.example.mobile_athleta.TelaForum;
import com.example.mobile_athleta.UseCase.ChecarVendedorUseCase;
import com.example.mobile_athleta.UseCase.ListarForumPorNomeUseCase;
import com.example.mobile_athleta.UseCase.ListarForunsUseCase;
import com.example.mobile_athleta.adapter.ForumAdapter;
import com.example.mobile_athleta.models.Forum;
import java.util.ArrayList;
import java.util.List;

public class ForumSocial extends Fragment {

    private RecyclerView recyclerViewForum;
    private SearchView searchView;
    private TextView textViewNoResults;
    private ImageView imageNoResults;
    private ForumAdapter forumAdapter;
    private List<Forum> forumList;

    private Button btnLoadMore;
    private ImageButton adicionarForum;
    int pagina = 0;
    private ListarForunsUseCase listarForunsUseCase = new ListarForunsUseCase();
    private ListarForumPorNomeUseCase listarForumPorNomeUseCase = new ListarForumPorNomeUseCase();
    private ChecarVendedorUseCase checarVendedorUseCase = new ChecarVendedorUseCase();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum_social, container, false);

        recyclerViewForum = view.findViewById(R.id.recycler_forum_social);
        searchView = view.findViewById(R.id.searchView);
        textViewNoResults = view.findViewById(R.id.textViewNoResults);
        imageNoResults = view.findViewById(R.id.erro_rosto_triste);
        btnLoadMore = view.findViewById(R.id.btnLoadMore);
        adicionarForum = view.findViewById(R.id.criar_forum);

        searchView.setQueryHint("Buscar fóruns");

        String token = getContext().getSharedPreferences("login", MODE_PRIVATE).getString("token", "");
        Long usuarioId = getContext().getSharedPreferences("login", MODE_PRIVATE).getLong("idUsuario", 0L);


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
                    pagina = 0;
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

        adicionarForum.setOnClickListener(v -> {
            checarVendedorUseCase.checarVendedor(token, usuarioId, bol -> {
                if(bol == true){
                    Bundle bundle = new Bundle();
                    bundle.putString("tela", "forum");
                    Intent cadastroVendedor = new Intent(getContext(), TelaCadastroForum.class);
                    cadastroVendedor.putExtras(bundle);
                    startActivity(cadastroVendedor);
                }
                else{
                    View dialogView = inflater.inflate(R.layout.vendedor_dialog, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    View botao = dialogView.findViewById(R.id.botao_vendedor_dialog);
                    builder.setView(dialogView);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    botao.setOnClickListener(v2 -> {
                        Intent config = new Intent(getContext(), TelaCadastroVendedor.class);
                        startActivity(config);
                        dialog.dismiss();
                    });
                }
            });
        });

        return view;
    }

    public void mudarPagina() {
        int numero = pagina + 1;
        pagina = numero;
    }
}