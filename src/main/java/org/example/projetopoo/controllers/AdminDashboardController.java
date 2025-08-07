package org.example.projetopoo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.projetopoo.data.DataService;
import org.example.projetopoo.model.Agendamento;
import org.example.projetopoo.model.StatusAgendamento;
import org.example.projetopoo.model.Usuario;
import org.example.projetopoo.model.Servico;
import java.io.IOException;
import java.time.LocalDateTime;
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
    private TableColumn<Agendamento, Void> actionColumn;

    @FXML
    private Button manageServicesButton;

    @FXML
    private Button logoutButton;

    @FXML
    public void initialize() {
        clienteColumn.setCellValueFactory(cellData -> cellData.getValue().getCliente().nomeProperty());
        servicoColumn.setCellValueFactory(cellData -> cellData.getValue().getServico().nomeProperty());
        dataHoraColumn.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        actionColumn.setCellFactory(param -> new TableCell<Agendamento, Void>() {
            private final Button confirmButton = new Button("Confirmar");
            private final Button cancelButton = new Button("Cancelar");
            private final HBox pane = new HBox(5, confirmButton, cancelButton);

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Agendamento agendamento = getTableView().getItems().get(getIndex());

                    confirmButton.setDisable(agendamento.getStatus() != StatusAgendamento.PENDENTE);
                    cancelButton.setDisable(agendamento.getStatus() != StatusAgendamento.PENDENTE);

                    confirmButton.setOnAction(event -> {
                        agendamento.setStatus(StatusAgendamento.CONFIRMADO);
                        updateAndSaveAgendamentos();
                        getTableView().refresh();
                    });

                    cancelButton.setOnAction(event -> {
                        agendamento.setStatus(StatusAgendamento.CANCELADO);
                        updateAndSaveAgendamentos();
                        getTableView().refresh();
                    });

                    setGraphic(pane);
                }
            }
        });

        loadAgendamentos();
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        if (usuario != null) {
            welcomeLabel.setText("Olá, " + usuario.getNome() + "!");
        }
    }

    private void loadAgendamentos() {
        List<Servico> servicosDisponiveis = dataService.loadServicos();
        List<Usuario> usuariosCadastrados = dataService.loadUsuarios();
        List<Agendamento> agendamentos = dataService.loadAgendamentos(usuariosCadastrados, servicosDisponiveis);

        agendamentosTable.getItems().setAll(agendamentos);
    }

    private void updateAndSaveAgendamentos() {
        dataService.saveAgendamentos(agendamentosTable.getItems());
    }

    @FXML
    private void handleManageServicesButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projetopoo/adminServices-view.fxml"));
            Parent root = loader.load();

            AdminServicesController controller = loader.getController();
            controller.setUsuarioLogado(usuarioLogado);

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Gerenciar Serviços");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogoutButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projetopoo/login-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}