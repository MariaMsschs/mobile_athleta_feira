package com.example.mobile_athleta.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.TelaConfiguracao;
import com.example.mobile_athleta.service.FotoFirebaseImpl;

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
    ImageButton config;

    private TextView nome;
    private TextView username;
    ImageView foto;

    FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        nome = view.findViewById(R.id.nome_perfil);
        username = view.findViewById(R.id.username_perfil);
        foto = view.findViewById(R.id.perfil_foto);

        String nomeAtual = getContext().getSharedPreferences("login", getContext().MODE_PRIVATE).getString("nome", "");
        String usernameAtual = getContext().getSharedPreferences("login", getContext().MODE_PRIVATE).getString("username", "");
        String caminhoAtual = getContext().getSharedPreferences("login", getContext().MODE_PRIVATE).getString("caminho","");

        nome.setText(nomeAtual);
        username.setText(usernameAtual);
        if (!caminhoAtual.isEmpty()) {
            fotoFirebaseImpl.recuperarImagem(foto, caminhoAtual);
            Log.d("IMAGEM!", "Caminho da imagem encontrado: " + caminhoAtual);
        } else {
            Log.d("IMAGEM!", "Caminho da imagem não encontrado");
        }

        if (savedInstanceState == null) {
            carregarFragment(new PostPerfil());
        }

        tabPosts = view.findViewById(R.id.tab_posts);
        tabForuns = view.findViewById(R.id.tab_foruns);
        tabEventos = view.findViewById(R.id.tab_eventos);
        View indicator = view.findViewById(R.id.indicator);

        tabPosts.post(() -> {
            int width = tabPosts.getWidth();
            ViewGroup.LayoutParams params = indicator.getLayoutParams();
            params.width = width;
            indicator.setLayoutParams(params);
        });

        tabPosts.setOnClickListener(v -> {
            animateIndicator(indicator, tabPosts);
            resetTabColors();
            tabPosts.setTextColor(getResources().getColor(R.color.black));
            carregarFragment(new PostPerfil());
        });

        tabForuns.setOnClickListener(v -> {
            animateIndicator(indicator, tabForuns);
            resetTabColors();
            tabForuns.setTextColor(getResources().getColor(R.color.black));
            carregarFragment(new ForumPerfil());
        });

        tabEventos.setOnClickListener(v -> {
            animateIndicator(indicator, tabEventos);
            resetTabColors();
            tabEventos.setTextColor(getResources().getColor(R.color.black));
            carregarFragment(new EventoPerfil());
        });

        config = view.findViewById(R.id.configuracao);
        config.setOnClickListener(v -> {
            Intent configuracao = new Intent(getActivity(), TelaConfiguracao.class);
            startActivity(configuracao);
        });

        return view;
    }

    private void animateIndicator(View indicator, TextView tab) {
        indicator.animate().x(tab.getX()).setDuration(250);
        ViewGroup.LayoutParams params = indicator.getLayoutParams();
        params.width = tab.getWidth();
        indicator.setLayoutParams(params);
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