package org.example.projetopoo.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.io.Serializable;

public class Usuario implements Serializable {
    private final StringProperty nome;
    private String email;
    private String senha;
    private TipoUsuario tipo;

    // Construtor
    public Usuario(String nome, String email, String senha, TipoUsuario tipo) {
        this.nome = new SimpleStringProperty(nome);
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    // Getters e Setters
    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public ObservableValue<String> nomeProperty() {
        return this.nome;
    }
}