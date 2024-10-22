package com.example.mobile_athleta.service;

import android.content.Context;
import android.widget.EditText;

public interface ValidacaoCadastro {

    void definirData(EditText editText, Context context);

    String converterData(String dataString);
}
