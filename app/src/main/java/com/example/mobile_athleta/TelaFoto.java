package com.example.mobile_athleta;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mobile_athleta.databinding.ActivityTelaFotoBinding;

public class TelaFoto extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ActivityTelaFotoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaFotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.camera.setOnClickListener(view -> {
            dispatchTakePictureIntent();
        });

        binding.botaoVoltar.setOnClickListener(view -> {
            Intent intent = new Intent(TelaFoto.this, TelaCadastro.class);
            startActivity(intent);
            finish();
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            Glide.with(this)
                    .load(imageBitmap)
                    .circleCrop()
                    .into(binding.camera);

            binding.botaoCadastro.setBackgroundResource(R.drawable.button_design);
            binding.botaoCadastro.setTextColor(getResources().getColor(R.color.white));
        }
    }
}