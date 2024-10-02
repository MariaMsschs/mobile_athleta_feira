package com.example.mobile_athleta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mobile_athleta.databinding.ActivityTelaCadastroBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Calendar;

public class TelaCadastro extends AppCompatActivity {
    private ActivityTelaCadastroBinding binding;
    private int selectedYear, selectedMonth, selectedDay;
    private String username;
    private String email;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        String data = binding.dataNascimento.getText().toString();

        binding.botaoVoltar.setOnClickListener(v -> {
            Intent login = new Intent(this, Login.class);
            startActivity(login);
        });

        binding.botaoCadastro.setOnClickListener(v -> {
            username = binding.nomeUsuario.getText().toString();
            email = binding.cadEmail.getText().toString();
            senha = binding.senha.getText().toString();

            salvarLogin(username, email, senha);
        });
//
//        // Obter a data atual
//        Calendar calendar = Calendar.getInstance();
//        final int year = calendar.get(Calendar.YEAR);
//        final int month = calendar.get(Calendar.MONTH);
//        final int day = calendar.get(Calendar.DAY_OF_MONTH);

//        // Ao clicar no botão, abre o DatePickerDialog
//        binding.calendario.setOnClickListener(v -> {
//            DatePickerDialog datePickerDialog = new DatePickerDialog(
//                    this,
//                    (view, year1, month1, dayOfMonth) -> {
//                        selectedYear = year1;
//                        selectedMonth = month1;
//                        selectedDay = dayOfMonth;
//                        String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
//                        binding.dataNascimento.setText(selectedDate);
//                    },
//                    year, month, day);
//
//            // Definir data máxima como a data atual (para evitar datas futuras)
//            datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
//            datePickerDialog.show();
//        });
    }

    private void salvarLogin(String username, String email, String senha) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener( task -> {

            if (task.isSuccessful()) {
                Toast.makeText(TelaCadastro.this, "Cadastro efetuado", Toast.LENGTH_SHORT).show();
                Intent home = new Intent(this, TelaHome.class);
                startActivity(home);
                finish();
            }
        });
    }

//    private void validarCadastro(String username, String email, String senha) {
//        if(username.isEmpty() || email.isEmpty() || senha.isEmpty()){
//            Toast.makeText(TelaCadastro.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
//        }
//    }
}