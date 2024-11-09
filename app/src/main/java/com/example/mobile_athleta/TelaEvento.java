package com.example.mobile_athleta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.mobile_athleta.UseCase.ExcluirEventoUseCase;
import com.example.mobile_athleta.databinding.ActivityTelaEventoBinding;
import com.example.mobile_athleta.service.FotoFirebaseImpl;

public class TelaEvento extends AppCompatActivity {
    private ActivityTelaEventoBinding binding;
    private FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();
    private ExcluirEventoUseCase excluirEventoUseCase = new ExcluirEventoUseCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_evento);

        binding = ActivityTelaEventoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        Long eventoUserId = extras.getLong("eventoUserId");
        Long eventoId = extras.getLong("eventoId");
        String nome = extras.getString("Nome");
        String descricao = extras.getString("Descricao");
        String img = extras.getString("Imagem");
        Long userId = getSharedPreferences("login", MODE_PRIVATE).getLong("idUsuario", 0L);
        String token = extras.getString("token");

        if(eventoUserId == userId){
            binding.excluirEvento.setVisibility(View.VISIBLE);

            binding.excluirEvento.setOnClickListener(view -> {
                excluirEventoUseCase.excluirEvento(token, eventoId, new ExcluirEventoUseCase.ExcluirEventoCallBack() {
                    @Override
                    public void onExcluirEventoSuccess() {
                        fotoFirebaseImpl.deletarFoto(img);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.anuncio_inserido_dialog, null);
                        AlertDialog.Builder builder = new AlertDialog.Builder(TelaEvento.this);
                        TextView texto = dialogView.findViewById(R.id.text);
                        texto.setText("Evento excluído com sucesso!");
                        View botao = dialogView.findViewById(R.id.botao_inserido);
                        builder.setView(dialogView);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        botao.setOnClickListener(v2 -> {
                            Intent intent = new Intent(TelaEvento.this, TelaHome.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            dialog.dismiss();
                        });
                    }

                    @Override
                    public void onExcluirEventoError() {
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.anuncio_erro_dialog, null);
                        AlertDialog.Builder builder = new AlertDialog.Builder(TelaEvento.this);
                        builder.setView(dialogView);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        View botao_alterado = dialogView.findViewById(R.id.botao_erro_inserir);
                        botao_alterado.setOnClickListener(v -> {
                            Intent intent = new Intent(TelaEvento.this, TelaHome.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            dialog.dismiss();
                        });
                    }
                });
            });
        }

        binding.botaoVoltar.setOnClickListener(view -> {
            finish();
        });

        binding.titulo.setText(nome);
        binding.descricao.setText(descricao);
        fotoFirebaseImpl.recuperarImagem(binding.imagemEvento, img );


    }
}