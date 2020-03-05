package br.com.listaprodutoscompras.listaprodutoscompras.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.listaprodutoscompras.listaprodutoscompras.delegate.ProdutosDelegate;
import br.com.listaprodutoscompras.listaprodutoscompras.R;
import br.com.listaprodutoscompras.listaprodutoscompras.fragment.FormularioProdutosFragment;
import br.com.listaprodutoscompras.listaprodutoscompras.fragment.ListaProdutosFragment;
import br.com.listaprodutoscompras.listaprodutoscompras.models.Produto;

public class ListaProdutosActivity extends AppCompatActivity implements ProdutosDelegate{

    public static final String CHAVE_PRODUTO = "produto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);
        exibe(new ListaProdutosFragment(), false);
    }

    public void exibe(Fragment fragment, boolean empilhado) {
        FragmentManager gerenciadorDeFragment = getSupportFragmentManager();
        FragmentTransaction transacao = gerenciadorDeFragment.beginTransaction();
        transacao.replace(R.id.produtos_frame, fragment);
        if(empilhado) {
            transacao.addToBackStack(null);/*Coloca no final da pilha*/
        }
        transacao.commit();/*Inicia a transacao*/
    }

    @Override
    public void lidaComOClickFAB() {
        exibe(new FormularioProdutosFragment(), true); /*Coloca no final da pilha*/
    }

    @Override
    public void voldaParaTelaAnterior() {
        onBackPressed();
    }

    @Override
    public void alteraNomeDaActivity(String nome) {
        setTitle(nome);
    }

    @Override
    public void lidaComAlunoSelecionado(Produto produto) {
        FormularioProdutosFragment formulario = new FormularioProdutosFragment();
        Bundle argumentos = new Bundle();
        argumentos.putSerializable(CHAVE_PRODUTO, produto);
        formulario.setArguments(argumentos);
        exibe(formulario, true);
    }
}
