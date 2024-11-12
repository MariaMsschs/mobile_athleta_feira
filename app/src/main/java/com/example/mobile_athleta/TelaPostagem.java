package com.example.mobile_athleta;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobile_athleta.UseCase.InserirPostagemUseCase;
import com.example.mobile_athleta.databinding.ActivityTelaPostagemBinding;
import com.example.mobile_athleta.models.Post;
import com.example.mobile_athleta.service.FotoFirebaseImpl;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TelaPostagem extends AppCompatActivity {

    private ActivityTelaPostagemBinding binding;
    private FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri imageUri;
    File photoFile = null;
    String forum = null;
    private InserirPostagemUseCase inserirPostagemUseCase = new InserirPostagemUseCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaPostagemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String fotoUser = getSharedPreferences("login", Context.MODE_PRIVATE).getString("caminho", "");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            forum = bundle.getString("forum");
            binding.tipoImagem.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.social_cinza, null));
            binding.tipoImagem.invalidate();
            binding.tipoPost.setText(forum);
        }

       fotoFirebaseImpl.recuperarImagem(binding.fotoPerfil, fotoUser);

        binding.botaoVoltar.setOnClickListener(v -> {
            finish();
        });

        binding.abrirCamera.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                // Criar arquivo temporário para salvar a imagem em tamanho completo
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Log.e("Erro", "Erro ao criar arquivo de imagem", ex);
                }
                if (photoFile != null) {
                    imageUri = FileProvider.getUriForFile(this,
                            "com.example.mobile_athleta.fileprovider",  // Certifique-se de ter o FileProvider configurado no AndroidManifest
                            photoFile);

                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    resultLauncherCamera.launch(cameraIntent);  // Lança a câmera
                }
            }
        });

        binding.abrirGaleria.setOnClickListener(v -> {
            Intent galeria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galeria.setType("image/*");
            resultLauncherGaleria.launch(galeria);
        });

        binding.publicar.setOnClickListener(v -> {
            String texto = binding.textoPost.getText().toString();
            Post post = new Post();
            post.setLegenda(texto.trim());
            String caminho = getSharedPreferences("login", MODE_PRIVATE).getString("caminho", "");
            post.setUserFoto(caminho);
            caminho = getSharedPreferences("login", MODE_PRIVATE).getString("username", "");
            post.setUsername(caminho);
            post.setCurtidas(new ArrayList<>());
            post.setCompartilhamento(new ArrayList<>());
            Long id = getSharedPreferences("login", MODE_PRIVATE).getLong("idUsuario", 0L);
            post.setUsuarioId(Long.toString(id));
            if (forum != null) {
                ArrayList<String> lista = new ArrayList<>();
                lista.add(forum);
                post.setForuns(lista);
            }else{
                post.setForuns(new ArrayList<>());
            }
            if(binding.imagemPost.getDrawable() != null) {
                caminho = getSharedPreferences("foto", MODE_PRIVATE).getString("caminho_imagem", "");
                post.setImagem(caminho);
            }

            inserirPostagemUseCase.inserir(post, new InserirPostagemUseCase.InserirCallback() {
                @Override
                public void onInserirSuccess(Post post) {
                    finish();
                }

                @Override
                public void onInserirFailure(String errorMessage) {
                    Log.e("Erro", errorMessage);
                    Toast.makeText(TelaPostagem.this, "Erro", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    // capture image orientation

    public int getCameraPhotoOrientation(Context context, Uri imageUri,
                                         String imagePath) {
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            Log.i("RotateImage", "Exif orientation: " + orientation);
            Log.i("RotateImage", "Rotate value: " + rotate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            imageUri = fotoFirebaseImpl.recuperarImageUri(this, imageBitmap);
            int rotateImage = getCameraPhotoOrientation(this, imageUri, photoFile.getAbsolutePath());
            binding.imagemPost.setRotation(rotateImage);
            Picasso.get().load(imageUri).into(binding.imagemPost);
            fotoFirebaseImpl.uploadImage(imageUri, this);
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            imageUri = Uri.fromFile(photoFile);
//            int rotateImage = getCameraPhotoOrientation(this, imageUri, photoFile.getAbsolutePath());
//            binding.imagemPost.setRotation(rotateImage);
//            Picasso.get().load(imageUri).into(binding.imagemPost);
//            fotoFirebaseImpl.uploadImage(imageUri, this);
//        }
//    }

    private final ActivityResultLauncher<Intent> resultLauncherCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    int rotateImage = getCameraPhotoOrientation(this, imageUri, photoFile.getAbsolutePath());
                    binding.imagemPost.setRotation(rotateImage);
                    Picasso.get().load(imageUri).into(binding.imagemPost);
                    fotoFirebaseImpl.uploadImage(imageUri, this);
                }
            }
    );



//    private final ActivityResultLauncher<Intent> resultLauncherCamera = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(), result -> {
//                if (result.getResultCode() == RESULT_OK) {
//                    int rotateImage = getCameraPhotoOrientation(this, imageUri, photoFile.getAbsolutePath());
//                    binding.imagemPost.setRotation(rotateImage);
//                    Picasso.get().load(imageUri).into(binding.imagemPost);
//                    fotoFirebaseImpl.uploadImage(imageUri, this);
//                }
//            }
//    );


    private final ActivityResultLauncher<Intent> resultLauncherGaleria = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    Picasso.get().load(imageUri).into(binding.imagemPost);
                    fotoFirebaseImpl.uploadImage(imageUri, this);
                }
            }
    );
}
