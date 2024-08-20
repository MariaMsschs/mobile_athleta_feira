package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class TelaCadastro extends AppCompatActivity {

    ImageButton voltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        voltar = findViewById(R.id.botao_voltar);

        voltar.setOnClickListener(v -> {
            MainActivity main = new MainActivity();
            Intent intent = new Intent(this, main.getClass());
            startActivity(intent);
        });
    }
}