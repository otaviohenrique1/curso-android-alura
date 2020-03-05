package br.com.applista.listadecompras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.applista.listadecompras.model.Produto;

public class ProdutoDao extends SQLiteOpenHelper {

    public ProdutoDao(Context context) {
        super(context, "Lista de Compras", null, 1);
    }

    /*Cria tabela produtos*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Produtos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, quantidade INTEGER NOT NULL)";
        db.execSQL(sql);
    }

    /*Atualiza o banco para a versao nova*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*String sql = "";
        switch (oldVersion) {
            case 1:
                sql = "ALTER TABLE Produtos ADD COLUMN marcado BOOLEAN";
                db.execSQL(sql);
                break;
        }*/
    }

    /*Adiciona o produto na tabela produtos*/
    public void insere(Produto produto){
        /*Faz a escrita do banco de dados*/
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosDoProduto(produto);
        db.insert("Produtos", null, dados);
    }

    /*Remove o produto da tabela produtos*/
    public void deleta(Produto produto){
        /*Faz a escrita do banco de dados*/
        SQLiteDatabase db = getWritableDatabase();
        String[] parans = {String.valueOf(produto.getId())};
        db.delete("Produtos", "id = ?", parans);
    }

    /*Altera os dados de algum produto da tabela produtos*/
    public void altera(Produto produto){
        /*Faz a escrita do banco de dados*/
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosDoProduto(produto);
        String[] parans = {String.valueOf(produto.getId())};
        db.update("Alunos", dados, "id = ?", parans);
    }

    /*Lista os produtos da tabela produtos*/
    public List<Produto> buscaListaProdutos(){
        String sql = "SELECT * FROM Produtos;";
        SQLiteDatabase db = getReadableDatabase();/*Faz a leitura do banco de dados*/
        Cursor cursor = db.rawQuery(sql, null);

        List<Produto> produtos = new ArrayList<>();

        /*Percorre a tabela e coloca os dados no ArrayList*/
        while (cursor.moveToNext()) {
            Produto produto = new Produto();
            produto.setId(cursor.getLong(cursor.getColumnIndex("id")));
            produto.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            produto.setQuantidade(cursor.getLong(cursor.getColumnIndex("quantidade")));
            produtos.add(produto);
        }

        /*Fecha a conexao*/
        cursor.close();

        return produtos;
    }

    /*Busca os dados de algum produto na tabela produtos*/
    @NonNull
    private ContentValues pegaDadosDoProduto(Produto produto){
        ContentValues dados = new ContentValues();
        dados.put("nome", produto.getNome());
        dados.put("quantidade", produto.getQuantidade());
        return dados;
    }
}
