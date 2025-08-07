package org.example.projetopoo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.projetopoo.data.DataService;
import org.example.projetopoo.model.Agendamento;
import org.example.projetopoo.model.StatusAgendamento;
import org.example.projetopoo.model.Usuario;
import org.example.projetopoo.model.Servico;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboardController {

    private Usuario usuarioLogado;
    private DataService dataService = new DataService();

    @FXML
    private Label welcomeLabel;

    @FXML
    private TableView<Agendamento> agendamentosTable;

    @FXML
    private TableColumn<Agendamento, String> clienteColumn;

    @FXML
    private TableColumn<Agendamento, String> servicoColumn;

    @FXML
    private TableColumn<Agendamento, LocalDateTime> dataHoraColumn;

    @FXML
    private TableColumn<Agendamento, StatusAgendamento> statusColumn;

    @FXML
    public void initialize() {
        // Vincula as colunas da tabela aos atributos da classe Agendamento
        clienteColumn.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        servicoColumn.setCellValueFactory(new PropertyValueFactory<>("servico"));
        dataHoraColumn.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Carrega os agendamentos do arquivo
        loadAgendamentos();
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        if (usuario != null) {
            welcomeLabel.setText("Olá, " + usuario.getNome() + "!");
        }
    }

    private void loadAgendamentos() {
        // TODO: A lista de serviços precisa ser carregada de forma persistente
        // Por enquanto, usaremos uma lista estática de serviços para carregar os agendamentos
        List<Servico> servicosDisponiveis = new ArrayList<>();
        servicosDisponiveis.add(new Servico(1, "Instalação de Ar-Condicionado", "", 350.00));
        servicosDisponiveis.add(new Servico(2, "Manutenção Preventiva", "", 150.00));
        servicosDisponiveis.add(new Servico(3, "Conserto de Vazamentos", "", 200.00));

        List<Usuario> usuariosCadastrados = dataService.loadUsuarios();
        List<Agendamento> agendamentos = dataService.loadAgendamentos(usuariosCadastrados, servicosDisponiveis);

        agendamentosTable.getItems().setAll(agendamentos);
    }
}