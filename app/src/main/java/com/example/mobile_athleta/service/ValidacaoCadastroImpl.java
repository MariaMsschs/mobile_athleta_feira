package com.example.mobile_athleta.service;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ValidacaoCadastroImpl implements ValidacaoCadastro {

    private int selectedYear, selectedMonth, selectedDay;

    @Override
    public void definirData(EditText editText, Context context) {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (view, year1, month1, dayOfMonth) -> {
                    selectedYear = year1;
                    selectedMonth = month1;
                    selectedDay = dayOfMonth;
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    editText.setText(selectedDate);
                },
                year, month, day);

        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    @Override
    public String converterData(String dataString) {
        final SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
        final SimpleDateFormat formatoSaida = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date data = formatoEntrada.parse(dataString);
            return formatoSaida.format(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return dataString;
        }
    }
}
