package org.example.projetopoo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.example.projetopoo.data.DataService;
import org.example.projetopoo.model.Agendamento;
import org.example.projetopoo.model.Servico;
import org.example.projetopoo.model.StatusAgendamento;
import org.example.projetopoo.model.Usuario;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class AgendamentoController {

    private Usuario usuarioLogado;
    private DataService dataService = new DataService();

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> timeComboBox;

    @FXML
    private ComboBox<String> servicoComboBox;

    @FXML
    private Button scheduleButton;

    @FXML
    public void initialize() {
        for (int hour = 8; hour <= 17; hour++) {
            timeComboBox.getItems().add(String.format("%02d:00", hour));
        }

        List<Servico> servicos = dataService.loadServicos();
        for (Servico servico : servicos) {
            servicoComboBox.getItems().add(servico.getNome());
        }
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    @FXML
    private void handleScheduleButtonAction() {
        String selectedServicoNome = servicoComboBox.getValue();
        LocalDate selectedDate = datePicker.getValue();
        String selectedTimeStr = timeComboBox.getValue();

        if (selectedServicoNome == null || selectedDate == null || selectedTimeStr == null) {
            new Alert(Alert.AlertType.ERROR, "Por favor, preencha todos os campos.").showAndWait();
            return;
        }

        LocalTime selectedTime = LocalTime.parse(selectedTimeStr);
        LocalDateTime agendamentoDateTime = LocalDateTime.of(selectedDate, selectedTime);

        List<Servico> servicos = dataService.loadServicos();
        Optional<Servico> servicoOptional = servicos.stream()
                .filter(s -> s.getNome().equals(selectedServicoNome))
                .findFirst();

        if (servicoOptional.isPresent()) {
            Servico servico = servicoOptional.get();

            List<Usuario> usuarios = dataService.loadUsuarios();
            List<Agendamento> agendamentos = dataService.loadAgendamentos(usuarios, servicos);

            int newId = 1;
            if (!agendamentos.isEmpty()) {
                newId = agendamentos.get(agendamentos.size() - 1).getId() + 1;
            }

            Agendamento novoAgendamento = new Agendamento(newId, usuarioLogado, servico, agendamentoDateTime, StatusAgendamento.PENDENTE);
            agendamentos.add(novoAgendamento);

            dataService.saveAgendamentos(agendamentos);
            new Alert(Alert.AlertType.INFORMATION, "Agendamento realizado com sucesso!").showAndWait();
            handleBackButtonAction();
        }
    }

    @FXML
    private void handleBackButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projetopoo/clientDashboard-view.fxml"));
            Parent root = loader.load();

            ClientDashboardController controller = loader.getController();
            controller.setUsuarioLogado(usuarioLogado);

            Stage stage = (Stage) scheduleButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Painel do Cliente");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}