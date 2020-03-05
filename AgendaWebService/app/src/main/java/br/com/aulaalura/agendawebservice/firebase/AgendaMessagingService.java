package br.com.aulaalura.agendawebservice.firebase;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Map;

import br.com.aulaalura.agendawebservice.Dao.AlunoDAO;
import br.com.aulaalura.agendawebservice.dto.AlunoSync;
import br.com.aulaalura.agendawebservice.event.AtualizaListaAlunoEvent;
import br.com.aulaalura.agendawebservice.sinc.AlunoSincronizador;

/**
 * Created by ROSANGELA on 22/02/2018.
 */

public class AgendaMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> mensagem = remoteMessage.getData();
        Log.i("mensagem recebida", String.valueOf(mensagem));

        conveteParaAluno(mensagem);
    }

    private void conveteParaAluno(Map<String, String> mensagem) {
        String chaveDeAcesso = "alunoWebServiceSync";
        if (mensagem.containsKey(chaveDeAcesso)) {
            String json = mensagem.get(chaveDeAcesso);
            ObjectMapper mapper = new ObjectMapper();
            try {
                AlunoSync alunoSync = mapper.readValue(json, AlunoSync.class);

                /*AlunoDAO alunoDAO = new AlunoDAO(this);
                alunoDAO.sincroniza(alunoSync.getAlunos());
                alunoDAO.close();*/

                new AlunoSincronizador(AgendaMessagingService.this).sincroniza(alunoSync);

                EventBus eventBus = EventBus.getDefault();
                eventBus.post(new AtualizaListaAlunoEvent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
