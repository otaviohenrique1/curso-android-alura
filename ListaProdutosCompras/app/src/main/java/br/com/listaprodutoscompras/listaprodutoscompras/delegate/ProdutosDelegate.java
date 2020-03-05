package br.com.listaprodutoscompras.listaprodutoscompras.delegate;

import br.com.listaprodutoscompras.listaprodutoscompras.models.Produto;

public interface ProdutosDelegate {
    void lidaComOClickFAB();
    void voldaParaTelaAnterior();
    void alteraNomeDaActivity(String nome);
    void lidaComAlunoSelecionado(Produto produto);
}
