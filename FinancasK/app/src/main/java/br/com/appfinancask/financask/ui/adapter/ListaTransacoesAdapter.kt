package br.com.appfinancask.financask.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.appfinancask.financask.R
import br.com.appfinancask.financask.extension.formataParaBrasileiro
import br.com.appfinancask.financask.extension.limitaEmAte
import br.com.appfinancask.financask.model.Tipo
import br.com.appfinancask.financask.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*

/**
 * Created by ROSANGELA on 23/01/2018.
 */
class ListaTransacoesAdapter(private val transacoes: List<Transacao>, private val context: Context) : BaseAdapter(){
    /*() -> construtor da classe*/

    /*Atributos*/
    /*private val transacoes = transacoes;*/
    /*private  val context = context*/

    private val limiteDaCategoria = 12
    /*Valor original era 14, mas para que apareca na tela foi mudado para 12*/

    /*: View -> retorno do metodo, metodo sem retorno recebe o : Unit*/
    /*Retorna a lista*/
    override fun getView(posicao: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)

        val transacao = transacoes[posicao]

        adicionaValor(transacao, viewCriada)
        adicionaIcone(transacao, viewCriada)
        adicionaCategoria(viewCriada, transacao)
        adicionaData(viewCriada, transacao)

        return viewCriada
    }

    /*Adiciona a data*/
    private fun adicionaData(viewCriada: View, transacao: Transacao) {
        viewCriada.transacao_data.text = transacao.data
                .formataParaBrasileiro()
    }

    /*Adiciona a categoria*/
    private fun adicionaCategoria(viewCriada: View, transacao: Transacao) {
        viewCriada.transacao_categoria.text = transacao.categoria
                .limitaEmAte(limiteDaCategoria)
    }

    /*Adiciona icone no item da lista*/
    private fun adicionaIcone(transacao: Transacao, viewCriada: View) {
        val icone = iconePor(transacao.tipo)
        viewCriada.transacao_icone
                .setBackgroundResource(icone)
    }

    /*Devolve o icone*/
    private fun iconePor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.drawable.icone_transacao_item_receita
        }
        return R.drawable.icone_transacao_item_despesa
    }

    /*Adiciona o valor em reais com uma cor*/
    private fun adicionaValor(transacao: Transacao, viewCriada: View) {
        val cor: Int = corPor(transacao.tipo)
        viewCriada.transacao_valor
                .setTextColor(cor)

        viewCriada.transacao_valor.text = transacao.valor
                .formataParaBrasileiro()
    }

    /*Devolve o valor de uma cor*/
    private fun corPor(tipo: Tipo): Int {
        /* if expression*/
        /*return if (tipo == Tipo.RECEITA) {
            ContextCompat.getColor(context, R.color.receita)
        } else {
            ContextCompat.getColor(context, R.color.despesa)
        }*/

        /*early return*/
        if (tipo == Tipo.RECEITA) {
            return ContextCompat.getColor(context, R.color.receita)
        }
        return ContextCompat.getColor(context, R.color.despesa)
    }

    /*Retorna item*/
    /*: Any -> retora quaquer tipo de retorno*/
    override fun getItem(posicao: Int): Transacao {
        return transacoes[posicao]
    }

    override fun getItemId(p0: Int): Long {
        return 0;
    }

    /*Retorna o tamanho da lista*/
    override fun getCount(): Int {
        return transacoes.size
    }
}