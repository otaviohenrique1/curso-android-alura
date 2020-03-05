package br.com.aulaalura.agendawebservice.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.aulaalura.agendawebservice.Dao.AlunoDAO;
import br.com.aulaalura.agendawebservice.converter.AlunoConverter;
import br.com.aulaalura.agendawebservice.modelo.Aluno;
import br.com.aulaalura.agendawebservice.web.WebClient;


/**
 * Created by ROSANGELA on 13/01/2018.
 */

public class EnviaAlunosTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde", "Enviando alunos...", true, true);
    }

    /*Medoto doInBackground é executado em uma Thread secundaria*/
    @Override
    protected String doInBackground(Void... params) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converteParaJSON(alunos);

        WebClient client = new WebClient();
        String resposta = client.post(json);
        return resposta;
    }

    /*Metodo executado depois do metodo doInBackground na Thred primaria*/
    @Override
    protected void onPostExecute(String o) {
        dialog.dismiss();
        Toast.makeText(context, (String)o, Toast.LENGTH_LONG).show();
    }
}
