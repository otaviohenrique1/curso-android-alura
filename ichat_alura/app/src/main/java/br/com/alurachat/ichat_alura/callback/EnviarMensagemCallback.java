package br.com.alurachat.ichat_alura.callback;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ROSANGELA on 14/02/2018.
 */

public class EnviarMensagemCallback implements retrofit2.Callback<Void> {
    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {

    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {

    }
}
