package com.example.mobile_athleta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.mobile_athleta.databinding.ActivityTelaHomeBinding;
import com.example.mobile_athleta.fragments.AnuncioFragment;
import com.example.mobile_athleta.fragments.HomeFragment;
import com.example.mobile_athleta.fragments.NotificacaoFragment;
import com.example.mobile_athleta.fragments.PerfilFragment;
import com.example.mobile_athleta.fragments.SocialFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TelaHome extends AppCompatActivity {
    private ActivityTelaHomeBinding binding;

    private PerfilFragment perfilFragment = new PerfilFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_home);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomviewnav);


        if (savedInstanceState == null) {
            carregarFragment(new HomeFragment());
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.perfil) {
                selectedFragment = new PerfilFragment();
            }

            if (item.getItemId() == R.id.home) {
                selectedFragment = new HomeFragment();
            }

            if (item.getItemId() == R.id.anuncios) {
                selectedFragment = new AnuncioFragment();
            }

            if(item.getItemId() == R.id.notificacoes){
                selectedFragment = new NotificacaoFragment();
            }

            if(item.getItemId() == R.id.social){
                selectedFragment = new SocialFragment();
            }

            if (selectedFragment != null) {
                carregarFragment(selectedFragment);
                return true;
            }

            return false;
        });
    }

    private void carregarFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_conteudo_home, fragment)
                .commit();
    }
}