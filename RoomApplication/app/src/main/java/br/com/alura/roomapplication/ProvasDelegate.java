package br.com.alura.roomapplication;

import br.com.alura.roomapplication.models.Prova;

public interface ProvasDelegate {
    void lidaComOClickFAB();
    void voltaParaTelaAnterior();
    void alteraNomeDaActivity(String nome);
    void lidaComProvaSelecionada(Prova prova);
}
