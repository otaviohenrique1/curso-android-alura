package br.com.appfinancask.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.appfinancask.financask.R
import br.com.appfinancask.financask.extension.formataParaBrasileiro
import br.com.appfinancask.financask.model.Tipo
import br.com.appfinancask.financask.model.Transacao

/**
 * Created by ROSANGELA on 27/01/2018.
 */
class AlteraTransacaoDialog(viewGroup: ViewGroup,
                            private val context: Context) : FormularioTransacaoDialog(context, viewGroup) {
    override val tituloBotaoPositivo: String
        get() = "Alterar"
    /*private val viewCriada: View = criaLayout()
    private val campoValor: EditText = viewCriada.form_transacao_valor
    private val campoCategoria: Spinner = viewCriada.form_transacao_categoria
    private val campoData: EditText = viewCriada.form_transacao_data*/

    fun chama(transacao: Transacao, delegate: (trasacao: Transacao) -> Unit) {
        val tipo = transacao.tipo

        /*configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, transacaoDelegate)*/

        /*Relacionado com a superclasse*/
        super.chama(tipo, delegate)
        inicializaCampos(transacao)
    }

    private fun inicializaCampos(transacao: Transacao) {
        /*Preenche os campos com os dados do item da lista*/
        val tipo = transacao.tipo
        inicializaCampoValor(transacao)
        inicializaCampoData(transacao)
        inicializaCampoCategoria(tipo, transacao)
    }

    private fun inicializaCampoCategoria(tipo: Tipo, transacao: Transacao) {
        val categoriasRetornadas = context.resources.getStringArray(categoriasPor(tipo))
        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }

    private fun inicializaCampoData(transacao: Transacao) {
        campoData.setText(transacao.data.formataParaBrasileiro())
    }

    private fun inicializaCampoValor(transacao: Transacao) {
        campoValor.setText(transacao.valor.toString())
    }

    override fun tituloPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.string.altera_receita
        }
        return R.string.altera_despesa
    }

    /*private fun configuraFormulario(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {
        val titulo = tituloPor(tipo)

        *//*_ -> Indica que o parametro nao esta sendo utilizado*//*
        AlertDialog.Builder(context)
                .setTitle(titulo)
                .setView(viewCriada)
                .setPositiveButton("Alterar",
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

                            transacaoDelegate.delegate(transacaoCriada)

                            *//*activity.atualizaTransacoes(transacaoCriada)
                            lista_transacoes_adiciona_menu.close(true)*//*

                            *//*Toast.makeText(this, "${transacaoCriada.valor} - " +
                                        "${transacaoCriada.categoria} - " +
                                        "${transacaoCriada.data.formataParaBrasileiro()} - " +
                                        "${transacaoCriada.tipo}", Toast.LENGTH_LONG).show()*//*
                        })
                .setNegativeButton("Cancelar", null)
                .show()
    }

    private fun tituloPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.string.altera_receita
        }
        return R.string.altera_despesa
    }

    private fun converteCampoValor(valorEmTexto: String) : BigDecimal {
        *//*Toast -> mensagem exibida*//*
        *//*try expression *//*
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

        *//*simple_spinner_dropdown_item -> Lista pareciad com o JComboBox*//*
        val adapter = ArrayAdapter
                .createFromResource(context,
                        categorias,
                        android.R.layout.simple_spinner_dropdown_item)

        campoCategoria.adapter = adapter
    }

    private fun categoriasPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.array.categorias_de_receita
        }
        return R.array.categorias_de_despesa
    }

    private fun configuraCampoData() {
        *//*Meses no java
        Janeiro	-> 0, Fevereiro -> 1, Março -> 2, Abril -> 3, Maio -> 4, Junho -> 5
        Julho -> 6, Agosto -> 7, Setembro -> 8, Outubro -> 9, Novembro  -> 10, Dezembro -> 11*//*

        val hoje = Calendar.getInstance()

        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        campoData.setText(hoje.formataParaBrasileiro())

        *//*DatePickerDialog -> Dialog que exibe calendario com datas*//*
        *//*_ -> Indica que o parametro nao esta sendo utilizado*//*
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
        *//*view as ViewGroup -> casting de variavel*//*
        return LayoutInflater.from(context)
                .inflate(R.layout.form_transacao,
                        viewGroup,
                        false)
    }*/
}