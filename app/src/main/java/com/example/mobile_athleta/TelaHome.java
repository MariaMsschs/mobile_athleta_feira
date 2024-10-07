package com.example.mobile_athleta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.mobile_athleta.adapter.EsporteCardAdapter;
import com.example.mobile_athleta.adapter.ForumAdapter;
import com.example.mobile_athleta.adapter.PostAdapter;
import com.example.mobile_athleta.databinding.ActivityTelaHomeBinding;
import com.example.mobile_athleta.fragments.HomeFragment;
import com.example.mobile_athleta.fragments.PerfilFragment;
import com.example.mobile_athleta.models.Esporte;
import com.example.mobile_athleta.models.Forum;
import com.example.mobile_athleta.models.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

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
            loadFragment(new HomeFragment());
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.perfil) {
                    selectedFragment = new PerfilFragment();
                }

                if (selectedFragment != null) {
                    loadFragment(selectedFragment);
                    return true;
                }

                return false;
            }
        });
    }

    // Método para trocar de fragment
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameConteudo, fragment)
                .commit();
    }
}