package br.com.aulaaplicacao.aluraviagens.util;

import android.support.annotation.NonNull;

public class DiasUtil {

    public static final String DIAS = " dias";
    public static final String DIA = " dia";

    @NonNull
    public static String formataEmTexto(int quantidadeDias) {
        // String diasEmTexto = "";
        // int quantidadeDias = pacote.getDias();
        if(quantidadeDias > 1) {
            return quantidadeDias + DIAS;
        }
        return quantidadeDias + DIA;
    }
}
