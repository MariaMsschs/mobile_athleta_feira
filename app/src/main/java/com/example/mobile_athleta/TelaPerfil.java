package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_athleta.fragments.EventoPerfil;
import com.example.mobile_athleta.fragments.ForumPerfil;
import com.example.mobile_athleta.fragments.PostPerfil;
import com.example.mobile_athleta.service.FotoFirebaseImpl;

public class TelaPerfil extends AppCompatActivity {
    TextView tabPosts, tabForuns, tabEventos;

    private TextView nome;
    private TextView username;
    ImageView foto;

    FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tela_perfil);
        nome = findViewById(R.id.nome_perfil);
        username = findViewById(R.id.username_perfil);
        foto = findViewById(R.id.perfil_foto);

        Bundle bundle = getIntent().getExtras();
        String nomeString = bundle.getString("nome");
        String usernameString = bundle.getString("username");
        String urlString = bundle.getString("url");
        Long userId = bundle.getLong("userId");
        getSharedPreferences("perfil",MODE_PRIVATE).edit().putLong("idPerfil", userId).apply();


        nome.setText(nomeString);
        username.setText(usernameString);
        fotoFirebaseImpl.recuperarImagem(foto, urlString);

        tabPosts = findViewById(R.id.tab_posts);
        tabForuns = findViewById(R.id.tab_foruns);
        tabEventos = findViewById(R.id.tab_eventos);
        View indicator = findViewById(R.id.indicator);

        tabPosts.post(() -> {
            int width = tabPosts.getWidth();
            ViewGroup.LayoutParams params = indicator.getLayoutParams();
            params.width = width;
            indicator.setLayoutParams(params);
            carregarFragment(new PostPerfil());
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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_conteudo_perfil, fragment)
                .commit();
    }


}