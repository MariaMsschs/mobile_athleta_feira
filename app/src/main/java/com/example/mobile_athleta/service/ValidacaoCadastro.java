package com.example.mobile_athleta.service;

import android.content.Context;
import android.widget.EditText;

public interface ValidacaoCadastro {

    void definirData(EditText editText, Context context);

    void definirDataEvento(EditText editText, Context context);

    String converterDataCadastro(String dataString);
    String converterDataInterface(String dataString);

    String converterDataEditar(String dataString);

    String converterDataFormatoLongo(String dataString);
}
