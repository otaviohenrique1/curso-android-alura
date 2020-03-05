package br.com.alura.roomapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import br.com.alura.roomapplication.AlunosDelegate;
import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.database.daos.AlunoDao;
import br.com.alura.roomapplication.database.GeradorDeBancoDeDados;
import br.com.alura.roomapplication.models.Aluno;

public class FormularioAlunosFragment extends Fragment {
    private Aluno aluno = new Aluno();
    private EditText campoNome;
    private EditText campoEmail;
    private AlunosDelegate delegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate = (AlunosDelegate) getActivity();
        delegate.alteraNomeDaActivity("Cadastro de Aluno");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formulario_alunos, container, false);

        configuraComponentsDa(view);

        colocaAlunoNaTelaSeNecessario();

        return view;
    }

    private void colocaAlunoNaTelaSeNecessario() {
        Bundle argumentos = getArguments();
        if(argumentos != null) {
            this.aluno = (Aluno) argumentos.getSerializable("aluno");
            campoNome.setText(aluno.getNome());
            campoEmail.setText(aluno.getEmail());
        }
    }

    private void configuraComponentsDa(View view) {
        this.campoEmail = view.findViewById(R.id.formulario_alunos_email);
        this.campoNome = view.findViewById(R.id.formulario_alunos_nome);

        Button cadastrar = view.findViewById(R.id.formulario_alunos_cadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizaInformacoesDoAluno();
                persisteAluno();
                delegate.voltaParaTelaAnterior();
            }
        });
    }

    private void persisteAluno() {
        GeradorDeBancoDeDados gerador = new GeradorDeBancoDeDados();
        AlunoDao alunoDao = gerador.gera(getContext()).getAlunoDao();

        if(ehAlunoNovo()) {
            alunoDao.insere(aluno);
        } else {
            alunoDao.altera(aluno);
        }
    }

    private boolean ehAlunoNovo() {
        return aluno.getId() == null;
    }

    private void atualizaInformacoesDoAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEmail(campoEmail.getText().toString());
    }
}
