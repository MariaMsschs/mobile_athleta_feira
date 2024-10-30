package com.example.mobile_athleta.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.TelaHome;

public class Item4Fragment extends Fragment {


    public Item4Fragment() {
    }

    public static Item4Fragment newInstance() {
        Item4Fragment fragment = new Item4Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ImageButton setaEsquerda;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item4, container, false);
        setaEsquerda = view.findViewById(R.id.seta_esquerda);
        button = view.findViewById(R.id.botao_item4);

        setaEsquerda.setOnClickListener(v -> {
            carregarFragment(new Item3Fragment());
        });

        button.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TelaHome.class);
            startActivity(intent);
            getActivity().finish();
        });

        return view;
    }

    private void carregarFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frame_item, fragment)
                .commit();
    }
}