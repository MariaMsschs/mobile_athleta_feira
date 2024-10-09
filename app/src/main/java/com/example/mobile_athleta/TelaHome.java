package com.example.mobile_athleta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.mobile_athleta.databinding.ActivityTelaHomeBinding;
import com.example.mobile_athleta.fragments.AnuncioFragment;
import com.example.mobile_athleta.fragments.HomeFragment;
import com.example.mobile_athleta.fragments.PerfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TelaHome extends AppCompatActivity {
    private ActivityTelaHomeBinding binding;

    private PerfilFragment perfilFragment = new PerfilFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_home);

        //FRAGMENT ---------------------------------------------------------------------------
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frameConteudo, perfilFragment).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomviewnav);

        // Carregar o primeiro fragmento como padrão
        if (savedInstanceState == null) {
            carregarFragment(new HomeFragment());
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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

                if (selectedFragment != null) {
                    carregarFragment(selectedFragment);
                    return true;
                }

                return false;
            }
        });
    }

    // Método para trocar de fragment
    private void carregarFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_conteudo_home, fragment)
                .commit();
    }
}