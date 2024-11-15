package com.example.mobile_athleta.fragments;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.TelaEvento;
import com.example.mobile_athleta.UseCase.ListarEventosPorNomeUseCase;
import com.example.mobile_athleta.UseCase.ListarEventosPorOrganizador;
import com.example.mobile_athleta.UseCase.ListarEventosUseCase;
import com.example.mobile_athleta.adapter.EventoAdapter;
import com.example.mobile_athleta.adapter.ForumAdapter;
import com.example.mobile_athleta.models.Evento;
import com.example.mobile_athleta.models.Forum;
import com.example.mobile_athleta.service.ValidacaoCadastroImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventoPerfil extends Fragment {
    ValidacaoCadastroImpl validacaoCadastroImpl = new ValidacaoCadastroImpl();
    private ListarEventosPorOrganizador listarEventosPorOrganizador = new ListarEventosPorOrganizador();
    int pagina = 0;
    private EventoAdapter eventoAdapter;
    private List<Evento> eventoList;
    private RecyclerView recyclerViewEvento;
    private TextView textViewNoResults;
    private ImageView imageNoResults;
    private Button btnMaisEventos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_evento_perfil, container, false);

        recyclerViewEvento = view.findViewById(R.id.recycler_evento_perfil);
        textViewNoResults = view.findViewById(R.id.textViewNoResults);
        imageNoResults = view.findViewById(R.id.erro_rosto_triste);
        btnMaisEventos = view.findViewById(R.id.btnLoadMore);

        String token = getContext().getSharedPreferences("login", Context.MODE_PRIVATE).getString("token", "");
        Long id = getContext().getSharedPreferences("perfil", Context.MODE_PRIVATE).getLong("idPerfil", 0L);

        eventoList = new ArrayList<>();

        recyclerViewEvento.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        eventoAdapter = new EventoAdapter(eventoList, evento -> {
            Intent intent = new Intent(getContext(), TelaEvento.class);
            Bundle bundle = new Bundle();
            bundle.putLong("eventoUserId", evento.getOrganizador());
            bundle.putLong("eventoId", evento.getIdEvento());
            bundle.putString("Nome", evento.getNome());
            bundle.putString("Descricao", evento.getDescricao());
            bundle.putString("Imagem", evento.getImg());
            bundle.putString("token", token);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        recyclerViewEvento.setAdapter(eventoAdapter);

        recyclerViewEvento.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager.findLastVisibleItemPosition() == eventoList.size() - 1) {
                    btnMaisEventos.setVisibility(View.VISIBLE);
                } else {
                    btnMaisEventos.setVisibility(View.GONE);
                }
            }
        });

        listarEventosPorOrganizador.listarEventos(token, new ListarEventosPorOrganizador.ListarEventosCallBack() {
            @Override
            public void onSuccess(List<Evento> eventoList) {
                if (eventoList.isEmpty()) {
                    textViewNoResults.setVisibility(View.VISIBLE);
                    imageNoResults.setVisibility(View.VISIBLE);
                    btnMaisEventos.setVisibility(View.GONE);
                } else {
                    textViewNoResults.setVisibility(View.GONE);
                    imageNoResults.setVisibility(View.GONE);
                    btnMaisEventos.setVisibility(View.VISIBLE);
                    eventoAdapter.setListaFiltrada(eventoList);
                    mudarPagina();
                }
            }

            @Override
            public void onError(String error) {
                if(error.equals("Vazio")){
                    textViewNoResults.setVisibility(View.VISIBLE);
                    imageNoResults.setVisibility(View.VISIBLE);
                    btnMaisEventos.setVisibility(View.GONE);
                }
            }
        }, id.intValue(),pagina,15);

        btnMaisEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarEventosPorOrganizador.listarEventos(token, new ListarEventosPorOrganizador.ListarEventosCallBack() {
                    @Override
                    public void onSuccess(List<Evento> eventoList) {
                        if (eventoList.isEmpty()) {
                            textViewNoResults.setVisibility(View.VISIBLE);
                            imageNoResults.setVisibility(View.VISIBLE);
                            btnMaisEventos.setVisibility(View.GONE);
                        } else {
                            textViewNoResults.setVisibility(View.GONE);
                            imageNoResults.setVisibility(View.GONE);
                            btnMaisEventos.setVisibility(View.VISIBLE);
                            eventoAdapter.setListaFiltrada(eventoList);
                            mudarPagina();
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                }, id.intValue(),pagina,15);
            }
        });

       return view;
    }



    public void mudarPagina() {
        int numero = pagina + 1;
        getContext().getSharedPreferences("posts", Context.MODE_PRIVATE).edit().putInt("pagina", numero).apply();
        pagina = numero;
    }
}