package com.example.mobile_athleta.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mobile_athleta.R;

public class Item1Fragment extends Fragment {


    public Item1Fragment() {

    }

    public static Item1Fragment newInstance() {
        Item1Fragment fragment = new Item1Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ImageButton imageButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item1, container, false);
        imageButton = view.findViewById(R.id.seta);

        imageButton.setOnClickListener(v -> {
            carregarFragment(new Item2Fragment());
        });
        return view;
    }

    private void carregarFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frame_item, fragment)
                .commit();
    }
}