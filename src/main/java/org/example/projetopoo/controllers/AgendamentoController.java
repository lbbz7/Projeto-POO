package org.example.projetopoo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AgendamentoController {

    private Usuario usuarioLogado;
    private DataService dataService = new DataService();

    @FXML
    private ComboBox<Servico> servicoComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> timeComboBox;

    @FXML
    public void initialize() {
        // Carrega os serviços disponíveis
        List<Servico> servicos = dataService.loadServicos();
        servicoComboBox.getItems().setAll(servicos);

        // Define como a ComboBox exibe o objeto Serviço
        servicoComboBox.setCellFactory(lv -> new javafx.scene.control.ListCell<Servico>() {
            @Override
            protected void updateItem(Servico item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null || empty ? null : item.getNome() + " - R$" + String.format("%.2f", item.getPreco()));
            }
        });
        servicoComboBox.setButtonCell(servicoComboBox.getCellFactory().call(null));

        // Define os horários disponíveis (exemplo)
        List<String> horarios = Arrays.asList(
                "08:00", "09:00", "10:00", "11:00",
                "13:00", "14:00", "15:00", "16:00");
        timeComboBox.getItems().setAll(horarios);
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    @FXML
    public void handleScheduleButtonAction() {
        Servico selectedServico = servicoComboBox.getSelectionModel().getSelectedItem();
        LocalDate selectedDate = datePicker.getValue();
        String selectedTime = timeComboBox.getSelectionModel().getSelectedItem();

        if (selectedServico == null || selectedDate == null || selectedTime == null) {
            new Alert(Alert.AlertType.ERROR, "Por favor, selecione um serviço, data e horário.").showAndWait();
            return;
        }

        LocalTime time = LocalTime.parse(selectedTime);
        LocalDateTime dataHoraAgendamento = LocalDateTime.of(selectedDate, time);

        // Carrega os agendamentos existentes, adiciona o novo e salva
        List<Agendamento> agendamentos = dataService.loadAgendamentos(dataService.loadUsuarios(), dataService.loadServicos());

        int newId = 1;
        if (!agendamentos.isEmpty()) {
            newId = agendamentos.get(agendamentos.size() - 1).getId() + 1;
        }

        Agendamento novoAgendamento = new Agendamento(newId, usuarioLogado, selectedServico, dataHoraAgendamento, StatusAgendamento.PENDENTE);
        agendamentos.add(novoAgendamento);
        dataService.saveAgendamentos(agendamentos);

        new Alert(Alert.AlertType.INFORMATION, "Agendamento realizado com sucesso!").showAndWait();
        handleBackButtonAction();
    }

    @FXML
    public void handleBackButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projetopoo/clientDashboard-view.fxml"));
            Parent root = loader.load();

            ClientDashboardController controller = loader.getController();
            controller.setUsuarioLogado(usuarioLogado);

            Stage stage = (Stage) servicoComboBox.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Painel do Cliente");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}