package br.com.listaprodutos.listadeprodutos.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.listaprodutos.listadeprodutos.R;

public class CadastroProdutoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        setTitle("Cadastro de produto");
    }
}
