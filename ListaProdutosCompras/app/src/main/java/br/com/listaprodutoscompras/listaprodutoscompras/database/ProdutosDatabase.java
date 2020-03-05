package br.com.listaprodutoscompras.listaprodutoscompras.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.com.listaprodutoscompras.listaprodutoscompras.database.dao.ProdutosDao;
import br.com.listaprodutoscompras.listaprodutoscompras.models.Produto;

@Database(entities = {Produto.class}, version = 1)
public abstract class ProdutosDatabase extends RoomDatabase{
    public abstract ProdutosDao getProdutoDao();
}
