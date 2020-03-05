package br.com.aulaaplicacao.aluraviagens.util;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataUtil {

    public static final String DIA_E_MES = "dd/MM";

    @NonNull
    public static String periodoEmTexto(int dias) {
        Calendar dataIda = Calendar.getInstance();
        Calendar dataVolta = Calendar.getInstance();
        dataVolta.add(Calendar.DATE, dias);
        SimpleDateFormat formatoBrasileiro = new SimpleDateFormat(DIA_E_MES);
        String dataFormartadaIda = formatoBrasileiro.format(dataIda.getTime());
        String dataFormartadaVolta = formatoBrasileiro.format(dataVolta.getTime());
        return dataFormartadaIda + " - "
                + dataFormartadaVolta + " de " +
                dataVolta.get(Calendar.YEAR);
    }
}
