package br.com.applista.listadecompras.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Produto implements Serializable{
    private String nome;
    private boolean marcado;
    private BigDecimal preco;
    private Long quantidade;
    private Long id;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean getMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return getId() + " - " + getNome() + " - " + getQuantidade() + " - " + getMarcado();
    }
}
