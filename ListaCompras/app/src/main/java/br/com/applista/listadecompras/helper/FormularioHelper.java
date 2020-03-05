package br.com.applista.listadecompras.helper;

import android.widget.EditText;

import br.com.applista.listadecompras.activity.FormularioActivity;
import br.com.applista.listadecompras.R;
import br.com.applista.listadecompras.model.Produto;

/**
 * Created by ROSANGELA on 03/02/2018.
 */

public class FormularioHelper {
    /*Classe que pega os dados dos campos do formulario*/

    /*Atributos*/
    private final EditText campoNome;
    private final EditText campoQuantidade;
    private Produto produto;

    public FormularioHelper(FormularioActivity activity) {
        campoNome = activity.findViewById(R.id.formulario_nome);
        campoQuantidade = activity.findViewById(R.id.formulario_quantidade);
        produto = new Produto();
    }

    /*Pega os dados do produto*/
    public Produto getProduto() {
        produto.setNome(campoNome.getText().toString());
        produto.setQuantidade(Long.valueOf(campoQuantidade.getText().toString()));
        return produto;
    }

    /*Preenche o formulario com os dados do produto recebidos da lista de produtos*/
    public void preencheFormulario(Produto produto) {
        campoNome.setText(produto.getNome().toString());
        campoQuantidade.setText(produto.getQuantidade().toString());
        this.produto = produto;
    }

    public boolean validaCampoNome() {
        return campoNome.getText().length() == 0;
    }

    public boolean validaCampoQuantidade() {
        return campoQuantidade.getText().length() == 0;
    }

    public boolean validaCampoTexto(EditText campoValidado) {
        return campoValidado.getText().length() == 0;
    }
}
