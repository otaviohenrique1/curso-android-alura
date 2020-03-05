package br.com.appfinancask.financask.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import br.com.appfinancask.financask.R
import br.com.appfinancask.financask.extension.formataParaBrasileiro
import br.com.appfinancask.financask.model.Resumo
import br.com.appfinancask.financask.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

/**
 * Created by ROSANGELA on 25/01/2018.
 */
class ResumoView(context: Context,
                 private val view: View,
                 transacoes: List<Transacao>) {

    private val resumo: Resumo = Resumo(transacoes)
    private val corReceita = ContextCompat.getColor(context, R.color.receita)
    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)

    /*Funcaao que carrega as outras funcoes*/
    fun atualiza() {
        adicionaReceita()
        adicionaDespesa()
        adicionaTotal()
    }

    /*Adiciona o valor da receita*/
    private fun adicionaReceita() {
        val totalReceita = resumo.receita
        with(view.resumo_card_receita) {
            setTextColor(corReceita) /*Coloca cor no valor da receita*/
            text = totalReceita.formataParaBrasileiro()/*Formata para moeda reais*/
        }
    }

    /*Adiciona o valor da despesa*/
    private fun adicionaDespesa() {
        val totalDespesa = resumo.despesa
        with(view.resumo_card_despesa) {
            setTextColor(corDespesa)/*Coloca cor no valor da despesa*/
            text = totalDespesa.formataParaBrasileiro()
        }
    }

    /*Adiciona o valor do total*/
    private fun adicionaTotal() {
        val total = resumo.total
        val cor = corPor(total)
        with(view.resumo_card_total) {
            setTextColor(cor)
            text = total.formataParaBrasileiro()
        }
    }

    private fun corPor(valor: BigDecimal): Int {
        /*Coloca cor no valor do total*/
        /*>= -> parecido com o compareTo*/
        if (valor >= BigDecimal.ZERO) {
            return corReceita
        }
        return corDespesa
    }
}