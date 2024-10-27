package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.mobile_athleta.fragments.Item1Fragment;

public class TelaItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_item);

        Fragment fragment = new Item1Fragment();
        carregarFragment(fragment);

    }

    private void carregarFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_item, fragment)
                .commit();
    }
}