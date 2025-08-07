package org.example.projetopoo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.projetopoo.model.Agendamento;
import org.example.projetopoo.model.StatusAgendamento;
import org.example.projetopoo.model.Usuario;
import org.example.projetopoo.model.Servico;
import org.example.projetopoo.model.TipoUsuario;

import java.time.LocalDateTime;

public class AdminDashboardController {

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

    // Lista estática para simular um banco de dados
    private ObservableList<Agendamento> agendamentos = FXCollections.observableArrayList();

    private Usuario usuarioLogado;

    @FXML
    public void initialize() {
        clienteColumn.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        servicoColumn.setCellValueFactory(new PropertyValueFactory<>("servico"));
        dataHoraColumn.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadAgendamentos();
        agendamentosTable.setItems(agendamentos);
    }

    private void loadAgendamentos() {
        Usuario cliente1 = new Usuario("João Silva", "joao@email.com", "123", TipoUsuario.CLIENTE);
        Servico servico1 = new Servico(1, "Instalação de Ar-Condicionado", "Descrição...", 350.00);

        agendamentos.addAll(
                new Agendamento(1, cliente1, servico1, LocalDateTime.of(2025, 8, 10, 14, 0), StatusAgendamento.PENDENTE),
                new Agendamento(2, cliente1, servico1, LocalDateTime.of(2025, 8, 12, 9, 30), StatusAgendamento.PENDENTE)
        );
    }

    private Agendamento getSelectedAgendamento() {
        Agendamento selected = agendamentosTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Erro", "Nenhum agendamento selecionado.", Alert.AlertType.WARNING);
        }
        return selected;
    }

    @FXML
    public void handleConfirmAction() {
        Agendamento selected = getSelectedAgendamento();
        if (selected != null) {
            selected.setStatus(StatusAgendamento.CONFIRMADO);
            agendamentosTable.refresh();
            showAlert("Sucesso", "Agendamento confirmado com sucesso!", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void handleCompleteAction() {
        Agendamento selected = getSelectedAgendamento();
        if (selected != null) {
            selected.setStatus(StatusAgendamento.CONCLUIDO);
            agendamentosTable.refresh();
            showAlert("Sucesso", "Agendamento concluído com sucesso!", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void handleCancelAction() {
        Agendamento selected = getSelectedAgendamento();
        if (selected != null) {
            selected.setStatus(StatusAgendamento.CANCELADO);
            agendamentosTable.refresh();
            showAlert("Sucesso", "Agendamento cancelado com sucesso!", Alert.AlertType.INFORMATION);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        System.out.println("Administrador Logado: " + usuario.getNome());
    }
}