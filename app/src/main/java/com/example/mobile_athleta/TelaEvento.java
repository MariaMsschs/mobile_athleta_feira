package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mobile_athleta.databinding.ActivityTelaEventoBinding;
import com.example.mobile_athleta.service.FotoFirebaseImpl;

public class TelaEvento extends AppCompatActivity {
    ActivityTelaEventoBinding binding;
    private FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_evento);

        binding = ActivityTelaEventoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        String nome = extras.getString("Nome");
        String descricao = extras.getString("Descricao");
        String img = extras.getString("Imagem");

        binding.botaoVoltar.setOnClickListener(view -> {
            finish();
        });

        binding.titulo.setText(nome);
        binding.descricao.setText(descricao);
        fotoFirebaseImpl.recuperarImagem(binding.imagemEvento, img );
    }
}