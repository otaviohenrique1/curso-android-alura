package br.com.listaprodutoscompras.listaprodutoscompras.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.listaprodutoscompras.listaprodutoscompras.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("Menu");

        configuraBotaoAbreCadastroProduto();
    }

    private void configuraBotaoAbreCadastroProduto() {
        Button abreCadastroProduto = findViewById(R.id.menu_cadastrar_produto);
        abreCadastroProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCadastroProduto = new Intent(MenuActivity.this, ListaProdutosActivity.class);
                startActivity(intentCadastroProduto);
            }
        });
    }
}
