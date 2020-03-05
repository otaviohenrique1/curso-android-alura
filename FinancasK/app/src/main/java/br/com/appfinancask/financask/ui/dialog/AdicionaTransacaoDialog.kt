package br.com.appfinancask.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.appfinancask.financask.R
import br.com.appfinancask.financask.model.Tipo

/**
 * Created by ROSANGELA on 27/01/2018.
 */
class AdicionaTransacaoDialog(viewGroup: ViewGroup,
                              context: Context) : FormularioTransacaoDialog(context, viewGroup) {
    override val tituloBotaoPositivo: String
        get() = "Adicionar"

    override fun tituloPor(tipo: Tipo): Int {
       if (tipo == Tipo.RECEITA) {
           return R.string.adiciona_receita
       }
       return R.string.adiciona_despesa
    }
}