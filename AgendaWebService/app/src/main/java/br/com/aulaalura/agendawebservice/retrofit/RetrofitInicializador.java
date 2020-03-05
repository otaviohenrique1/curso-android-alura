package br.com.aulaalura.agendawebservice.retrofit;

import br.com.aulaalura.agendawebservice.services.AlunoService;
import br.com.aulaalura.agendawebservice.services.DispositivoService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by ROSANGELA on 07/02/2018.
 */

public class RetrofitInicializador {
    private final Retrofit retrofit;

    public RetrofitInicializador() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);

        String enderecoIPServidor = "192.168.1.32";
        String endereco = "http://" + enderecoIPServidor + ":8080/api/aluno";
        retrofit = new Retrofit.Builder()
                .baseUrl(endereco)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public AlunoService getAlunoService() {
        return retrofit.create(AlunoService.class);/*Cria AlunoService*/
    }

    public DispositivoService getDispositivoService() {
        return retrofit.create(DispositivoService.class);
    }
}
