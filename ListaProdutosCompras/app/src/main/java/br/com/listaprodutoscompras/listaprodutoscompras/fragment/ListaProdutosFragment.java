package br.com.listaprodutoscompras.listaprodutoscompras.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import br.com.listaprodutoscompras.listaprodutoscompras.R;
import br.com.listaprodutoscompras.listaprodutoscompras.database.GeradorDeBancoDeDados;
import br.com.listaprodutoscompras.listaprodutoscompras.database.ProdutosDatabase;
import br.com.listaprodutoscompras.listaprodutoscompras.database.dao.ProdutosDao;
import br.com.listaprodutoscompras.listaprodutoscompras.delegate.ProdutosDelegate;
import br.com.listaprodutoscompras.listaprodutoscompras.models.Produto;

public class ListaProdutosFragment extends Fragment {
    public static final String BUSCA_NOME_PRODUTO = "Digite o nome do produto para a busca";
    public static final String TEXTO_BOTAO_BUSCAR = "Buscar";
    public static final String TEXTO_BOTAO_CANCELAR = "Cancelar";
    public static final String TITULO_CAMPO_NOME = "Nome";
    public static final String LISTA_DE_PRODUTOS = "Lista de produtos";
    public static final String BOTAO_SIM = "Sim";
    private ProdutosDelegate delegate;
    private Button botaoAdd;
    private ListView lista;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate = (ProdutosDelegate) getActivity();
        setHasOptionsMenu(true);/*Habilita menu superior na tela*/
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_produtos, container, false);/*attachToRoot -> nao adiciona na root*/
        configuraComponentsDaView(view);
        return view;
    }

    private void configuraComponentsDaView(View view) {
        configuraLista(view);
        configuraFAB(view);
    }

    private void configuraLista(View view) {
        lista = view.findViewById(R.id.fragment_lista);
        GeradorDeBancoDeDados geradorDeBancoDeDados = new GeradorDeBancoDeDados();
        ProdutosDatabase database = geradorDeBancoDeDados.gera(getContext());
        final ProdutosDao produtosDao = database.getProdutoDao();
        List<Produto> produtos = produtosDao.busca();
        final ArrayAdapter<Produto> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, produtos);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int posicao, long id) {
                Produto produto = (Produto) lista.getItemAtPosition(posicao);
                delegate.lidaComAlunoSelecionado(produto);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int posicao, long id) {
                final Produto produto = (Produto) lista.getItemAtPosition(posicao);
                String mensagem = "Excluir produto " + produto.getNome() + " ?";
                Snackbar.make(botaoAdd, mensagem, Snackbar.LENGTH_LONG)
                        .setAction(BOTAO_SIM, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                produtosDao.deleta(produto);
                                adapter.remove(produto);
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    private void configuraFAB(View view) {
        botaoAdd = view.findViewById(R.id.fragment_lista_fab);
        botaoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.lidaComOClickFAB();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        delegate.alteraNomeDaActivity(LISTA_DE_PRODUTOS);
    }

    /*Menu Superior*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.lista_provas_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.lista_produto_nome) {
            Context contexto = getContext();

            LinearLayout linearLayout = new LinearLayout(contexto);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            final EditText campoNomeBusca = new EditText(contexto);
            campoNomeBusca.setHint(TITULO_CAMPO_NOME);
            campoNomeBusca.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
            linearLayout.addView(campoNomeBusca);

            new AlertDialog.Builder(contexto)
                    .setView(linearLayout)
                    .setMessage(BUSCA_NOME_PRODUTO)
                    .setPositiveButton(TEXTO_BOTAO_BUSCAR, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String nomeProdutoString = campoNomeBusca.getText().toString();
                            GeradorDeBancoDeDados geradorDeBancoDeDados = new GeradorDeBancoDeDados();
                            ProdutosDatabase produtosDatabase = geradorDeBancoDeDados.gera(getContext());
                            ProdutosDao produtosDao = produtosDatabase.getProdutoDao();
                            List<Produto> produtos = produtosDao.buscaProduto(nomeProdutoString);
                            final ArrayAdapter<Produto> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, produtos);
                            lista.setAdapter(adapter);
                        }
                    })
                    .setNegativeButton(TEXTO_BOTAO_CANCELAR, null)
                    .show();
        }
        return true;
    }
}
