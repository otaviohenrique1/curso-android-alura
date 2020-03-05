package br.com.alura.roomapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.alura.roomapplication.R;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button cadastroAlunos = findViewById(R.id.dash_btn_aluno);
        cadastroAlunos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencaoAluno = new Intent(DashboardActivity.this, AlunosActivity.class);
                startActivity(intencaoAluno);
            }
        });

        Button cadastroProvas = findViewById(R.id.dash_btn_provas);
        cadastroProvas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencaoProva = new Intent(DashboardActivity.this, ProvasActivity.class);
                startActivity(intencaoProva);
            }
        });
    }
}
