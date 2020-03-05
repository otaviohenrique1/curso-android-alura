package br.com.alurachat.ichat_alura.event;

import br.com.alurachat.ichat_alura.modelo.Mensagem;

/**
 * Created by ROSANGELA on 16/02/2018.
 */

public class MensagemEvent {
    public Mensagem mensagem;

    public MensagemEvent(Mensagem mensagem) {

        this.mensagem = mensagem;
    }
}
