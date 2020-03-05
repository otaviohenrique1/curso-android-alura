package br.com.appfinancask.financask.model

import java.math.BigDecimal
import java.util.Calendar

/**
 * Created by ROSANGELA on 23/01/2018.
 */

class Transacao(val valor: BigDecimal,
                val categoria: String = "Indefinida",
                val tipo: Tipo,
                val data: Calendar = Calendar.getInstance()){
    /*Calendar.getInstance() -> pega a data atual*/

    /*Sobrecarga de construtor*/
    /*Construtor secundario*/
    /*
    constructor(valor: BigDecimal, tipo: Tipo) : this(valor, "Indefinida", tipo)
    */
}
