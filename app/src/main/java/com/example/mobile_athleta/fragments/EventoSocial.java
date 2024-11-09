package com.example.mobile_athleta.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.TelaCadastroEvento;
import com.example.mobile_athleta.TelaCadastroVendedor;
import com.example.mobile_athleta.TelaEvento;
import com.example.mobile_athleta.UseCase.ChecarVendedorUseCase;
import com.example.mobile_athleta.UseCase.ListarEventosPorNomeUseCase;
import com.example.mobile_athleta.UseCase.ListarEventosUseCase;
import com.example.mobile_athleta.adapter.EventoAdapter;
import com.example.mobile_athleta.models.Evento;
import java.util.ArrayList;
import java.util.List;

public class EventoSocial extends Fragment {
    EventoAdapter eventoAdapter;
    List<Evento> eventoList;
    private SearchView searchView;
    private TextView textViewNoResults;
    private ImageView imageNoResults;

    private ImageButton adicionarEvento;
    private RecyclerView recyclerViewEvento;
    private ListarEventosUseCase listarEventosUseCase = new ListarEventosUseCase();
    private ListarEventosPorNomeUseCase listarEventosPorNomeUseCase = new ListarEventosPorNomeUseCase();

    private ChecarVendedorUseCase checarVendedorUseCase = new ChecarVendedorUseCase();

    int pagina = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evento_social, container, false);

        recyclerViewEvento = view.findViewById(R.id.recycler_evento_social);
        searchView = view.findViewById(R.id.searchView);
        textViewNoResults = view.findViewById(R.id.textViewNoResults);
        imageNoResults = view.findViewById(R.id.erro_rosto_triste);
        adicionarEvento = view.findViewById(R.id.criar_evento);

        searchView.setQueryHint("Buscar eventos");

        String token = getContext().getSharedPreferences("login", Context.MODE_PRIVATE).getString("token", "");
        Long usuarioId = getContext().getSharedPreferences("login", MODE_PRIVATE).getLong("idUsuario", 0L);

        eventoList = new ArrayList<>();

        recyclerViewEvento.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        eventoAdapter = new EventoAdapter(eventoList, evento -> {
            Intent intent = new Intent(getContext(), TelaEvento.class);
            Bundle bundle = new Bundle();
            bundle.putString("Nome", evento.getNome());
            bundle.putString("Descricao", evento.getDescricao());
            bundle.putString("Imagem", evento.getImg());
            intent.putExtras(bundle);
            startActivity(intent);
        });
        recyclerViewEvento.setAdapter(eventoAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listarEventosPorNomeUseCase.listarEventos(token, new ListarEventosPorNomeUseCase.ListarEventosCallback() {
                    @Override
                    public void onListarEventosSuccess(List<Evento> eventoList) {
                        textViewNoResults.setVisibility(View.GONE);
                        imageNoResults.setVisibility(View.GONE);
                        eventoAdapter.updateEventoList(eventoList);
                    }

                    @Override
                    public void onListarEventosFailure(String errorMessage) {
                        if (query.isEmpty()){
                            pagina = 0;
                            listarEventosUseCase.listarEventos(token, new ListarEventosUseCase.ListarEventosCallback() {
                                @Override
                                public void onListarEventosSuccess(List<Evento> postList) {
                                    textViewNoResults.setVisibility(View.GONE);
                                    imageNoResults.setVisibility(View.GONE);
                                    eventoAdapter.updateEventoList(eventoList);
                                    mudarPagina();
                                }

                                @Override
                                public void onListarEventosFailure(String errorMessage) {
                                    textViewNoResults.setVisibility(View.VISIBLE);
                                    imageNoResults.setVisibility(View.VISIBLE);
                                    textViewNoResults.setText("Ops! Parece que não temos eventos disponíveis");
                                }
                            },pagina,15);
                        }
                    }
                }, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    pagina = 0;
                    listarEventosUseCase.listarEventos(token, new ListarEventosUseCase.ListarEventosCallback() {
                        @Override
                        public void onListarEventosSuccess(List<Evento> postList) {
                            textViewNoResults.setVisibility(View.GONE);
                            imageNoResults.setVisibility(View.GONE);
                            eventoAdapter.updateEventoList(postList);
                            mudarPagina();
                        }

                        @Override
                        public void onListarEventosFailure(String errorMessage) {
                            recyclerViewEvento.setVisibility(View.GONE);
                            textViewNoResults.setVisibility(View.VISIBLE);
                            textViewNoResults.setText("Ops! Parece que não temos eventos disponíveis");
                            imageNoResults.setVisibility(View.VISIBLE);
                        }
                    },pagina,15);
                }
                return false;
            }
        });

        listarEventosUseCase.listarEventos(token, new ListarEventosUseCase.ListarEventosCallback() {
            @Override
            public void onListarEventosSuccess(List<Evento> eventoList) {
                textViewNoResults.setVisibility(View.GONE);
                imageNoResults.setVisibility(View.GONE);
                recyclerViewEvento.setVisibility(View.VISIBLE);
                eventoAdapter.setListaFiltrada(eventoList);
                mudarPagina();
            }

            @Override
            public void onListarEventosFailure(String errorMessage) {
                recyclerViewEvento.setVisibility(View.GONE);
                textViewNoResults.setVisibility(View.VISIBLE);
                textViewNoResults.setText("Ops! Parece que não temos eventos disponíveis");
                imageNoResults.setVisibility(View.VISIBLE);
            }
        },pagina,15);

        adicionarEvento.setOnClickListener(v -> {
            checarVendedorUseCase.checarVendedor(token, usuarioId, bol -> {
                if(bol == true){
                    Bundle bundle = new Bundle();
                    bundle.putString("tela", "evento");
                    Intent cadastroVendedor = new Intent(getContext(), TelaCadastroEvento.class);
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

    private void filterList(String text) {
        List<Evento> listaFiltrada = new ArrayList<>();
        for (Evento e : eventoList) {
            if (e.getNome().toLowerCase().contains(text.toLowerCase())) {
                listaFiltrada.add(e);
            }
        }

        if (listaFiltrada.isEmpty()) {
            textViewNoResults.setVisibility(View.VISIBLE);
            imageNoResults.setVisibility(View.VISIBLE);
            recyclerViewEvento.setVisibility(View.GONE);
        } else {
            textViewNoResults.setVisibility(View.GONE);
            imageNoResults.setVisibility(View.GONE);
            recyclerViewEvento.setVisibility(View.VISIBLE);
            eventoAdapter.setListaFiltrada(listaFiltrada);
        }
    }

    public void mudarPagina() {
        int numero = pagina + 1;
        getContext().getSharedPreferences("posts", Context.MODE_PRIVATE).edit().putInt("pagina", numero).apply();
        pagina = numero;
    }
}