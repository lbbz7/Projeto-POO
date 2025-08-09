package org.example.projetopoo.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.io.Serializable;

public class Servico implements Serializable {
    private int id;
    private final StringProperty nome;
    private final StringProperty descricao;
    private final DoubleProperty preco;

    // Construtor
    public Servico(int id, String nome, String descricao, double preco) {
        this.id = id;
        this.nome = new SimpleStringProperty(nome);
        this.descricao = new SimpleStringProperty(descricao);
        this.preco = new SimpleDoubleProperty(preco);
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
        return descricao.get();
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }

    public double getPreco() {
        return preco.get();
    }

    public void setPreco(double preco) {
        this.preco.set(preco);
    }

    public ObservableValue<String> nomeProperty() {
        return this.nome;
    }

    public StringProperty descricaoProperty() {
        return this.descricao;
    }

    public DoubleProperty precoProperty() {
        return this.preco;
    }
}