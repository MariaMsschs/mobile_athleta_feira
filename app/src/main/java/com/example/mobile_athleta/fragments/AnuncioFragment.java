package com.example.mobile_athleta.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.TelaProduto;
import com.example.mobile_athleta.adapter.AnuncioAdapter;
import com.example.mobile_athleta.adapter.EsporteCardAdapter;
import com.example.mobile_athleta.adapter.ForumAdapter;
import com.example.mobile_athleta.models.Anuncio;
import com.example.mobile_athleta.models.Esporte;
import com.example.mobile_athleta.models.Produto;

import java.util.ArrayList;
import java.util.List;

public class AnuncioFragment extends Fragment {

    private RecyclerView recyclerViewAnuncios;
    private AnuncioAdapter anuncioAdapter;
    private List<Anuncio> anuncioList;
    private SearchView searchView;
    private TextView textViewNoResults;
    private ImageView imageNoResults;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anuncio, container, false);

        recyclerViewAnuncios = view.findViewById(R.id.recycler_anuncios);
        textViewNoResults = view.findViewById(R.id.textViewNoResults);
        imageNoResults = view.findViewById(R.id.erro_rosto_triste);

        searchView = view.findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setQueryHint("Faça sua busca aqui!");
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

        // Inicialização da lista de produtos
        anuncioList = new ArrayList<>();
        anuncioList.add(new Anuncio(32.00, "Raquete de tênis usada por 2 anos", "Raquete de tênis", 1));
        anuncioList.add(new Anuncio(89.00, "óculos de natação rosa com glitter", "óculos de natação", 1));
        anuncioList.add(new Anuncio(10.00, "Bolinha de ping pong laranja", "bolinhas de ping pong", 10));

        recyclerViewAnuncios.setLayoutManager(new GridLayoutManager(getContext(), 2));

        anuncioAdapter = new AnuncioAdapter(anuncioList, (AnuncioAdapter.OnItemClickListener) anuncio -> {
            Intent intent = new Intent(getContext(), TelaProduto.class);
            intent.putExtra("anuncioId", anuncio.getIdAnuncio());
            startActivity(intent);
        });

        recyclerViewAnuncios.setAdapter(anuncioAdapter);

        return view;
    }

    private void filterList(String text) {
        List<Anuncio> listaFiltrada = new ArrayList<>();
        for (Anuncio p : anuncioList) {
            if (p.getNome().toLowerCase().contains(text.toLowerCase())) {
                listaFiltrada.add(p);
            }
        }

        if (listaFiltrada.isEmpty()) {
            textViewNoResults.setVisibility(View.VISIBLE);
            imageNoResults.setVisibility(View.VISIBLE);
            recyclerViewAnuncios.setVisibility(View.GONE);
        } else {
            textViewNoResults.setVisibility(View.GONE);
            imageNoResults.setVisibility(View.GONE);
            recyclerViewAnuncios.setVisibility(View.VISIBLE);
            anuncioAdapter.setListaFiltrada(listaFiltrada);
        }
    }
}