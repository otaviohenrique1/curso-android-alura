package br.com.alura.roomapplication;

import br.com.alura.roomapplication.models.Aluno;

public interface AlunosDelegate {
    void lidaComOClickFAB();
    void voltaParaTelaAnterior();
    void alteraNomeDaActivity(String nome);
    void lidaComAlunoSelecionado(Aluno aluno);
}
