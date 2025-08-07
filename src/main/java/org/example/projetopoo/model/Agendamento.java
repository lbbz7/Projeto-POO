package org.example.projetopoo.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Agendamento implements Serializable {
    private int id;
    private Usuario cliente;
    private Servico servico;
    private LocalDateTime dataHora;
    private StatusAgendamento status;

    // Construtor
    public Agendamento(int id, Usuario cliente, Servico servico, LocalDateTime dataHora, StatusAgendamento status) {
        this.id = id;
        this.cliente = cliente;
        this.servico = servico;
        this.dataHora = dataHora;
        this.status = status;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }

    // Sobrescrita do toString para exibição
    @Override
    public String toString() {
        return "Agendamento{" +
                "id=" + id +
                ", cliente=" + cliente.getEmail() +
                ", servico=" + servico.getNome() +
                ", dataHora=" + dataHora +
                ", status=" + status +
                '}';
    }
}