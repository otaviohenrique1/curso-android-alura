package br.com.aulaandroid.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.aulaandroid.agenda.adapter.AlunosAdapter;
import br.com.aulaandroid.agenda.Dao.AlunoDAO;
import br.com.aulaandroid.agenda.converter.AlunoConverter;
import br.com.aulaandroid.agenda.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {
    /*Lista de alunos*/
    private ListView listaAlunos;

    /*Cria activity*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        /*Evento de click de algum item da lista*/
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /*Exibe os dados do aluno*/
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
                /*Abre o formulario com os dados do aluno*/
                Intent intentVaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                intentVaiProFormulario.putExtra("aluno", aluno);/*Manda os dados do aluno*/
                startActivity(intentVaiProFormulario);
            }
        });

        /*Cria novo aluno*/
        Button novoAluno = (Button) findViewById(R.id.novo_aluno);
        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });

        registerForContextMenu(listaAlunos); /*Registra o componente com menu de contexto*/
    }

    /*Inicia a lista*/
    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    /*Botao do menu superior*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*Botoes da parte superior da tela*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_enviar_notas:
                /*Botao que convete dados da lista para o formato json*/
                new EnviaAlunosTask(this).execute();
                break;
            case  R.id.menu_baixar_provas:
                /*Botao que abre a parte de provas*/
                Intent vaiParaProvas = new Intent(this, ProvasActivity.class);
                startActivity(vaiParaProvas);
                break;
            case R.id.menu_mapa:
                /*Botao que abre o mapa*/
                Intent vaiParaMapa = new Intent(this, MapaActivity.class);
                startActivity(vaiParaMapa);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*Cria menu de contexto*/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

         /*Cria o item ligar no menu de contexto*/
        MenuItem itemLigar = menu.add("Ligar");
        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 123);
                } else {
                    Intent intentLigar = new Intent(Intent.ACTION_VIEW);
                    intentLigar.setData(Uri.parse("tel: " + aluno.getTelefone()));
                    startActivity(intentLigar);
                }
                return false;
            }
        });

        /*Cria o item SMS no menu de contexto*/
        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms: " + aluno.getTelefone()));
        itemSMS.setIntent(intentSMS);

        /*Cria o item visualizar no mapa no menu de contexto*/
        MenuItem itemMapa = menu.add("Visualizar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        itemMapa.setIntent(intentMapa);

        /*Cria o item visitar site no menu de contexto*/
        MenuItem itemSite = menu.add("Visitar Site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);

        String site = aluno.getSite();
        if (!site.startsWith("http://")) {
            site = "http://" + site;
        }

        intentSite.setData(Uri.parse(aluno.getSite()));
        itemSite.setIntent(intentSite);

        /*Cria o item deletar no menu de contexto*/
        MenuItem deletar = menu.add("Deletar");
        /*Acao do item do menu de contexto*/
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Toast.makeText(ListaAlunosActivity.this, "Deletar o aluno " + aluno.getNome(), Toast.LENGTH_SHORT).show();/*Menssagem*/

                /*Apaga o aluno da lista*/
                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.deleta(aluno);
                dao.close();/*Fecha a conexao*/

                /*Carrega a lista depois que o aluno foi apagado da lista*/
                carregaLista();
                return false;
            }
        });
    }

    /*Carrega a lista de alunos*/
    private void carregaLista() {
        /*Pega do banco a lista de alunos*/
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();/*Fecha a conexao com o banco*/

        /*Cria lista*/
        AlunosAdapter adapter = new AlunosAdapter(this, alunos);
        listaAlunos.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            // faz a ligacao
        } else if (requestCode == 124) {
            // faz o envio do SMS
        }
    }
}