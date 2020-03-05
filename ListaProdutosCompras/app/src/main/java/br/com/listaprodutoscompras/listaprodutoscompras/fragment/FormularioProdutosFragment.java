package br.com.listaprodutoscompras.listaprodutoscompras.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import br.com.listaprodutoscompras.listaprodutoscompras.R;
import br.com.listaprodutoscompras.listaprodutoscompras.database.GeradorDeBancoDeDados;
import br.com.listaprodutoscompras.listaprodutoscompras.database.dao.ProdutosDao;
import br.com.listaprodutoscompras.listaprodutoscompras.delegate.ProdutosDelegate;
import br.com.listaprodutoscompras.listaprodutoscompras.models.Produto;

public class FormularioProdutosFragment extends Fragment {
    public static final String CHAVE_PRODUTO = "produto";
    private Produto produto = new Produto();
    private EditText campoNome;
    private EditText campoPreco;
    private EditText campoQuantidade;
    private ProdutosDelegate delegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate = (ProdutosDelegate) getActivity();
        delegate.alteraNomeDaActivity("Cadastro de Produtos");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formulario_produtos, container, false);
        configuraComponentesDaView(view);
        colocaProdutoNaTelaSeNecessario();
        return view;
    }

    private void colocaProdutoNaTelaSeNecessario() {
        Bundle argumentos = getArguments();
        if(argumentos != null) {
            this.produto = (Produto) argumentos.getSerializable(CHAVE_PRODUTO);
            campoNome.setText(produto.getNome());
            campoPreco.setText(Double.toString(produto.getPreco()));
            campoQuantidade.setText(Double.toString(produto.getPreco()));
        }
    }

    private void configuraComponentesDaView(View view) {
        this.campoNome = view.findViewById(R.id.formulario_produtos_nome);
        this.campoPreco = view.findViewById(R.id.formulario_produtos_preco);
        this.campoQuantidade = view.findViewById(R.id.formulario_produtos_quantidade);

        Button cadastrar = view.findViewById(R.id.formulario_produtos_cadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizaInformacoesDoProduto();
                persisteProduto();
                delegate.voldaParaTelaAnterior();
            }
        });
    }

    private void persisteProduto() {
        GeradorDeBancoDeDados gerador = new GeradorDeBancoDeDados();
        ProdutosDao produtosDao = gerador.gera(getContext()).getProdutoDao();
        if(produto.getId() == null) {
            produtosDao.insere(produto);
        } else {
            produtosDao.altera(produto);
        }
    }

    private void atualizaInformacoesDoProduto() {
        produto.setNome(campoNome.getText().toString());
        produto.setPreco(Double.parseDouble(campoPreco.getText().toString()));
        produto.setQuantidade(Double.parseDouble(campoQuantidade.getText().toString()));
    }
}
