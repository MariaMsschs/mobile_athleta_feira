package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_athleta.UseCase.DeixarDeSeguirUsuarioUseCase;
import com.example.mobile_athleta.UseCase.SeguirUsuarioUseCase;
import com.example.mobile_athleta.UseCase.VerificarRelacionamentoUseCase;
import com.example.mobile_athleta.fragments.EventoPerfil;
import com.example.mobile_athleta.fragments.ForumPerfil;
import com.example.mobile_athleta.fragments.PostPerfil;
import com.example.mobile_athleta.service.FotoFirebaseImpl;
import com.example.mobile_athleta.service.RelacionamentoUsuario;

public class TelaPerfil extends AppCompatActivity {
    TextView tabPosts, tabForuns, tabEventos;

    private TextView nome;
    private TextView username;
    private TextView seguir;
    ImageView foto;

    FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();
    ImageButton seta;

    private VerificarRelacionamentoUseCase verificarRelacionamentoUseCase = new VerificarRelacionamentoUseCase();

    private SeguirUsuarioUseCase seguirUsuarioUseCase = new SeguirUsuarioUseCase();
    private DeixarDeSeguirUsuarioUseCase deixarDeSeguirUsuarioUseCase = new DeixarDeSeguirUsuarioUseCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tela_perfil);
        nome = findViewById(R.id.nome_perfil);
        username = findViewById(R.id.username_perfil);
        foto = findViewById(R.id.perfil_foto);
        seguir = findViewById(R.id.seguir);
        seta = findViewById(R.id.botao_voltar);

        Bundle bundle = getIntent().getExtras();
        String nomeString = bundle.getString("nome");
        String usernameString = bundle.getString("username");
        String urlString = bundle.getString("url");
        Long userId = bundle.getLong("userId");
        Long id  = getSharedPreferences("login",MODE_PRIVATE).getLong("idUsuario", 0);
        getSharedPreferences("perfil",MODE_PRIVATE).edit().putLong("idPerfil", userId).apply();

        String token = getSharedPreferences("login", MODE_PRIVATE).getString("token", "");

        nome.setText(nomeString);
        username.setText(usernameString);
        fotoFirebaseImpl.recuperarImagem(foto, urlString);

        tabPosts = findViewById(R.id.tab_posts);
        tabForuns = findViewById(R.id.tab_foruns);
        tabEventos = findViewById(R.id.tab_eventos);
        View indicator = findViewById(R.id.indicator);


        verificarRelacionamentoUseCase.verificar(token, new RelacionamentoUsuario(id, userId), new VerificarRelacionamentoUseCase.VerificarRelacionamentoCallback() {
            @Override
            public void onSucess() {
                seguir.setText("Seguindo");
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });

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

        seta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        seguir.setOnClickListener(v -> {
            if (seguir.getText().toString().equals("Seguir")) {
                seguirUsuarioUseCase.seguir(token, new RelacionamentoUsuario(id, userId), new SeguirUsuarioUseCase.VerificarRelacionamentoCallback() {
                    @Override
                    public void onSucess(RelacionamentoUsuario relacionamentoUsuario) {
                        getSharedPreferences("id",MODE_PRIVATE).edit().putLong("idRelacionamento", relacionamentoUsuario.getIdRelacionamento()).apply();
                        seguir.setText("Seguindo");
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                    }
                });
            }
            else if(seguir.getText().toString().equals("Seguindo")){
                deixarDeSeguirUsuarioUseCase.deixarDeSeguir(token, getSharedPreferences("id", MODE_PRIVATE).getLong("idRelacionamento", 0), new DeixarDeSeguirUsuarioUseCase.VerificarRelacionamentoCallback() {
                    @Override
                    public void onSucess(boolean response) {
                        seguir.setText("Seguir");
                    }

                    @Override
                    public void onFailure(String errorMessage) {

                    }
                });
            }
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