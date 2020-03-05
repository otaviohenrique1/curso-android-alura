package br.com.applista.listadecompras.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.applista.listadecompras.R;
import br.com.applista.listadecompras.dao.ProdutoDao;
import br.com.applista.listadecompras.helper.FormularioHelper;
import br.com.applista.listadecompras.model.Produto;

public class FormularioActivity extends AppCompatActivity {
    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);
        Intent intent = getIntent();
        Produto produto = (Produto) intent.getSerializableExtra("produto");

        if(intent.hasExtra("produto")) {
            helper.preencheFormulario(produto);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:
                boolean campoNomeValidado = helper.validaCampoNome();
                boolean campoQuantidadeValidado = helper.validaCampoQuantidade();

                if (!campoNomeValidado && !campoQuantidadeValidado) {
                    Produto produto = helper.getProduto();
                    ProdutoDao dao = new ProdutoDao(this);

                    if (produto.getId() != null) {
                        dao.altera(produto);
                    } else {
                        dao.insere(produto);
                    }

                    dao.close();

                    String mensagemProdutoSalvo = "Produto " + produto.getNome() + " salvo!";
                    Toast.makeText(this, mensagemProdutoSalvo, Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                } else {
                    String mensagemValidacaoCampos = "Campos vazios, preencha os campos para poder cadastrar um produto";
                    Toast.makeText(this, mensagemValidacaoCampos, Toast.LENGTH_SHORT).show();
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
