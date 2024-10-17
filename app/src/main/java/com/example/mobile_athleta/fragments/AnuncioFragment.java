package com.example.mobile_athleta.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.adapter.AnuncioAdapter;
import com.example.mobile_athleta.adapter.EsporteCardAdapter;
import com.example.mobile_athleta.adapter.ForumAdapter;
import com.example.mobile_athleta.models.Esporte;
import com.example.mobile_athleta.models.Produto;

import java.util.ArrayList;
import java.util.List;

public class AnuncioFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AnuncioFragment(){
    }

    public static AnuncioFragment newInstance(String param1, String param2) {
        AnuncioFragment fragment = new AnuncioFragment();
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

    RecyclerView recyclerViewAnuncios;
    private AnuncioAdapter anuncioAdapter;
    private List<Produto> produtoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anuncio, container, false);

        recyclerViewAnuncios = view.findViewById(R.id.recycler_anuncios);

        produtoList = new ArrayList<>();
        produtoList.add(new Produto(1, "Luva de Baseball", "possivel anunciante", 70, "https://lastfm.freetls.fastly.net/i/u/avatar170s/a3db53601b2b5a80e288e0f91f1cec7e"));
        produtoList.add(new Produto(3, "Luva de Baseball", "possivel anunciante", 70, "https://lastfm.freetls.fastly.net/i/u/avatar170s/a3db53601b2b5a80e288e0f91f1cec7e"));
        produtoList.add(new Produto(2, "coisas", "blaba", 50, "https://lastfm.freetls.fastly.net/i/u/avatar170s/a3db53601b2b5a80e288e0f91f1cec7e"));
        produtoList.add(new Produto(4, "raquete", "blaba", 50, "https://lastfm.freetls.fastly.net/i/u/avatar170s/a3db53601b2b5a80e288e0f91f1cec7e"));
        produtoList.add(new Produto(5, "Luva de Baseball", "possivel anunciante", 70, "https://lastfm.freetls.fastly.net/i/u/avatar170s/a3db53601b2b5a80e288e0f91f1cec7e"));
        produtoList.add(new Produto(6, "raquete", "blaba", 50, "https://lastfm.freetls.fastly.net/i/u/avatar170s/a3db53601b2b5a80e288e0f91f1cec7e"));

        recyclerViewAnuncios.setLayoutManager(new GridLayoutManager(getContext(), 2));
        AnuncioAdapter anuncioAdapter = new AnuncioAdapter(produtoList);
        recyclerViewAnuncios.setAdapter(anuncioAdapter);

        return view;
    }
}