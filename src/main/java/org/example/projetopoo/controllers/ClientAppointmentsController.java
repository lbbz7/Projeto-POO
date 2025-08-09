package org.example.projetopoo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.example.projetopoo.data.DataService;
import org.example.projetopoo.model.Agendamento;
import org.example.projetopoo.model.Servico;
import org.example.projetopoo.model.StatusAgendamento;
import org.example.projetopoo.model.Usuario;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ClientAppointmentsController {

    private Usuario usuarioLogado;
    private DataService dataService = new DataService();

    @FXML
    private TableView<Agendamento> appointmentsTable;

    @FXML
    private TableColumn<Agendamento, Servico> serviceColumn;

    @FXML
    private TableColumn<Agendamento, LocalDateTime> dateTimeColumn;

    @FXML
    private TableColumn<Agendamento, StatusAgendamento> statusColumn;

    @FXML
    private TableColumn<Agendamento, Void> actionColumn; // Ação de cancelar

    public void initData(Usuario usuario) {
        this.usuarioLogado = usuario;

        // Define as fábricas de células para as colunas
        // Agora, referenciamos diretamente as propriedades observáveis do Agendamento
        serviceColumn.setCellValueFactory(cellData -> cellData.getValue().servicoProperty());
        dateTimeColumn.setCellValueFactory(cellData -> cellData.getValue().dataHoraProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        // Customiza a exibição da coluna de serviço para mostrar o nome
        serviceColumn.setCellFactory(column -> new TableCell<Agendamento, Servico>() {
            @Override
            protected void updateItem(Servico servico, boolean empty) {
                super.updateItem(servico, empty);
                if (empty || servico == null) {
                    setText(null);
                } else {
                    setText(servico.getNome());
                }
            }
        });

        // Customiza a exibição da data e hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        dateTimeColumn.setCellFactory(column -> new TableCell<Agendamento, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        });

        // TODO: Implementar a coluna de ação para cancelar agendamento
        // actionColumn.setCellFactory(...);

        loadClientAgendamentos();
    }

    private void loadClientAgendamentos() {
        // Carrega todos os dados do DataService
        List<Usuario> usuariosCadastrados = dataService.loadUsuarios();
        List<Servico> servicosDisponiveis = dataService.loadServicos();
        List<Agendamento> allAgendamentos = dataService.loadAgendamentos(usuariosCadastrados, servicosDisponiveis);

        // Filtra os agendamentos para mostrar apenas os do usuário logado
        List<Agendamento> agendamentosDoCliente = allAgendamentos.stream()
                .filter(a -> a.getCliente().getEmail().equals(usuarioLogado.getEmail()))
                .collect(Collectors.toList());

        appointmentsTable.getItems().setAll(agendamentosDoCliente);
    }

    @FXML
    public void handleBackButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projetopoo/clientDashboard-view.fxml"));
            Parent root = loader.load();

            ClientDashboardController controller = loader.getController();
            controller.setUsuarioLogado(usuarioLogado);

            Stage stage = (Stage) appointmentsTable.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Painel do Cliente");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}