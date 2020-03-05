package br.com.aulaalura.agendawebservice.services;

import java.util.List;

import br.com.aulaalura.agendawebservice.dto.AlunoSync;
import br.com.aulaalura.agendawebservice.modelo.Aluno;
import br.com.aulaalura.agendawebservice.sinc.AlunoSincronizador;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by ROSANGELA on 08/02/2018.
 */

public interface AlunoService {
    /*Insere aluno*/
    @POST("aluno")
    Call<Void> insere(@Body Aluno aluno);

    /*Pega lista de aluno*/
    @GET("aluno")
    Call<AlunoSync> lista();

    /*Deleta aluno aluno*/
    @DELETE("aluno/{id}")
    Call<Void> deleta(@Path("id") String id);

    @GET("aluno/diff")
    Call<AlunoSync> novos(@Header("datahora") String versao);

    @PUT("aluno/lista")
    Call<AlunoSync> atualiza(@Body List<Aluno> alunos);
}
