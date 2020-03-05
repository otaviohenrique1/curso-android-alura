package br.com.listaprodutoscompras.listaprodutoscompras.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.listaprodutoscompras.listaprodutoscompras.models.Produto;

@Dao
public interface ProdutosDao {
    @Insert
    void insere(Produto produto);

    @Query("select * from Produto order by nome")
    List<Produto> busca();

    @Query("select * from Produto where :nome")
    List<Produto> buscaProduto(String nome);

    @Update
    void altera(Produto produto);

    @Delete
    void deleta(Produto produto);
}
