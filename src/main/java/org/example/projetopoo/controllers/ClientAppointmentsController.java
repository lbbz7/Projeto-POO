package org.example.projetopoo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.projetopoo.model.Agendamento;
import org.example.projetopoo.model.StatusAgendamento;
import org.example.projetopoo.model.Usuario;
import org.example.projetopoo.model.Servico;
import java.io.IOException;
import java.time.LocalDateTime;

public class ClientAppointmentsController {

    private Usuario usuarioLogado;

    @FXML
    private TableView<Agendamento> agendamentosTable;

    @FXML
    private TableColumn<Agendamento, String> servicoColumn;

    @FXML
    private TableColumn<Agendamento, LocalDateTime> dataHoraColumn;

    @FXML
    private TableColumn<Agendamento, StatusAgendamento> statusColumn;

    public void initData(Usuario usuario) {
        this.usuarioLogado = usuario;

        servicoColumn.setCellValueFactory(new PropertyValueFactory<>("servico"));
        dataHoraColumn.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadClientAgendamentos();
    }

    private void loadClientAgendamentos() {
        // Simulação de agendamentos. Em um projeto real, você buscaria por ID do usuário.
        ObservableList<Agendamento> agendamentosDoCliente = FXCollections.observableArrayList(
                new Agendamento(1, usuarioLogado,
                        new Servico(1, "Manutenção Preventiva", "", 150.00),
                        LocalDateTime.of(2025, 8, 15, 10, 0), StatusAgendamento.PENDENTE),
                new Agendamento(2, usuarioLogado,
                        new Servico(2, "Instalação de Ar-Condicionado", "", 350.00),
                        LocalDateTime.of(2025, 8, 20, 14, 30), StatusAgendamento.CONFIRMADO)
        );
        agendamentosTable.setItems(agendamentosDoCliente);
    }

    @FXML
    public void handleBackButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projetopoo/views/clientDashboard-view.fxml"));
            Parent root = loader.load();

            ClientDashboardController controller = loader.getController();
            controller.setUsuarioLogado(usuarioLogado);

            Stage stage = (Stage) agendamentosTable.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Painel do Cliente");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}