package br.com.alura.roomapplication.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.alura.roomapplication.ProvasDelegate;
import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.fragments.FormularioProvasFragment;
import br.com.alura.roomapplication.fragments.ListaProvasFragment;
import br.com.alura.roomapplication.models.Prova;

public class ProvasActivity extends AppCompatActivity implements ProvasDelegate {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        exibe(new ListaProvasFragment(), false);
    }

    public void exibe(Fragment fragment, boolean empilhado) {
        FragmentManager gerenciadorDeFragment = getSupportFragmentManager();
        FragmentTransaction transacao = gerenciadorDeFragment.beginTransaction();
        transacao.replace(R.id.provas_frame, fragment);
        if(empilhado) {
            transacao.addToBackStack(null);
        }
        transacao.commit();
    }

    @Override
    public void lidaComOClickFAB() {
        exibe(new FormularioProvasFragment(), true);
    }

    @Override
    public void voltaParaTelaAnterior() {
        onBackPressed();
    }

    @Override
    public void alteraNomeDaActivity(String nome) {
        setTitle(nome);
    }

    @Override
    public void lidaComProvaSelecionada(Prova prova) {
        FormularioProvasFragment formulario = new FormularioProvasFragment();
        Bundle argumentos = new Bundle();
        argumentos.putSerializable("prova", prova);
        formulario.setArguments(argumentos);
        exibe(formulario, true);
    }
}
