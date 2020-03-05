package br.com.alurachat.ichat_alura.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ROSANGELA on 13/02/2018.
 */

public class Mensagem implements Serializable{
    @SerializedName("text")
    private String texto;
    private int id;

    public Mensagem(int id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public int getId() {
        return id;
    }
}
