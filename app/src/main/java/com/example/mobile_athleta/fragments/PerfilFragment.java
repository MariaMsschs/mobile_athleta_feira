package com.example.mobile_athleta.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobile_athleta.R;

public class PerfilFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public PerfilFragment() {
    }

    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
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

    TextView tabPosts, tabForuns, tabEventos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        if (savedInstanceState == null) {
            carregarFragment(new PostPerfil());
        }

        tabPosts = view.findViewById(R.id.tab_posts);
        tabForuns = view.findViewById(R.id.tab_foruns);
        tabEventos = view.findViewById(R.id.tab_eventos);
        View indicator = view.findViewById(R.id.indicator);

        tabPosts.setOnClickListener(v -> {
            indicator.animate().x(tabPosts.getX()).setDuration(200);
            resetTabColors();
            tabPosts.setTextColor(getResources().getColor(R.color.black));
            carregarFragment(new PostPerfil());
        });

        tabForuns.setOnClickListener(v -> {
            indicator.animate().x(tabForuns.getX()).setDuration(200);
            resetTabColors();
            tabForuns.setTextColor(getResources().getColor(R.color.black));
            carregarFragment(new ForumPerfil());
        });

        //criar recycler de evento e adicionar fragment
        tabEventos.setOnClickListener(v -> {
            indicator.animate().x(tabEventos.getX()).setDuration(200);
            resetTabColors();
            tabEventos.setTextColor(getResources().getColor(R.color.black));
        });

        return view;
    }

    private void resetTabColors() {
        tabPosts.setTextColor(getResources().getColor(R.color.gray));
        tabForuns.setTextColor(getResources().getColor(R.color.gray));
        tabEventos.setTextColor(getResources().getColor(R.color.gray));
    }

    private void carregarFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frame_conteudo_perfil, fragment)
                .commit();
    }
}