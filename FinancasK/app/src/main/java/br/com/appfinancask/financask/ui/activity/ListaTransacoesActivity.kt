package br.com.appfinancask.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView
import br.com.appfinancask.financask.R
import br.com.appfinancask.financask.dao.TransacaoDao
import br.com.appfinancask.financask.model.Tipo
import br.com.appfinancask.financask.model.Transacao
import br.com.appfinancask.financask.ui.ResumoView
import br.com.appfinancask.financask.ui.adapter.ListaTransacoesAdapter
import br.com.appfinancask.financask.ui.dialog.AdicionaTransacaoDialog
import br.com.appfinancask.financask.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

/**
 * Created by ROSANGELA on 23/01/2018.
 */
class ListaTransacoesActivity : AppCompatActivity() {
    /*() -> construtor padrão*/

    /*List -> lista imutavel
    MutableList -> lista mutavel*/
    /*private val transacoes: MutableList<Transacao> = mutableListOf()*//*Devolve lista vazia*/

    private val dao =  TransacaoDao()
    private val transacoes = dao.transacoes

    /*lateinit -> Variavel inicializa depois para evitar que a property receba null*/
    /*private lateinit var viewDaActivity: View*/

    /*Property delegada de tipo delegacao preguicosa*/
    /*Property inicializada apenas quando for necessaria*/
    private val viewDaActivity by lazy {
        window.decorView
    }

    private val viewGroupDaActivity by lazy {
        viewDaActivity as ViewGroup
    }

    /*override -> Sobrescrita de metodo*/
    /*fun -> funcao*/
    /*savedInstanceState: Bundle -> variavel: tipo da variavel*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        // findViewById<ListView>(R.id.lista_transacoes_listview)

        /*var -> variavel que muda de valor*/
        /*val -> variavel que nao muda de valor*/
        /*val transacoes: List<Transacao> = transacoesDeExemplo()*/

         /*viewDaActivity = window.decorView*/

        configuraResumo()
        configuraLista()
        configuraFab()

        /*Object Expression -> classe anonima*/
        /*lista_transacoes_adiciona_receita.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                /*this@ListaTransacoesActivity -> Referencia a propria activity*/
                Toast.makeText(this@ListaTransacoesActivity,
                        "clique de receita",Toast.LENGTH_LONG).show()
            }
        })*/

    }

    private fun configuraFab() {
        /*Object Expression -> classe anonima*/
        lista_transacoes_adiciona_receita
                .setOnClickListener {
                    chamaDialogDeAdicao(Tipo.RECEITA)
                }

        lista_transacoes_adiciona_despesa
                .setOnClickListener {
                    chamaDialogDeAdicao(Tipo.DESPESA)
                }
    }

    private fun chamaDialogDeAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(viewGroupDaActivity, this)
                .chama(tipo) {transacaoCriada ->
                    adiciona(transacaoCriada)
                    lista_transacoes_adiciona_menu.close(true)
                }
    }

    private fun adiciona(transacao: Transacao) {
        /*transacoes.add(transacao)
        atualizaTransacoes()*/

        dao.adiciona(transacao)
        atualizaTransacoes()
    }

    private fun atualizaTransacoes() {
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        /*Pega view da activity*/
        /*val view: View = viewDaActivity*/
        val resumoView = ResumoView(this, viewDaActivity, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista() {
        /*this -> propria activity*/
        val listaTransacoesAdapter = ListaTransacoesAdapter(transacoes, this)
        with(lista_transacoes_listview) {
            adapter = listaTransacoesAdapter
            /*adapter = ListaTransacoesAdapter(transacoes, this@ListaTransacoesActivity)*/

            /*pega o item que foi clicado na lista*/
            /*setOnItemClickListener { parent, view, posicao, id -> }*/
            setOnItemClickListener { _, _, posicao, _ ->
                val transacao = transacoes[posicao]
                chamaDialogDeAlteracao(transacao, posicao)
            }

            /*Menu de contexto que aparece quando ocorre um click longo*/
            /*menu, view, menuInfo*/
            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }
        }
        /*
        /*this -> propria activity*/
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)

        /*pega o item que foi clicado na lista*/
        lista_transacoes_listview.setOnItemClickListener { parent, view, posicao, id ->
            val transacao = transacoes[posicao]
            chamaDialogDeAlteracao(transacao, posicao)
        }
        */
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val idDoMenu = item?.itemId
        if (idDoMenu == 1) {
            /*Pega posicao do item na lista*/
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val posicaoDaTransacao = adapterMenuInfo.position
            remove(posicaoDaTransacao)
        }
        return super.onContextItemSelected(item)
    }

    private fun remove(posicao: Int) {
        /*transacoes.removeAt(posicao)
        atualizaTransacoes()*/

        dao.remove(posicao)
        atualizaTransacoes()
    }

    private fun chamaDialogDeAlteracao(transacao: Transacao, posicao: Int) {
        AlteraTransacaoDialog(viewGroupDaActivity, this)
                .chama(transacao) {transacaoAlterada ->
                    altera(transacaoAlterada, posicao)
                }
    }

    private fun altera(transacao: Transacao, posicao: Int) {
        /*Altera a transacao*/
        /*transacoes[posicao] = transacao*/
        /*transacoes.set(posicao, transacao)*/

        dao.altera(transacao, posicao)
        atualizaTransacoes()
    }

    /*private fun transacoesDeExemplo(): List<Transacao> {
        return listOf(
                Transacao(valor = BigDecimal(20.5), categoria = "Almoço de final de semana",
                        data = Calendar.getInstance(),tipo = Tipo.DESPESA),
                Transacao(valor = BigDecimal(100.0), tipo = Tipo.RECEITA,categoria = "Economia"),
                Transacao(valor = BigDecimal(200), tipo = Tipo.DESPESA),
                Transacao(valor = BigDecimal(500), categoria = "Prêmio", tipo = Tipo.RECEITA)
        )/*Named parameters -> data = Calendar.getInstance()*/
    }*/
}