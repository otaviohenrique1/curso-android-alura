package br.com.appfinancask.financask.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.Locale

/**
 * Created by ROSANGELA on 24/01/2018.
 */

fun BigDecimal.formataParaBrasileiro() : String{
    /*Formata para a moeda real*/
    val formatoBrasileiro = DecimalFormat
            .getCurrencyInstance(Locale("pt", "br"))
    return formatoBrasileiro
            .format(this)
            .replace("R$", "R$ ")
            .replace("-R$", "RS -")
}