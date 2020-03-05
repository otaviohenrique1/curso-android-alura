package br.com.appfinancask.financask.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ROSANGELA on 24/01/2018.
 */

fun String.limitaEmAte(caracteres: Int) : String {
    /*Ajusta a String que nao cabe na lista */
    if(this.length > caracteres) {
        /*String template -> "${categoriaFormatada.substring(0, 14)}", indicada com "${<expressao>}"*/
        val primeiroCaracter = 0
        return "${this.substring(primeiroCaracter, caracteres)}..."
    }
    return this
}

fun String.converteParaCalendar() : Calendar {
    val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
    val dataConvertida: Date = formatoBrasileiro.parse(this)
    val data = Calendar.getInstance()
    data.time = dataConvertida
    return data
}