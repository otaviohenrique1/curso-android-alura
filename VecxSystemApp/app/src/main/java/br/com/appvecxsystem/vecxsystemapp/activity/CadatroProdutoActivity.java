package br.com.appvecxsystem.vecxsystemapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.appvecxsystem.vecxsystemapp.R;

public class CadatroProdutoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadatro_produto);

        setTitle("Cadastro de produtos");
    }
}
