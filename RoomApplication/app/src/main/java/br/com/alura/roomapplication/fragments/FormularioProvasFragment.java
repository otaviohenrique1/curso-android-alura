package br.com.alura.roomapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import br.com.alura.roomapplication.ProvasDelegate;
import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.database.GeradorDeBancoDeDados;
import br.com.alura.roomapplication.database.conversor.ConversorDeData;
import br.com.alura.roomapplication.database.daos.ProvaDao;
import br.com.alura.roomapplication.models.Prova;

public class FormularioProvasFragment extends Fragment {
    public static final String PROVA = "prova";
    private Prova prova = new Prova();
    private EditText campoMateria;
    private EditText data;
    private ProvasDelegate delegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate = (ProvasDelegate) getActivity();
        delegate.alteraNomeDaActivity("Cadastro de Prova");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formulario_provas, container, false);

        configuraComponentsDa(view);

        colocaProvaNaTelaSeNecessario();

        return view;
    }

    private void colocaProvaNaTelaSeNecessario() {
        Bundle argumentos = getArguments();
        if(argumentos != null) {
            this.prova = (Prova) argumentos.getSerializable(PROVA);
            data.setText(ConversorDeData.converteDo(prova.getDataRealizacao()));
            campoMateria.setText(prova.getMateria());
        }
    }

    private void configuraComponentsDa(View view) {
        this.campoMateria = view.findViewById(R.id.formulario_prova_materia);
        this.data = view.findViewById(R.id.formulario_prova_data);

        Button cadastrar = view.findViewById(R.id.formulario_prova_cadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizaInformacoesDaProva();
                persisteProva();
                delegate.voltaParaTelaAnterior();
            }
        });
    }

    private void persisteProva() {
        GeradorDeBancoDeDados gerador = new GeradorDeBancoDeDados();
        ProvaDao provaDao = gerador.gera(getContext()).getProvaDao();

        if(ehProvaNova()) {
            provaDao.insere(prova);
        } else {
            provaDao.altera(prova);
        }
    }

    private boolean ehProvaNova() {
        return prova.getId() == null;
    }

    private void atualizaInformacoesDaProva() {
        prova.setMateria(campoMateria.getText().toString());
        prova.setDataRealizacao(ConversorDeData.converte(data.getText().toString()));
    }
}
