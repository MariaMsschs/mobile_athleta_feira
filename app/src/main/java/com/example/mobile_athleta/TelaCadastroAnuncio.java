package com.example.mobile_athleta;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobile_athleta.UseCase.InserirAnuncioUseCase;
import com.example.mobile_athleta.UseCase.ListarAnunciosUseCase;
import com.example.mobile_athleta.databinding.ActivityTelaCadastroAnuncioBinding;
import com.example.mobile_athleta.models.Anuncio;
import com.example.mobile_athleta.service.FotoFirebaseImpl;

import java.util.HashMap;
import java.util.Map;

public class TelaCadastroAnuncio extends AppCompatActivity {
    private ActivityTelaCadastroAnuncioBinding binding;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri imageUri;

    private EditText nomeEdit;
    private EditText descricaoEdit;
    private EditText quantEdit;
    private EditText precoEdit;

    private FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaCadastroAnuncioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nomeEdit = findViewById(R.id.nome_anuncio);
        descricaoEdit = findViewById(R.id.descricao_anuncio);
        quantEdit = findViewById(R.id.quantidade_anuncio);
        precoEdit = findViewById(R.id.preco_anuncio);

        binding.botaoVoltar.setOnClickListener(v -> {
            finish();
        });

        binding.imagemAnuncio.setOnClickListener(view -> {
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.alert_dialog, null);
            View tirarFoto = dialogView.findViewById(R.id.botao_ok);
            View abrirGaleria = dialogView.findViewById(R.id.abrir_galeria);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView);
            AlertDialog dialog = builder.create();
            dialog.show();

            tirarFoto.setOnClickListener(v -> {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (camera.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(camera, REQUEST_IMAGE_CAPTURE);
                }
                dialog.dismiss();
            });

            abrirGaleria.setOnClickListener(v -> {
                Intent galeria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galeria.setType("image/*");
                resultLauncherGaleria.launch(galeria);
                dialog.dismiss();
            });
        });

        estado(binding.estadoAnuncio);

        binding.cadastroAnuncio.setOnClickListener(v -> {
            checkAllFields();
            Long idEstado = getSharedPreferences("anuncio", MODE_PRIVATE).getLong("idEstado", 0L);


            if(nomeEdit.getError() == null && descricaoEdit.getError() == null && precoEdit.getError() == null
                    && quantEdit.getError() == null && idEstado > 0) {

                binding.frameLayoutAnuncio.setVisibility(ProgressBar.VISIBLE);

                String nome = binding.nomeAnuncio.getText().toString();
                String descricao = binding.descricaoAnuncio.getText().toString();
                double preco = Double.parseDouble(binding.precoAnuncio.getText().toString());
                int quant = Integer.parseInt(binding.quantidadeAnuncio.getText().toString());
                String imagem = getSharedPreferences("foto", MODE_PRIVATE).getString("caminho_imagem", "");
                Long idUsuario = getSharedPreferences("login", MODE_PRIVATE).getLong("idUsuario", 0L);

                Bundle bundle = new Bundle();

                bundle.putString("tela", "anuncio");
                bundle.putString("nome", nome);
                bundle.putString("descricao", descricao);
                bundle.putDouble("preco", preco);
                bundle.putInt("quant", quant);
                bundle.putString("imagem", imagem);
                bundle.putLong("idUsuario", idUsuario);
                bundle.putLong("idEstado", idEstado);

                Intent pagamento = new Intent(this, TelaPagamento.class);
                pagamento.putExtras(bundle);
                startActivity(pagamento);
            }
            else{
                Toast.makeText(this, "Existe um campo vazio!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void estado(Spinner spinner) {
        Map<String, Integer> estadoMap = new HashMap<>();

        String[] estados = {"Selecione um estado", "Acre", "Alagoas", "Amapá", "Amazonas", "Bahia", "Ceará",
                "Distrito Federal", "Espírito Santo", "Goiás", "Maranhão", "Mato Grosso",
                "Mato Grosso do Sul", "Minas Gerais", "Pará", "Paraíba", "Paraná", "Pernambuco",
                "Piauí", "Rio de Janeiro", "Rio Grande do Norte", "Rio Grande do Sul",
                "Rondônia", "Roraima", "Santa Catarina", "São Paulo", "Sergipe", "Tocantins"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estados);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        for (int i = 1; i < estados.length; i++) {
            estadoMap.put(estados[i], i);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String estadoSelecionado = parent.getItemAtPosition(position).toString();

                if (!estadoSelecionado.equals("Selecione um estado")) {
                    int numero = estadoMap.get(estadoSelecionado);
                    Long numeroEstado = (long) numero;

                    getSharedPreferences("anuncio", MODE_PRIVATE).edit().putLong("idEstado", numeroEstado).apply();
                    Log.d("Estado", "Estado: " + estadoSelecionado + ", Número: " + numero);
                } else {
                    Log.d("Estado", "Nenhum estado selecionado");
                    getSharedPreferences("anuncio", MODE_PRIVATE).edit().putLong("idEstado", 0).apply();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Estado", "Não selecionado");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                imageUri = fotoFirebaseImpl.recuperarImageUri(this, imageBitmap);
                Glide.with(this)
                        .load(imageBitmap)
                        .into(binding.imagemAnuncio);

                binding.cadastroAnuncio.setBackgroundResource(R.drawable.button_design);
                binding.cadastroAnuncio.setTextColor(getResources().getColor(R.color.white));
                fotoFirebaseImpl.uploadImage(imageUri, this);
            }
        }
    }

    private final ActivityResultLauncher<Intent> resultLauncherGaleria = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    Glide.with(this)
                            .load(imageUri)
                            .into(binding.imagemAnuncio);

                    binding.cadastroAnuncio.setBackgroundResource(R.drawable.button_design);
                    binding.cadastroAnuncio.setTextColor(getResources().getColor(R.color.white));

                    fotoFirebaseImpl.uploadImage(imageUri, this);
                }
            }
    );

    public void checkAllFields() {
        if(nomeEdit.getText().toString().trim().isEmpty()){
            nomeEdit.setError("Este campo é obrigatório");
        } if (descricaoEdit.getText().toString().trim().isEmpty()) {
            descricaoEdit.setError("Este campo é obrigatório");
        } if(precoEdit.getText().toString().trim().isEmpty()){
            precoEdit.setError("Este campo é obrigatório");
        } if(quantEdit.getText().toString().trim().isEmpty()){
            quantEdit.setError("Este campo é obrigatório");
        }
    }
}