package com.example.mobile_athleta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.mobile_athleta.UseCase.ListarAnuncioPorIdUseCase;
import com.example.mobile_athleta.UseCase.ListarTelefonePorIdUseCase;
import com.example.mobile_athleta.UseCase.ListarUsuarioPorIdUseCase;
import com.example.mobile_athleta.databinding.ActivityTelaProdutoBinding;
import com.example.mobile_athleta.service.FotoFirebaseImpl;

public class TelaProduto extends AppCompatActivity {
    ActivityTelaProdutoBinding binding;

    private ListarAnuncioPorIdUseCase listarAnuncioPorIdUseCase = new ListarAnuncioPorIdUseCase();
    private ListarUsuarioPorIdUseCase listarUsuarioPorIdUseCase = new ListarUsuarioPorIdUseCase();
    private ListarTelefonePorIdUseCase listarTelefonePorIdUseCase = new ListarTelefonePorIdUseCase();
    private FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();

    String telefone;
    Long anuncianteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaProdutoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.setaVoltar.setOnClickListener(view -> {
            finish();
        });

        Long anuncioId = getIntent().getLongExtra("anuncioId", -1);
        String token = getSharedPreferences("login", MODE_PRIVATE).getString("token", "");
        listarAnuncioPorId(token, anuncioId);

        binding.botaoAnuncio.setOnClickListener(v -> {
            Log.d("chamada", anuncianteId.toString());
            listarTelefonePorId(token, anuncianteId);
        });
    }

    public void listarTelefonePorId(String token, Long idAnunciante) {
        Log.d("token", token);
        Log.d("anuncianteId", idAnunciante.toString());
        listarTelefonePorIdUseCase.listarTelefonePorId(token, idAnunciante, this, new ListarTelefonePorIdUseCase.ListarTelefoneCallBack() {
            @Override
            public void onTelefoneRetornado() {
                telefone = getSharedPreferences("anuncios", MODE_PRIVATE).getString("telefone", "");
                String url = "https://api.whatsapp.com/send?phone=" + "+55" + telefone;
                try {
                    PackageManager pm = getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (PackageManager.NameNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
            }

            @Override
            public void onTelefoneError(){
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.anuncio_erro_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(TelaProduto.this);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();
                View botao_alterado = dialogView.findViewById(R.id.botao_erro_inserir);
                botao_alterado.setOnClickListener(v -> {
                    Intent intent = new Intent(TelaProduto.this, TelaHome.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                });
            }
        });
    }


    public void listarAnuncioPorId(String token, Long anuncioId){
        listarAnuncioPorIdUseCase.listarAnuncioPorId(token, anuncioId, anuncio -> {
            Long usuarioId = anuncio.getIdUsuario();
            Log.d("USUARIO ANUNCIO ID 1", usuarioId.toString());
            anuncianteId = usuarioId;
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