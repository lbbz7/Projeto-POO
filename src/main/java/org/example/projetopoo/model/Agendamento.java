package org.example.projetopoo.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Agendamento implements Serializable {
    private int id;
    private final ObjectProperty<Usuario> cliente;
    private final ObjectProperty<Servico> servico;
    private final ObjectProperty<LocalDateTime> dataHora;
    private final ObjectProperty<StatusAgendamento> status;

    // Construtor
    public Agendamento(int id, Usuario cliente, Servico servico, LocalDateTime dataHora, StatusAgendamento status) {
        this.id = id;
        this.cliente = new SimpleObjectProperty<>(cliente);
        this.servico = new SimpleObjectProperty<>(servico);
        this.dataHora = new SimpleObjectProperty<>(dataHora);
        this.status = new SimpleObjectProperty<>(status);
    }

    // Getters e Setters com propriedades observáveis
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getCliente() {
        return cliente.get();
    }

    public ObjectProperty<Usuario> clienteProperty() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente.set(cliente);
    }

    public Servico getServico() {
        return servico.get();
    }

    public ObjectProperty<Servico> servicoProperty() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico.set(servico);
    }

    public LocalDateTime getDataHora() {
        return dataHora.get();
    }

    public ObjectProperty<LocalDateTime> dataHoraProperty() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora.set(dataHora);
    }

    public StatusAgendamento getStatus() {
        return status.get();
    }

    public ObjectProperty<StatusAgendamento> statusProperty() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status.set(status);
    }

    // Sobrescrita do toString para exibição
    @Override
    public String toString() {
        return "Agendamento{" +
                "id=" + id +
                ", cliente=" + cliente.get().getEmail() +
                ", servico=" + servico.get().getNome() +
                ", dataHora=" + dataHora.get() +
                ", status=" + status.get() +
                '}';
    }
}