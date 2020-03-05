package br.com.listaprodutoscompras.listaprodutoscompras.database;

import android.arch.persistence.room.Room;
import android.content.Context;

public class GeradorDeBancoDeDados {
    public ProdutosDatabase gera(Context contexto) {
        ProdutosDatabase produtosDb = Room
                .databaseBuilder(contexto, ProdutosDatabase.class, "ProdutosDB")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()/*Apaga banco(trocar para addMigrations() caso o codigo seja mudado)*/
                /*Exemplo -> .addMigrations(doisParaTres())*/
                .build();
        return produtosDb;
    }

    /*Exemplo de migrations*/
    /*
    private Migration doisParaTres() {
        return new Migration(2,3) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {
                String sql = "alter table Produtos add column data integer;";
                database.execSQL(sql);
            }
        };
    }
    */
}
