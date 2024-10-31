package com.example.mobile_athleta.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.adapter.EventoAdapter;
import com.example.mobile_athleta.models.Evento;
import com.example.mobile_athleta.service.ValidacaoCadastroImpl;

import java.util.ArrayList;
import java.util.List;

public class EventoSocial extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public EventoSocial() {
    }

    public static EventoSocial newInstance(String param1, String param2) {
        EventoSocial fragment = new EventoSocial();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ValidacaoCadastroImpl validacaoCadastroImpl = new ValidacaoCadastroImpl();
    EventoAdapter eventoAdapter;
    List<Evento> eventoList;
    private SearchView searchView;
    private TextView textViewNoResults;
    private ImageView imageNoResults;
    private RecyclerView recyclerViewEvento;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evento_social, container, false);

        recyclerViewEvento = view.findViewById(R.id.recycler_evento_social);
        searchView = view.findViewById(R.id.searchView);
        textViewNoResults = view.findViewById(R.id.textViewNoResults);
        imageNoResults = view.findViewById(R.id.erro_rosto_triste);

        String dateString = "06-02-2008";
        String dataFormatada = validacaoCadastroImpl.converterDataInterface(dateString);

        eventoList = new ArrayList<>();
        eventoList.add(new Evento(2, "PingPros", "Comunidade de ping pong.", dataFormatada, "https://static.wikia.nocookie.net/disney/images/e/e5/Profile_-_Marie.jpg/revision/latest?cb=20240215032542&path-prefix=pt-br"));
        eventoList.add(new Evento(3, "piruetando", "Comunidade de ballet.", dataFormatada, "https://i.scdn.co/image/ab6761610000e5eb0522e98a6f0cf1ddbee9a74f"));
        eventoList.add(new Evento(4, "ta dando onda", "Comunidade de surfe.", dataFormatada, "https://i.scdn.co/image/ab6761610000e5eb0522e98a6f0cf1ddbee9a74f"));

        recyclerViewEvento.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        eventoAdapter = new EventoAdapter(eventoList);
        recyclerViewEvento.setAdapter(eventoAdapter);

        searchView.setQueryHint("Buscar eventos");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
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
}
