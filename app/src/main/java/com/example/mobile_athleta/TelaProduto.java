package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.mobile_athleta.UseCase.ListarAnuncioPorIdUseCase;
import com.example.mobile_athleta.UseCase.ListarUsuarioPorIdUseCase;
import com.example.mobile_athleta.databinding.ActivityTelaProdutoBinding;
import com.example.mobile_athleta.service.FotoFirebaseImpl;

public class TelaProduto extends AppCompatActivity {
    ActivityTelaProdutoBinding binding;

    private ListarAnuncioPorIdUseCase listarAnuncioPorIdUseCase = new ListarAnuncioPorIdUseCase();
    private ListarUsuarioPorIdUseCase listarUsuarioPorIdUseCase = new ListarUsuarioPorIdUseCase();
    private FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();

    String number = "11999999999";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_produto);
        binding = ActivityTelaProdutoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.setaVoltar.setOnClickListener(view -> {
            finish();
        });

        binding.botaoAnuncio.setOnClickListener(view -> {
            String url = "https://api.whatsapp.com/send?phone=" + "+55" + number;
            try {
                PackageManager pm = view.getContext().getPackageManager();
                pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                view.getContext().startActivity(intent);
            } catch (PackageManager.NameNotFoundException e) {
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        Long anuncioId = getIntent().getLongExtra("anuncioId", -1);
        String token = getSharedPreferences("login", MODE_PRIVATE).getString("token", "");
        listarAnuncioPorId(token, anuncioId);
    }

    public void listarAnuncioPorId(String token, Long anuncioId){
        listarAnuncioPorIdUseCase.listarAnuncioPorId(token, anuncioId, anuncio -> {
            Long usuarioId = anuncio.getIdUsuario();
            listarUsuarioPorIdUseCase.listarUsuarioPorId(token, usuarioId, usuario -> {
                String nomeAnunciante = usuario.getNome();
                binding.anunciante.setText(nomeAnunciante);
            });

            fotoFirebaseImpl.recuperarImagem(binding.imagemProduto, anuncio.getImagem());
            binding.tituloProduto.setText(anuncio.getNome());
            binding.descricaoProduto.setText(anuncio.getDescricao());
            binding.precoProduto.setText(String.valueOf(anuncio.getPreco()));
        });
    }
}