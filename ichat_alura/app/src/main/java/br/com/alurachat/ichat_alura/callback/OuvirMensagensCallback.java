package br.com.alurachat.ichat_alura.callback;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import br.com.alurachat.ichat_alura.activity.MainActivity;
import br.com.alurachat.ichat_alura.event.MensagemEvent;
import br.com.alurachat.ichat_alura.modelo.Mensagem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ROSANGELA on 14/02/2018.
 */

public class OuvirMensagensCallback implements Callback<Mensagem> {
    private MainActivity activity;
    private EventBus bus;

    public OuvirMensagensCallback(EventBus bus, Context context) {
        this.activity = (MainActivity) context;
        this.bus = bus;
    }

    @Override
    public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {
        if (response.isSuccessful()) {
            Mensagem mensagem = response.body();

            bus.post(new MensagemEvent(mensagem));

            /*activity.colocaNaLista(mensagem);*/

            /*Intent intent = new Intent("nova_mensagem");
            intent.putExtra("mensagem", mensagem);

            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
            localBroadcastManager.sendBroadcast(intent);*/
        }
    }

    @Override
    public void onFailure(Call<Mensagem> call, Throwable t) {
        /*activity.ouvirMensagem();*/
    }
}
