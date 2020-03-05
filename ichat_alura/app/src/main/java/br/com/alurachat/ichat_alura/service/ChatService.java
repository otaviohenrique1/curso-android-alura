package br.com.alurachat.ichat_alura.service;

import br.com.alurachat.ichat_alura.modelo.Mensagem;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by ROSANGELA on 13/02/2018.
 */

public interface ChatService {
    @POST("polling")
    Call<Void> enviar(@Body Mensagem mensagem);

    @GET("polling")
    Call<Mensagem> ouvirMensagens();
}
