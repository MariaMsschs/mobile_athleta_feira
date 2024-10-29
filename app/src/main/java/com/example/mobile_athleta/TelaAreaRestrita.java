package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;

import com.example.mobile_athleta.databinding.ActivityTelaAreaRestritaBinding;

public class TelaAreaRestrita extends AppCompatActivity {
    private WebView webView;
    private ActivityTelaAreaRestritaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_area_restrita);

        binding = ActivityTelaAreaRestritaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl("https://app.powerbi.com/view?r=eyJrIjoiZTgwNzNlZTItOTM4ZC00OTBmLWEyOWEtYWY1ZDA0Y2YzM2M3IiwidCI6ImIxNDhmMTRjLTIzOTctNDAyYy1hYjZhLTFiNDcxMTE3N2FjMCJ9");

        binding.botaoVoltar.setOnClickListener(v -> finish());

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}