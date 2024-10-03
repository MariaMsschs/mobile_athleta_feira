package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobile_athleta.databinding.ActivityTelaCadastroBinding;
import com.google.firebase.auth.FirebaseAuth;

public class TelaCadastro extends AppCompatActivity {
    private ActivityTelaCadastroBinding binding;
    private int selectedYear, selectedMonth, selectedDay;
//    private String username;
//    private String email;
//    private String senha;

    private EditText nome;
    private EditText email;
    private EditText senha;
    private EditText dataNascimento;
    private EditText username;
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

//        String data = binding.dataNascimento.getText().toString();

        binding.botaoVoltar.setOnClickListener(v -> {
            Intent login = new Intent(this, Login.class);
            startActivity(login);
            finish();
        });

        binding.botaoCadastro.setOnClickListener(v -> {
//            username = binding.nomeUsuario.getText().toString();
//            email = binding.cadEmail.getText().toString();
//            senha = binding.senha.getText().toString();
            salvarLogin(nome, email, senha);
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

    private void salvarLogin(EditText username, EditText email, EditText senha) {
        checkAllFields();
        if(email.getError() == null && username.getError() == null && senha.getError() == null) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email.getText().toString(), senha.getText().toString()).addOnCompleteListener( task -> {

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
}