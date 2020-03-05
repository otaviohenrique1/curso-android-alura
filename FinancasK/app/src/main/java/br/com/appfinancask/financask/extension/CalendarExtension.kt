package br.com.appfinancask.financask.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ROSANGELA on 23/01/2018.
 */
fun Calendar.formataParaBrasileiro() : String{
    /*Formata a data para o formato de data brasileiro*/
    val formatoBrasileiro = "dd/MM/yyyy"
    val format = SimpleDateFormat(formatoBrasileiro)
    return format.format(this.time)
}