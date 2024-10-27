package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.example.mobile_athleta.databinding.ActivityTelaProdutoBinding;

public class TelaProduto extends AppCompatActivity {
    ActivityTelaProdutoBinding binding;

    String number = "11999999999";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_produto);
        binding = ActivityTelaProdutoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int produtoId = getIntent().getIntExtra("produtoId", -1);

        binding.setaProduto.setOnClickListener(view -> {
            finish();
        });

        binding.botaoAnuncio.setOnClickListener(view -> {
            String url = "https://api.whatsapp.com/send?phone=" + "+55" + number;
            try {
                PackageManager pm = view.getContext().getPackageManager();
                pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                view.getContext().startActivity(i);
            } catch (PackageManager.NameNotFoundException e) {
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
    }
}