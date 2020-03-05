package br.com.applista.listadecompras.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.applista.listadecompras.R;
import br.com.applista.listadecompras.adapter.ListaComprasAdapter;
import br.com.applista.listadecompras.dao.ProdutoDao;
import br.com.applista.listadecompras.model.Produto;

public class ListaActivity extends AppCompatActivity {

    private ListView listaDeCompras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        listaDeCompras = findViewById(R.id.lista_compras_lista);

        /*Evento de click de algum item da lista*/
        listaDeCompras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /*Exibe os dados do produto*/
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Produto produto = (Produto) listaDeCompras.getItemAtPosition(position);/*Abre o formulario com os dados do produto*/
                Intent intentAbreFormulario = new Intent(ListaActivity.this, FormularioActivity.class);
                intentAbreFormulario.putExtra("produto", produto);/*Manda os dados do produto*/
                startActivity(intentAbreFormulario);
            }
        });

        abreFormulario();

        /*Registra o componente com menu de contexto*/
        registerForContextMenu(listaDeCompras);
    }

    private void abreFormulario() {
        Button novoProduto = findViewById(R.id.lista_compras_adicionar_produto);
        /*Acao do botao que abre o formulario de adicionar produto*/
        novoProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiParaFormulario  = new Intent(ListaActivity.this, FormularioActivity.class);
                startActivity(intentVaiParaFormulario);
            }
        });
    }

    /*Carrega a lista quando a activity for criada*/
    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    /*Cria menu de contexto*/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Produto produto = (Produto) listaDeCompras.getItemAtPosition(info.position);

        /*Cria o item deletar no menu de contexto*/
        MenuItem deletar = menu.add("Deletar");
        /*Acao do item do menu de contexto*/
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                /*Mensagem*/
                Toast.makeText(ListaActivity.this,  "O produto de nome: " + produto.getNome() + " foi deletado", Toast.LENGTH_SHORT).show();

                /*Apaga produto da lista*/
                ProdutoDao dao = new ProdutoDao(ListaActivity.this);
                dao.deleta(produto);
                dao.close();

                /*Carrega a lista depois que o produto foi apagado da lista*/
                carregaLista();
                return false;
            }
        });
    }

    /*Carrega a lista de produtos*/
    private void carregaLista() {
        ProdutoDao dao = new ProdutoDao(this);
        List<Produto> listaProdutosCompra = dao.buscaListaProdutos();
        dao.close();

        ListaComprasAdapter adapterLista = new ListaComprasAdapter(this, listaProdutosCompra);
        listaDeCompras.setAdapter(adapterLista);
    }
}