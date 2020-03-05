package br.com.alura.roomapplication.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

public class GeradorDeBancoDeDados {
    public AluraDatabase gera(Context contexto) {
        AluraDatabase aluraDB = Room
                .databaseBuilder(contexto, AluraDatabase.class, "AluraDB")
                .allowMainThreadQueries()
                .addMigrations(doisParaTres())
                .build();

        return aluraDB;
    }

    private Migration doisParaTres() {
        return new Migration(2,3) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {
                String sql = "alter table Prova add column dataRealizacao integer;";
                database.execSQL(sql);
            }
        };
    }
}
