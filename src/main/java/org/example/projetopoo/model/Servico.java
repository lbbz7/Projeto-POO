package org.example.projetopoo.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.io.Serializable;

public class Servico implements Serializable {
    private int id;
    private final StringProperty nome;
    private String descricao;
    private double preco;

    // Construtor
    public Servico(int id, String nome, String descricao, double preco) {
        this.id = id;
        this.nome = new SimpleStringProperty(nome);
        this.descricao = descricao;
        this.preco = preco;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public ObservableValue<String> nomeProperty() {
        return this.nome;
    }
}