package com.example.mobile_athleta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.example.mobile_athleta.UseCase.InserirAnuncioUseCase;
import com.example.mobile_athleta.databinding.ActivityTelaCadastroBinding;
import com.example.mobile_athleta.databinding.ActivityTelaPagamentoBinding;
import com.example.mobile_athleta.models.Anuncio;
import com.example.mobile_athleta.models.Usuario;

public class TelaPagamento extends AppCompatActivity {
    private ActivityTelaPagamentoBinding binding;

    private EditText numeroEdit;
    private EditText titularEdit;
    private EditText validadeEdit;
    private EditText cvvEdit;
    private FrameLayout frameLayout;

    private InserirAnuncioUseCase inserirAnuncioUseCase = new InserirAnuncioUseCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaPagamentoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        numeroEdit = findViewById(R.id.numero_cartao);
        titularEdit = findViewById(R.id.titular_cartao);
        validadeEdit = findViewById(R.id.validade_cartao);
        cvvEdit = findViewById(R.id.cvv);
        frameLayout = findViewById(R.id.frameLayoutPagamento);

        frameLayout.setVisibility(ProgressBar.GONE);

        binding.botaoVoltar.setOnClickListener(v -> {
            Intent cadProduto = new Intent(this, TelaCadastroAnuncio.class);
            startActivity(cadProduto);
            finish();
        });

        Bundle bundle = getIntent().getExtras();
        Anuncio anuncio = new Anuncio(bundle.getString("nome"), bundle.getString("descricao"),
                bundle.getDouble("preco"), bundle.getInt("quant"),
                bundle.getString("imagem"), bundle.getLong("idUsuario"), bundle.getLong("idEstado"));

        binding.pagamento.setOnClickListener(v -> {
            checkAllFields();
            if(numeroEdit.getError() == null && titularEdit.getError() == null && validadeEdit.getError() == null
                    && cvvEdit.getError() == null) {
                binding.frameLayoutPagamento.setVisibility(ProgressBar.VISIBLE);
                cadastrarAnuncio(anuncio);
            }
        });


        validadeEdit.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private final String pattern = "##/##";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d]", "");
                    StringBuilder formatted = new StringBuilder();
                    for (int i = 0; i < clean.length(); i++) {
                        if (i == 2) {
                            formatted.append("/");
                        }
                        formatted.append(clean.charAt(i));
                    }

                    current = formatted.toString();
                    validadeEdit.setText(current);
                    validadeEdit.setSelection(current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        numeroEdit.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String cleanString = s.toString().replaceAll("[^\\d]", ""); // Remove tudo que não for dígito
                    StringBuilder formatted = new StringBuilder();

                    for (int i = 0; i < cleanString.length(); i++) {
                        if (i > 0 && i % 4 == 0) {
                            formatted.append(" ");
                        }
                        formatted.append(cleanString.charAt(i));
                    }

                    current = formatted.toString();
                    numeroEdit.setText(current);
                    numeroEdit.setSelection(current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


    }

    public void cadastrarAnuncio(Anuncio anuncio) {
        String token = getSharedPreferences("login", MODE_PRIVATE).getString("token", "");

        if(anuncio.getImagem() != null){
            inserirAnuncioUseCase.inserirAnuncio(token, anuncio, new InserirAnuncioUseCase.InserirAnuncioCallBack() {
                @Override
                public void onInserirAnuncioSuccess() {
                    getSharedPreferences("foto", MODE_PRIVATE).edit().remove("caminho_imagem").apply();

                    binding.frameLayoutPagamento.setVisibility(ProgressBar.GONE);

                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.anuncio_inserido_dialog, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(TelaPagamento.this);
                    View botao = dialogView.findViewById(R.id.botao_inserido);
                    builder.setView(dialogView);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    botao.setOnClickListener(v2 -> {
                        Intent intent = new Intent(TelaPagamento.this, TelaHome.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    });
                }

                @Override
                public void onInserirAnuncioFailure(){
                    binding.frameLayoutPagamento.setVisibility(ProgressBar.GONE);
                    getSharedPreferences("foto", MODE_PRIVATE).edit().remove("caminho_imagem").apply();
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.anuncio_erro_dialog, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(TelaPagamento.this);
                    builder.setView(dialogView);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    View botao_alterado = dialogView.findViewById(R.id.botao_erro_inserir);
                    botao_alterado.setOnClickListener(v -> {
                        Intent intent = new Intent(TelaPagamento.this, TelaHome.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    });
                }
            });
        }
        else{
            Log.d("FALHA AO INSERIR", "Imagem vazia");
        }

    }

    public void checkAllFields() {
        if(numeroEdit.getText().toString().trim().isEmpty()){
            numeroEdit.setError("Este campo é obrigatório");
        }
        else if (titularEdit.getText().toString().trim().isEmpty()) {
            titularEdit.setError("Este campo é obrigatório");
        }
        else if(validadeEdit.getText().toString().trim().isEmpty()){
            validadeEdit.setError("Este campo é obrigatório");
        }
        else if(cvvEdit.getText().toString().trim().isEmpty()){
            cvvEdit.setError("Este campo é obrigatório");
        }
    }
}