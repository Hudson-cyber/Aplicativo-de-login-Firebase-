package com.hd.teladelogin.activity;

import android.text.TextUtils;
import android.widget.EditText;

public class ValidacaoDados {

    public static boolean validarDadosEmail(String strEmail){

        return true;
    }

    public static boolean validaDadosSenha(String strSenha){
        return true;
    }

    public static boolean emBranco(String strDado){
        return TextUtils.isEmpty(strDado);
    }

    public static String padronizarDados(String strDado){
        return strDado.toLowerCase();
    }

    public static void mensagem(EditText editText, String strMensagem){
        editText.setError(strMensagem);
    }
}
