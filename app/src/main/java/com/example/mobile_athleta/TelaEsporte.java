package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_athleta.UseCase.ListarEsportePorIdUseCase;
import com.example.mobile_athleta.databinding.ActivityTelaEsporteBinding;
import com.example.mobile_athleta.service.FotoFirebaseImpl;

import android.os.Bundle;

public class TelaEsporte extends AppCompatActivity {
    private ActivityTelaEsporteBinding binding;

    private ListarEsportePorIdUseCase listarEsportePorIdUseCase = new ListarEsportePorIdUseCase();
    private FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaEsporteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.setaVoltar.setOnClickListener(view -> {
            finish();
        });

        Long esporteId = getIntent().getLongExtra("esporteId", -1);
        String token = getSharedPreferences("login", MODE_PRIVATE).getString("token", "");
        listarEsportePorId(token, esporteId);

    }

    public void listarEsportePorId(String token, Long esporteId){
        listarEsportePorIdUseCase.listarEsportePorId(token, esporteId, esporte -> {
            fotoFirebaseImpl.recuperarImagem(binding.imagemEsporte, esporte.getImagem());
            binding.tituloEsporte.setText(esporte.getNome());
            binding.descricaoEsporte.setText(esporte.getDescricao());
            binding.pratica.setText(esporte.getComoPratica());
        });
    }
}