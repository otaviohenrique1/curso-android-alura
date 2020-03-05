package br.com.aulaalura.agendawebservice.dto;

import java.util.List;

import br.com.aulaalura.agendawebservice.modelo.Aluno;

/**
 * Created by ROSANGELA on 12/02/2018.
 */

public class AlunoSync {
    private List<Aluno> alunos;
    private String momentoDaUltimaModificacao;

    public String getMomentoDaUltimaModificacao() {
        return momentoDaUltimaModificacao;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }
}
