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
import com.example.mobile_athleta.UseCase.GetSeguidoresUseCase;
import com.example.mobile_athleta.UseCase.GetSeguindoUseCase;
import com.example.mobile_athleta.service.FotoFirebaseImpl;

public class PerfilFragment extends Fragment {
    TextView tabPosts, tabForuns, tabEventos, nome, username, seguindo, seguidores;
    ImageButton config;
    ImageView foto;
    FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();
    GetSeguidoresUseCase getSeguidoresUseCase = new GetSeguidoresUseCase();
    GetSeguindoUseCase getSeguindoUseCase = new GetSeguindoUseCase();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        String token = getContext().getSharedPreferences("login", getContext().MODE_PRIVATE).getString("token", "");
        Long userId = getContext().getSharedPreferences("login", getContext().MODE_PRIVATE).getLong("idUsuario", 0);
        nome = view.findViewById(R.id.nome_perfil);
        username = view.findViewById(R.id.username_perfil);
        foto = view.findViewById(R.id.perfil_foto);
        seguindo = view.findViewById(R.id.seguindo);
        seguidores = view.findViewById(R.id.seguidores);

        getSeguidoresUseCase.getSeguidores(token, userId, new GetSeguidoresUseCase.SeguidoresCallback() {
            @Override
            public void onSeguidoresSuccess(String numero_seguidores) {
                seguidores.setText(numero_seguidores + " seguidores");
            }

            @Override
            public void onSeguidoresFailure(String errorMessage) {
                Log.d("Erro ao buscar seguidores", errorMessage);
            }
        });

        getSeguindoUseCase.getSeguindo(token, userId, new GetSeguindoUseCase.SeguindoCallback() {
            @Override
            public void onSeguindoSuccess(String numero_seguidores) {
                seguindo.setText(numero_seguidores + " seguindo");
            }

            @Override
            public void onSeguindoFailure(String errorMessage) {
                Log.d("Erro ao buscar seguindo", errorMessage);
            }
        });

        String nomeAtual = getContext().getSharedPreferences("login", getContext().MODE_PRIVATE).getString("nome", "");
        String usernameAtual = getContext().getSharedPreferences("login", getContext().MODE_PRIVATE).getString("username", "");
        String caminhoAtual = getContext().getSharedPreferences("login", getContext().MODE_PRIVATE).getString("caminho","");
        Long id = getContext().getSharedPreferences("login", getContext().MODE_PRIVATE).getLong("idUsuario", 0L);
        view.getContext().getSharedPreferences("perfil", getContext().MODE_PRIVATE).edit().putLong("idPerfil", id).apply();

        nome.setText(nomeAtual);
        username.setText(usernameAtual);
        fotoFirebaseImpl.recuperarImagem(foto, caminhoAtual);

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