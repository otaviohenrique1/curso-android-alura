package br.com.alura.roomapplication.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Calendar;

@Entity
public class Prova implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String materia;

    private Calendar dataRealizacao;

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return materia;
    }

    public Calendar getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(Calendar dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }
}
