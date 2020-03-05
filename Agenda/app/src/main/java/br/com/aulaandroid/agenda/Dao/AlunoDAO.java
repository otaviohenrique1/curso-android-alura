package br.com.aulaandroid.agenda.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.aulaandroid.agenda.modelo.Aluno;

/**
 * Created by ROSANGELA on 10/01/2018.
 */

public class AlunoDAO extends SQLiteOpenHelper {

    public AlunoDAO(Context context) {
        super(context, "Agenda", null, 2);
    }

    /*Cria tabela*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL, caminhoFoto TEXT)";
        db.execSQL(sql);
    }

    /*Atualiza a tabela*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion) {
            case 1:
                sql = "ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT";
                db.execSQL(sql);
                break;
        }


    }

    /*Insere na tabela*/
    public void insere(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();/*Faz a leitura do banco de dados*/

        ContentValues dados = pegaDadosDoAluno(aluno);

        db.insert("Alunos", null, dados);
    }

    /*Pega os dados do aluno*/
    @NonNull
    private ContentValues pegaDadosDoAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        dados.put("caminhoFoto", aluno.getCaminhoFoto());
        return dados;
    }

    /*Busca a lista de alunos*/
    public List<Aluno> buscaAlunos() {
        String sql = "SELECT * from Alunos;";
        SQLiteDatabase db = getReadableDatabase();/*Faz a leitura do banco de dados*/
        Cursor c = db.rawQuery(sql, null);

        List<Aluno> alunos = new ArrayList<>();
        while(c.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));
            alunos.add(aluno);
        }
        c.close();
        return alunos;
    }

    /*Apaga o aluno da lista*/
    public void deleta(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();/*Faz a leitura do banco de dados*/
        String[] params = {aluno.getId().toString()};
        db.delete("Alunos", "id = ?", params);
    }

    /*Altera os dados do aluno*/
    public void altera(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();/*Faz a leitura do banco de dados*/

        ContentValues dados = pegaDadosDoAluno(aluno);

        String[] params = {aluno.getId().toString()};
        db.update("Alunos", dados, "id = ?", params);
    }

    public boolean ehAluno(String telefone) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Alunos WHERE telefone = ?", new String[]{telefone});
        int resultados = c.getCount();
        c.close();
        return resultados > 0;
    }
}