package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView cadastre_se;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cadastre_se = findViewById(R.id.botao_cadastrese);

        cadastre_se.setOnClickListener(v -> {
            TelaCadastro telaCadastro = new TelaCadastro();
            Intent intent = new Intent(this, telaCadastro.getClass());
            startActivity(intent);
        });
    }
}