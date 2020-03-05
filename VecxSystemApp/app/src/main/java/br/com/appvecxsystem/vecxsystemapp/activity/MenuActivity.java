package br.com.appvecxsystem.vecxsystemapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.appvecxsystem.vecxsystemapp.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = new Intent(this, CadatroProdutoActivity.class);
        startActivity(intent);
    }
}
