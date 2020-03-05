package br.com.aulaalura.agendawebservice.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.aulaalura.agendawebservice.modelo.Aluno;


/**
 * Created by ROSANGELA on 10/01/2018.
 */

public class AlunoDAO extends SQLiteOpenHelper {

    public AlunoDAO(Context context) {
        super(context, "Agenda", null, 6);
    }

    /*Cria tabela*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "endereco TEXT, " +
                "telefone TEXT, " +
                "site TEXT, " +
                "nota REAL, " +
                "caminhoFoto TEXT, " +
                "sincronizado INT DEFAULT 0, " +
                "desativado INT DEFAULT 0);";
        db.execSQL(sql);
    }

    /*Atualiza a tabela*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion) {
            case 1:
                /*Atualizando o bd para a versao 2*/
                sql = "ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT";
                db.execSQL(sql);
            case 2:
                /**/
                /*Atualizando o bd para a versao 3 realizando uma migration no banco de dados*/
                String criandoTabelaNova = "CREATE TABLE Alunos_novo " +
                        "(id CHAR(36) PRIMARY KEY, " +
                        "nome TEXT NOT NULL, " +
                        "endereco TEXT, " +
                        "telefone TEXT, " +
                        "site TEXT, " +
                        "nota REAL, " +
                        "caminhoFoto TEXT);";
                db.execSQL(criandoTabelaNova);

                String inserindoAlunosNaTabelaNova = "INSER INTO Alunos_novo " +
                        "(id, nome, endereco, telefone, site, nota, caminhoFoto) "+
                        "SELECT id, nome, endereco, telefone, site, nota, caminhoFoto " +
                        "FROM Alunos";
                /*Pega os dados da tabela Alunos e insere na tabela Alunos_novo*/
                db.execSQL(inserindoAlunosNaTabelaNova);

                String removendoTabelaAntiga = "DROP TABLE Alunos";
                db.execSQL(removendoTabelaAntiga);

                String alterandoNomeDaTabelaNova = "ALTER TABLE Alunos_novo "+
                        "RENAME TO Alunos";
                /*Renomeia a tabela Alunos_novo para Alunos*/
                db.execSQL(alterandoNomeDaTabelaNova);
            case 3:
                /*Atualizando o bd para a versao 4 com o UUID*/
                String buscaAlunos = "SELECT * FROM Alunos";
                Cursor cursor = db.rawQuery(buscaAlunos, null);

                List<Aluno> alunos = populaAlunos(cursor);

                String atualizaIdDoAluno = "UPDATE Alunos SET id=? WHERE id=?";

                for (Aluno aluno : alunos) {
                    db.execSQL(atualizaIdDoAluno, new String[]{geraUUID(), aluno.getId()});
                }
            case 4:
                String adicionaCampoSicronizado = "ALTER TABLE Alunos ADD COLUMN sincronizado DEFAULT 0";
                db.execSQL(adicionaCampoSicronizado);
            case 5:
                String adicionaCampoDesativado = "ALTER TABLE Alunos ADD COLUMN desativado INT DEFAULT 0";
                db.execSQL(adicionaCampoDesativado);
        }
    }

    private String geraUUID() {
        return UUID.randomUUID().toString();
    }

    /*Insere na tabela*/
    public void insere(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();/*Faz a leitura do banco de dados*/
        insereIdSeNecessario(aluno);
        ContentValues dados = pegaDadosDoAluno(aluno);
        db.insert("Alunos", null, dados);
//        long id = db.insert("Alunos", null, dados);
//        aluno.setId(id);
    }

    private void insereIdSeNecessario(Aluno aluno) {
        if (aluno.getId() == null) {
            aluno.setId(geraUUID());
        }
    }

    /*Pega os dados do aluno*/
    @NonNull
    private ContentValues pegaDadosDoAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("id", aluno.getId());
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        dados.put("caminhoFoto", aluno.getCaminhoFoto());
        dados.put("sincronizado", aluno.getSincronizado());
        dados.put("desativado", aluno.getDesativado());
        return dados;
    }

    /*Busca a lista de alunos*/
    public List<Aluno> buscaAlunos() {
        /*String sql = "SELECT * from Alunos;";*/
        String sql = "SELECT * from Alunos WHERE desativado = 0;";
        SQLiteDatabase db = getReadableDatabase();/*Faz a leitura do banco de dados*/
        Cursor c = db.rawQuery(sql, null);

        List<Aluno> alunos = populaAlunos(c);
        c.close();
        return alunos;
    }

    @NonNull
    private List<Aluno> populaAlunos(Cursor c) {
        List<Aluno> alunos = new ArrayList<>();
        while(c.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(c.getString(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));
            aluno.setSincronizado(c.getInt(c.getColumnIndex("sincronizado")));
            aluno.setDesativado(c.getInt(c.getColumnIndex("desativado")));
            alunos.add(aluno);
        }
        return alunos;
    }

    /*Apaga o aluno da lista*/
    public void deleta(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();/*Faz a leitura do banco de dados*/
        String[] params = {aluno.getId().toString()};
        if(aluno.estaDesativado()) {
            db.delete("Alunos", "id = ?", params);
        } else {
            aluno.desativa();
            altera(aluno);
        }
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
        String sql = "SELECT * FROM ALunos WHERE telefone=?";
        Cursor c = db.rawQuery(sql, new String[]{telefone});
        int resultados = c.getCount();
        c.close();
        return resultados > 0;
    }

    /*Pega os alunos do servidor e salva no bd interno da aplicacao*/
    public void sincroniza(List<Aluno> alunos) {
        for (Aluno aluno : alunos) {
            aluno.sincroniza();
            if (existe(aluno)) {
                if (aluno.estaDesativado()) {
                    deleta(aluno);
                } else {
                    altera(aluno);
                }
            } else if (!aluno.estaDesativado()) {
                insere(aluno);
            }
        }
    }

    private boolean existe(Aluno aluno) {
        SQLiteDatabase db = getReadableDatabase();
        String existe = "SELECT id FROM Alunos WHERE id = ? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{aluno.getId()});
        int quantidade = cursor.getCount();
        return quantidade > 0;
    }

    public List<Aluno> listaNaoSincronizados(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Alunos WHERE sincronizado = 0";
        Cursor cursor = db.rawQuery(sql, null);
        return populaAlunos(cursor);
    }
}