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
import org.example.projetopoo.data.DataService;
import org.example.projetopoo.model.Agendamento;
import org.example.projetopoo.model.Servico;
import org.example.projetopoo.model.StatusAgendamento;
import org.example.projetopoo.model.Usuario;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientAppointmentsController {

    private Usuario usuarioLogado;
    private DataService dataService = new DataService();

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
        // TODO: A lista de serviços precisa ser carregada de forma persistente
        List<Servico> servicosDisponiveis = new ArrayList<>();
        servicosDisponiveis.add(new Servico(1, "Instalação de Ar-Condicionado", "", 350.00));
        servicosDisponiveis.add(new Servico(2, "Manutenção Preventiva", "", 150.00));
        servicosDisponiveis.add(new Servico(3, "Conserto de Vazamentos", "", 200.00));

        // Carrega todos os agendamentos do arquivo
        List<Usuario> usuariosCadastrados = dataService.loadUsuarios();
        List<Agendamento> allAgendamentos = dataService.loadAgendamentos(usuariosCadastrados, servicosDisponiveis);

        // Filtra os agendamentos para mostrar apenas os do usuário logado
        List<Agendamento> agendamentosDoCliente = allAgendamentos.stream()
                .filter(a -> a.getCliente().getEmail().equals(usuarioLogado.getEmail()))
                .collect(Collectors.toList());

        ObservableList<Agendamento> agendamentos = FXCollections.observableArrayList(agendamentosDoCliente);
        agendamentosTable.setItems(agendamentos);
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