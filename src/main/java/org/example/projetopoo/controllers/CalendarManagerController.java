package org.example.projetopoo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CalendarManagerController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private ListView<LocalTime> availableHoursList;

    @FXML
    public void initialize() {
        datePicker.setValue(LocalDate.now());
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> loadAvailableHours(newValue));
        loadAvailableHours(LocalDate.now());
    }

    private void loadAvailableHours(LocalDate date) {
        // Simulação de horários disponíveis. Em um projeto real, viriam do banco de dados.
        List<LocalTime> hours = new ArrayList<>();
        hours.add(LocalTime.of(9, 0));
        hours.add(LocalTime.of(10, 30));
        hours.add(LocalTime.of(14, 0));

        ObservableList<LocalTime> availableHours = FXCollections.observableArrayList(hours);
        availableHoursList.setItems(availableHours);
    }

    @FXML
    public void handleAddHourButtonAction() {
        // Lógica para adicionar um novo horário disponível
        // Por exemplo, você pode abrir uma caixa de diálogo para o admin inserir um horário
        System.out.println("Adicionar novo horário");
    }

    @FXML
    public void handleRemoveHourButtonAction() {
        // Lógica para remover o horário selecionado
        LocalTime selectedHour = availableHoursList.getSelectionModel().getSelectedItem();
        if (selectedHour != null) {
            availableHoursList.getItems().remove(selectedHour);
            System.out.println("Horário removido: " + selectedHour);
        }
    }
}