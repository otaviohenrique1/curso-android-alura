package br.com.appfinancask.financask.model

import java.math.BigDecimal

/**
 * Created by ROSANGELA on 25/01/2018.
 */
class Resumo(private val transacoes: List<Transacao>) {
    /*Calcula o valor da receita*/
    /*fun receita() : BigDecimal {
        /*Inicia a variavel*/
        /*var totalReceita = BigDecimal.ZERO*/
        /*For Loop -> Parecido com o Foreach*/
        for (transacao in transacoes) {
            if (transacao.tipo == Tipo.RECEITA) {
                totalReceita = totalReceita.plus(transacao.valor)
            }
        }
        return totalReceita
    }*/

    /*Calcula o valor da despesa*/
    /*fun despesa() : BigDecimal {
        /*var totalDespesa = BigDecimal.ZERO
        for (transacao in transacoes) {
            if (transacao.tipo == Tipo.DESPESA) {
                totalDespesa = totalDespesa.plus(transacao.valor)
            }
        }
        return totalDespesa*/

        return somaPor(Tipo.DESPESA)
    }*/

    /*Calcula o valor da total*/
    /*fun total() : BigDecimal {
        return receita().subtract(despesa())
    }*/

    /*fun receita() = somaPor(Tipo.RECEITA)*//*Calcula o valor da receita*/

    /*fun despesa() = somaPor(Tipo.DESPESA)*//*Calcula o valor da despesa*/

    /*Calcula o valor da total*/
    /*Subtrai a despesa da receita*/
    /*fun total() = receita().subtract(despesa())*//*Single Expression Function*/

    /*Calcula o valor da receita*/
    val receita get() = somaPor(Tipo.RECEITA)

    /*Calcula o valor da despesa*/
    val despesa get() = somaPor(Tipo.DESPESA)


    private fun somaPor(tipo: Tipo) : BigDecimal {
        /*val somaDeTransacoesPeloTipo = transacoes
                .filter({ transacao -> transacao.tipo == tipo })
                .sumByDouble({ transacao -> transacao.valor.toDouble() })*/

        val somaDeTransacoesPeloTipo = transacoes
                .filter{ it.tipo == tipo }
                .sumByDouble{ it.valor.toDouble() }
        return BigDecimal(somaDeTransacoesPeloTipo)
    }

    /*Calcula o valor da total*/
    /*Subtrai a despesa da receita*/
    val total get() = receita.subtract(despesa)
}