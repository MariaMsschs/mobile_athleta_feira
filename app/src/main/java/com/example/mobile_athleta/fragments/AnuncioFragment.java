package com.example.mobile_athleta.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mobile_athleta.R;
import com.example.mobile_athleta.TelaProduto;
import com.example.mobile_athleta.UseCase.ListarAnunciosUseCase;
import com.example.mobile_athleta.UseCase.ListarUsuarioUseCase;
import com.example.mobile_athleta.adapter.AnuncioAdapter;
import com.example.mobile_athleta.models.Anuncio;
import java.util.ArrayList;
import java.util.List;

public class AnuncioFragment extends Fragment {

    private RecyclerView recyclerViewAnuncios;
    private AnuncioAdapter anuncioAdapter;
    private List<Anuncio> anuncioList;
    private SearchView searchView;
    private TextView textViewNoResults;
    private ImageView imageNoResults;
    private ListarAnunciosUseCase listarAnunciosUseCase = new ListarAnunciosUseCase();

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

        anuncioList = new ArrayList<>();

        String token = getContext().getSharedPreferences("login", MODE_PRIVATE).getString("token", "");

        listarAnunciosUseCase.listarAnuncios(token, anuncios ->  {
            anuncioList = anuncios;

            if(anuncioList.isEmpty()) {
                Log.d("Vazia", anuncios.get(0).toString());
            }
            else{
                recyclerViewAnuncios.setLayoutManager(new GridLayoutManager(getContext(), 2));

                anuncioAdapter = new AnuncioAdapter(anuncioList, anuncio -> {
                    Intent intent = new Intent(getContext(), TelaProduto.class);
                    intent.putExtra("anuncioId", anuncio.getIdAnuncio());
                    startActivity(intent);
                });

                recyclerViewAnuncios.setAdapter(anuncioAdapter);
            }
        });

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