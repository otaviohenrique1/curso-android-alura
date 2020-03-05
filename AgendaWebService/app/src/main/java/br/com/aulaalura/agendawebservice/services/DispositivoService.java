package br.com.aulaalura.agendawebservice.services;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by ROSANGELA on 22/02/2018.
 */

public interface DispositivoService {
    @POST("firebase/dispositivo")
    Call<Void> enviaToken(@Header("token") String token);
}
