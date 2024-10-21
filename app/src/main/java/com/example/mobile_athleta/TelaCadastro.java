package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.example.mobile_athleta.UseCase.CadastrarUsuarioUseCase;
import com.example.mobile_athleta.databinding.ActivityTelaCadastroBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.mobile_athleta.models.Usuario;

public class TelaCadastro extends AppCompatActivity {
    private ActivityTelaCadastroBinding binding;
    private CadastrarUsuarioUseCase cadastrarUsuarioUseCase = new CadastrarUsuarioUseCase();
    private int selectedYear, selectedMonth, selectedDay;
    private EditText nomeEdit;
    private EditText emailEdit;
    private EditText senhaEdit;
    private EditText dataNascimentoEdit;
    private EditText usernameEdit;

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nomeEdit = findViewById(R.id.nome);
        emailEdit = findViewById(R.id.cad_email);
        senhaEdit = findViewById(R.id.cad_senha);
        dataNascimentoEdit = findViewById(R.id.data_nascimento);
        usernameEdit = findViewById(R.id.nome_usuario);
        frameLayout = findViewById(R.id.frameLayoutCadastro);
        frameLayout.setVisibility(ProgressBar.GONE);


        String nome = binding.nome.getText().toString();
        String email = binding.cadEmail.getText().toString();
        String senha = binding.cadSenha.getText().toString();
        String dataNascimento = binding.dataNascimento.getText().toString();
        String username = binding.nomeUsuario.getText().toString();


        binding.botaoVoltar.setOnClickListener(v -> {
            Intent login = new Intent(this, Login.class);
            startActivity(login);
            finish();
        });

        binding.calendario.setOnClickListener(v -> {
            definirData();
        });

        dataNascimentoEdit.setOnClickListener(v -> {
            definirData();
        });

        binding.botaoCadastro.setOnClickListener(v -> {
            checkAllFields();
            if(emailEdit.getError() == null && nomeEdit.getError() == null && senhaEdit.getError() == null
                    && usernameEdit.getError() == null && dataNascimentoEdit.getError() == null) {

                frameLayout.setVisibility(ProgressBar.VISIBLE);

                Usuario usuario = new Usuario(nome, email, senha, converterData(dataNascimento), username);

                cadastrarUsuario(usuario);
                salvarLoginFirebase(username, email, senha);

                Intent home = new Intent(this, TelaFoto.class);
                frameLayout.setVisibility(ProgressBar.GONE);
                startActivity(home);
                finish();
            }
        });
    }

    private void cadastrarUsuario(Usuario usuario){
        cadastrarUsuarioUseCase.cadastrarUsuario(usuario);
    }
    private void salvarLoginFirebase(String username, String email, String senha) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener( task -> {
            if (task.isSuccessful()) {
                getSharedPreferences("login", MODE_PRIVATE).edit().putString("username", username).apply();
            }
        });
    }

    private Date converterData(String dataString) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date data = formato.parse(dataString);
            return data;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void checkAllFields() {
        if(nomeEdit.getText().toString().trim().isEmpty()){
            nomeEdit.setError("Este campo é obrigatório");
        } if (emailEdit.getText().toString().trim().isEmpty()) {
            emailEdit.setError("Este campo é obrigatório");
        } if(senhaEdit.getText().toString().trim().isEmpty()){
            senhaEdit.setError("Este campo é obrigatório");
        } if(dataNascimentoEdit.getText().toString().trim().isEmpty()){
            dataNascimentoEdit.setError("Este campo é obrigatório");
        } if(usernameEdit.getText().toString().trim().isEmpty()){
            usernameEdit.setError("Este campo é obrigatório");
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
}