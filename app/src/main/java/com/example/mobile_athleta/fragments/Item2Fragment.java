package com.example.mobile_athleta.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mobile_athleta.R;

public class Item2Fragment extends Fragment {

    public Item2Fragment() {
    }

    public static Item2Fragment newInstance() {
        Item2Fragment fragment = new Item2Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ImageButton setaEsquerda, setaDireita;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item2, container, false);
        setaEsquerda = view.findViewById(R.id.seta_esquerda);
        setaDireita = view.findViewById(R.id.seta_direita);

        setaEsquerda.setOnClickListener(v -> {
            carregarFragment(new Item1Fragment());
        });

        setaDireita.setOnClickListener(v -> {
            carregarFragment(new Item3Fragment());
        });

        return view;
    }

    private void carregarFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frame_item, fragment)
                .commit();
    }
}