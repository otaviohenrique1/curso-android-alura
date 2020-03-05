package br.com.appfinancask.financask.dao

import br.com.appfinancask.financask.model.Transacao

/**
 * Created by ROSANGELA on 30/01/2018.
 */
class TransacaoDao {
    /*Associa o MutableLista com o List para que a estrutura da lista nao seja modificada*/
    val transacoes: List<Transacao> = Companion.transacoes
    /*companion object -> parecido com o static no Java*/
    companion object {
        private val transacoes: MutableList<Transacao> = mutableListOf()
    }

    fun adiciona(transacao: Transacao) {
        Companion.transacoes.add(transacao)
    }

    fun altera(transacao: Transacao, posicao: Int) {
        Companion.transacoes[posicao] = transacao
    }

    fun remove(posicao: Int) {
        Companion.transacoes.removeAt(posicao)
    }
}