package br.com.aulaandroid.agenda.modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ROSANGELA on 15/01/2018.
 */

public class Prova implements Serializable{
    /*Atributos*/
    private String materia;
    private String data;
    private List<String> topicos;

    /*Metodo construtor*/
    public Prova(String materia, String data, List<String> topicos) {
        this.materia = materia;
        this.data = data;
        this.topicos = topicos;
    }

    /*Metodos get e set*/
    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<String> getTopicos() {
        return topicos;
    }

    public void setTopicos(List<String> topicos) {
        this.topicos = topicos;
    }

    /*Metodo toString*/
    /*Mostra como os dados serao exibidos na lista*/
    @Override
    public String toString() {
        return this.materia;
    }
}