package com.example.mobile_athleta.fragments;

import android.content.Context;
import android.os.Bundle;

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
    public EventoPerfil() {
    }

    public static EventoPerfil newInstance() {
        EventoPerfil fragment = new EventoPerfil();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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

        String dateString = "06-02-2008";

        String dataFormatada = validacaoCadastroImpl.converterDataInterface(dateString);

        eventoList = new ArrayList<>();

        recyclerViewEvento.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        eventoAdapter = new EventoAdapter(eventoList, new EventoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Evento evento) {
            }
        });
        recyclerViewEvento.setAdapter(eventoAdapter);

        String token = getContext().getSharedPreferences("login", Context.MODE_PRIVATE).getString("token", "");
        Long id = getContext().getSharedPreferences("login", Context.MODE_PRIVATE).getLong("idUsuario", 0);

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