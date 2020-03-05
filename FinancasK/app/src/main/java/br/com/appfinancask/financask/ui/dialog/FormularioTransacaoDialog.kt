package br.com.appfinancask.financask.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.appfinancask.financask.R
import br.com.appfinancask.financask.extension.converteParaCalendar
import br.com.appfinancask.financask.extension.formataParaBrasileiro
import br.com.appfinancask.financask.model.Tipo
import br.com.appfinancask.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

/**
 * Created by ROSANGELA on 29/01/2018.
 */
abstract class FormularioTransacaoDialog(private val context: Context,
                                     private val viewGroup: ViewGroup) {
    /*Classes com open podem ser herdadas -> open class FormularioTransacaoDialog()*/

    private val viewCriada = criaLayout()
    protected val campoValor = viewCriada.form_transacao_valor
    protected val campoCategoria = viewCriada.form_transacao_categoria
    protected val campoData = viewCriada.form_transacao_data
    abstract protected val tituloBotaoPositivo: String

    fun chama(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {
        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, delegate)
    }

    private fun configuraFormulario(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {
        val titulo = tituloPor(tipo)

        /*_ -> Indica que o parametro nao esta sendo utilizado*/
        AlertDialog.Builder(context)
                .setTitle(titulo)
                .setView(viewCriada)
                .setPositiveButton(tituloBotaoPositivo,
                        { _, _ ->
                            val valorEmTexto = campoValor.text.toString()
                            val dataEmTexto = campoData.text.toString()
                            val categoriaEmTexto = campoCategoria.selectedItem.toString()

                            var valor = converteCampoValor(valorEmTexto)
                            val data = dataEmTexto.converteParaCalendar()

                            val transacaoCriada = Transacao(
                                    tipo = tipo,
                                    valor = valor,
                                    data = data,
                                    categoria = categoriaEmTexto
                            )

                            delegate(transacaoCriada)

                            /*activity.atualizaTransacoes(transacaoCriada)
                            lista_transacoes_adiciona_menu.close(true)*/

                            /*Toast.makeText(this, "${transacaoCriada.valor} - " +
                                        "${transacaoCriada.categoria} - " +
                                        "${transacaoCriada.data.formataParaBrasileiro()} - " +
                                        "${transacaoCriada.tipo}", Toast.LENGTH_LONG).show()*/
                        })
                .setNegativeButton("Cancelar", null)
                .show()
    }

    abstract protected fun tituloPor(tipo: Tipo): Int;
    /*{
        if (tipo == Tipo.RECEITA) {
            return R.string.adiciona_receita
        }
        return R.string.adiciona_despesa
    }*/

    private fun converteCampoValor(valorEmTexto: String) : BigDecimal {
        /*Toast -> mensagem exibida*/
        /*try expression */
        return try {
            BigDecimal(valorEmTexto)
        } catch (exception: NumberFormatException) {
            Toast.makeText(context,
                    "Falha na conversão de valor",
                    Toast.LENGTH_LONG)
                    .show()
            BigDecimal.ZERO
        }
    }

    private fun configuraCampoCategoria(tipo: Tipo) {
        val categorias = categoriasPor(tipo)

        /*simple_spinner_dropdown_item -> Lista pareciad com o JComboBox*/
        val adapter = ArrayAdapter
                .createFromResource(context,
                        categorias,
                        android.R.layout.simple_spinner_dropdown_item)

        campoCategoria.adapter = adapter
    }

    protected fun categoriasPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.array.categorias_de_receita
        }
        return R.array.categorias_de_despesa
    }

    private fun configuraCampoData() {
        /*Meses no java
        Janeiro	-> 0, Fevereiro -> 1, Março -> 2, Abril -> 3, Maio -> 4, Junho -> 5
        Julho -> 6, Agosto -> 7, Setembro -> 8, Outubro -> 9, Novembro  -> 10, Dezembro -> 11*/

        val hoje = Calendar.getInstance()

        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        campoData.setText(hoje.formataParaBrasileiro())

        /*DatePickerDialog -> Dialog que exibe calendario com datas*/
        /*_ -> Indica que o parametro nao esta sendo utilizado*/
        campoData.setOnClickListener {
            DatePickerDialog(context,
                { _, ano, mes, dia ->
                    val dataSelecionada = Calendar.getInstance()
                    dataSelecionada.set(ano, mes, dia)
                    campoData.setText(dataSelecionada.formataParaBrasileiro())
                }
                , ano, mes, dia)
                .show()
        }
    }

    private fun criaLayout() : View {
        /*view as ViewGroup -> casting de variavel*/
        return LayoutInflater.from(context)
                .inflate(R.layout.form_transacao,
                        viewGroup,
                        false)
    }
}