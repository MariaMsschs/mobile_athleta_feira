package com.example.mobile_athleta.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.mobile_athleta.TelaCadastroAnuncio;
import com.example.mobile_athleta.TelaCadastroVendedor;
import com.example.mobile_athleta.TelaConfiguracao;
import com.example.mobile_athleta.TelaProduto;
import com.example.mobile_athleta.UseCase.ChecarVendedorUseCase;
import com.example.mobile_athleta.UseCase.ListarAnunciosUseCase;
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

    private ImageButton adicionarAnuncio;
    private ListarAnunciosUseCase listarAnunciosUseCase = new ListarAnunciosUseCase();
    private ChecarVendedorUseCase checarVendedorUseCase = new ChecarVendedorUseCase();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anuncio, container, false);

        recyclerViewAnuncios = view.findViewById(R.id.recycler_anuncios);
        textViewNoResults = view.findViewById(R.id.textViewNoResults);
        imageNoResults = view.findViewById(R.id.erro_rosto_triste);
        adicionarAnuncio = view.findViewById(R.id.adicionar_anuncio);

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
        Long usuarioId = getContext().getSharedPreferences("login", MODE_PRIVATE).getLong("idUsuario", 0L);

        listarAnunciosUseCase.listarAnuncios(token, anuncios ->  {
            anuncioList = anuncios;

            recyclerViewAnuncios.setLayoutManager(new GridLayoutManager(getContext(), 2));
            anuncioAdapter = new AnuncioAdapter(anuncioList, anuncio -> {
                Intent produto = new Intent(getContext(), TelaProduto.class);
                produto.putExtra("anuncioId", anuncio.getIdAnuncio());
                startActivity(produto);
            });
            recyclerViewAnuncios.setAdapter(anuncioAdapter);
        });

        adicionarAnuncio.setOnClickListener(v -> {
            checarVendedorUseCase.checarVendedor(token, usuarioId, bol -> {
                if(bol == true){
                    Intent cadastroVendedor = new Intent(getContext(), TelaCadastroAnuncio.class);
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