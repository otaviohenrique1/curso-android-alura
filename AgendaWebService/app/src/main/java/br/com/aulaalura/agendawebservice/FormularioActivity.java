package br.com.aulaalura.agendawebservice;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import br.com.aulaalura.agendawebservice.Dao.AlunoDAO;
import br.com.aulaalura.agendawebservice.modelo.Aluno;
import br.com.aulaalura.agendawebservice.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FormularioActivity extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 567;
    private FormularioHelper helper;
    private String caminhoFoto;

    /*Cria activity do formulario*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);

        Intent intent = getIntent();
        /*Pega os dados do aluno enviados pela lista de alunos*/
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        if(aluno != null){
            /*Verifica se os dados do aluno estao vazios*/
            helper.preencheFormulario(aluno);
        }

        /*Botao que tira foto*/
        Button bottaoFoto = (Button) findViewById(R.id.formulario_botao_foto);
        bottaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Acao do botao que tira foto*/
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); /*Abre a camera*/
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg"; /*Nome da foto*/
                File arquivoFoto = new File(caminhoFoto); /*Cria arquivo da foto e coloca o caminho da foto*/
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, CODIGO_CAMERA); /*Pega a foto e coloca no ImageView*/
            }
        });
    }

    /*Pega a foto e coloca no ImageView*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODIGO_CAMERA) {
                helper.carregaImagem(caminhoFoto);
            }
        }
    }

    /*Cria menu superior da tela de cadastro*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*Acao do botao do menu superior*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                Aluno aluno = helper.getAluno();

                aluno.desincroniza();

                AlunoDAO dao = new AlunoDAO(this);

                /*Verifica se o aluno tem id no banco de dados*/
                if(aluno.getId() != null){
                    /*Se tem id no banco de dados*/
                    /*Altera os dados*/
                    dao.altera(aluno);
                }else{
                    /*Se não tem id no banco de dados*/
                    /*Insere os dados*/
                    dao.insere(aluno);
                }

                dao.close();/*Fecha a conexao*/

                Call call = new RetrofitInicializador()
                        .getAlunoService()
                        .insere(aluno);

                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        Log.i("onResponse", "requisicao com sucesso");
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Log.e("onFailure", "requisicao falhou");
                    }
                });/*Executa em uma thread separada da thread principal*/

                Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " salvo!", Toast.LENGTH_SHORT).show();/*Exibe menssagem*/
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}