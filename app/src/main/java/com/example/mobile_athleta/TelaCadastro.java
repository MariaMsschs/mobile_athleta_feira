package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.example.mobile_athleta.databinding.ActivityTelaCadastroBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class TelaCadastro extends AppCompatActivity {
    private ActivityTelaCadastroBinding binding;
    private int selectedYear, selectedMonth, selectedDay;
    private EditText nome;
    private EditText email;
    private EditText senha;
    private EditText dataNascimento;
    private EditText username;

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nome = findViewById(R.id.nome);
        email = findViewById(R.id.cad_email);
        senha = findViewById(R.id.cad_senha);
        dataNascimento = findViewById(R.id.data_nascimento);
        username = findViewById(R.id.nome_usuario);
        frameLayout = findViewById(R.id.frameLayoutCadastro);
        frameLayout.setVisibility(ProgressBar.GONE);

        binding.botaoVoltar.setOnClickListener(v -> {
            Intent login = new Intent(this, Login.class);
            startActivity(login);
            finish();
        });

        binding.calendario.setOnClickListener(v -> {
            definirData();
        });

        dataNascimento.setOnClickListener(v -> {
            definirData();
        });

        binding.botaoCadastro.setOnClickListener(v -> {
            salvarLogin(username, email, senha);
        });
    }

    private void salvarLogin(EditText username, EditText email, EditText senha) {
        checkAllFields();
        if(email.getError() == null && username.getError() == null && senha.getError() == null) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            frameLayout.setVisibility(ProgressBar.VISIBLE);
            auth.createUserWithEmailAndPassword(email.getText().toString(), senha.getText().toString()).addOnCompleteListener( task -> {
                if (task.isSuccessful()) {
                    getSharedPreferences("login", MODE_PRIVATE).edit().putString("username", username.getText().toString()).apply();
                    Intent home = new Intent(this, TelaFoto.class);
                    frameLayout.setVisibility(ProgressBar.GONE);
                    startActivity(home);
                    finish();
                }
            });
        }
    }

    public void checkAllFields() {
        if(nome.getText().toString().trim().isEmpty()){
            nome.setError("Este campo é obrigatório");
        } if (email.getText().toString().trim().isEmpty()) {
            email.setError("Este campo é obrigatório");
        } if(senha.getText().toString().trim().isEmpty()){
            senha.setError("Este campo é obrigatório");
        } if(dataNascimento.getText().toString().trim().isEmpty()){
            dataNascimento.setError("Este campo é obrigatório");
        } if(username.getText().toString().trim().isEmpty()){
            username.setError("Este campo é obrigatório");
        }
    }

    public void definirData() {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, month1, dayOfMonth) -> {
                    selectedYear = year1;
                    selectedMonth = month1;
                    selectedDay = dayOfMonth;
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    binding.dataNascimento.setText(selectedDate);
                },
                year, month, day);

        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    //    private void validarCadastro(String username, String email, String senha) {
//        if(username.isEmpty() || email.isEmpty() || senha.isEmpty()){
//            Toast.makeText(TelaCadastro.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
//        }
//    }
}