package org.example.projetopoo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.projetopoo.model.Agendamento;
import org.example.projetopoo.model.Servico;
import org.example.projetopoo.model.StatusAgendamento;
import org.example.projetopoo.model.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoController {

    private Usuario usuarioLogado;
    private Servico servicoSelecionado;

    @FXML
    private Label servicoLabel;

    @FXML
    private DatePicker dataPicker;

    @FXML
    private ListView<LocalTime> horariosListView;

    @FXML
    private Button confirmarButton;

    public void initData(Usuario usuario, Servico servico) {
        this.usuarioLogado = usuario;
        this.servicoSelecionado = servico;
        servicoLabel.setText("Agendando: " + servico.getNome());

        // Inicializa o DatePicker para a data atual
        dataPicker.setValue(LocalDate.now());

        // Adiciona um listener para atualizar a lista de horários quando a data mudar
        dataPicker.valueProperty().addListener((observable, oldValue, newValue) -> loadAvailableHours(newValue));

        // Carrega os horários iniciais para a data atual
        loadAvailableHours(LocalDate.now());

        // Desabilita o botão de confirmação até que um horário seja selecionado
        confirmarButton.setDisable(true);
        horariosListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            confirmarButton.setDisable(newVal == null);
        });
    }

    private void loadAvailableHours(LocalDate date) {
        // Simulação de horários disponíveis. Em um projeto real, essa lógica seria mais robusta
        // e se comunicaria com a lógica de calendário do administrador.
        List<LocalTime> availableHours = new ArrayList<>();
        if (date.isAfter(LocalDate.now().minusDays(1))) { // Não mostra horários para datas passadas
            availableHours.add(LocalTime.of(9, 0));
            availableHours.add(LocalTime.of(10, 30));
            availableHours.add(LocalTime.of(14, 0));
        }

        ObservableList<LocalTime> horarios = FXCollections.observableArrayList(availableHours);
        horariosListView.setItems(horarios);
    }

    @FXML
    public void handleConfirmarButtonAction() {
        LocalTime horarioSelecionado = horariosListView.getSelectionModel().getSelectedItem();
        if (horarioSelecionado != null) {
            LocalDateTime dataHoraAgendamento = LocalDateTime.of(dataPicker.getValue(), horarioSelecionado);

            // TODO: A lógica para salvar o agendamento no banco de dados viria aqui.
            // Por enquanto, vamos apenas criar o objeto e exibir a informação.
            Agendamento novoAgendamento = new Agendamento(
                    // O ID precisaria ser gerado automaticamente
                    (int) (Math.random() * 1000),
                    usuarioLogado,
                    servicoSelecionado,
                    dataHoraAgendamento,
                    StatusAgendamento.PENDENTE
            );

            System.out.println("Novo agendamento criado:");
            System.out.println("Cliente: " + novoAgendamento.getCliente().getNome());
            System.out.println("Serviço: " + novoAgendamento.getServico().getNome());
            System.out.println("Data e Hora: " + novoAgendamento.getDataHora());
            System.out.println("Status: " + novoAgendamento.getStatus());

            // Exibe um alerta de sucesso
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Seu agendamento foi solicitado com sucesso! Aguarde a confirmação do administrador.");
            alert.showAndWait();

            // TODO: Voltar para a tela inicial do cliente
            // Stage stage = (Stage) confirmarButton.getScene().getWindow();
            // stage.close(); // ou carregar uma nova cena

        } else {
            new Alert(Alert.AlertType.WARNING, "Por favor, selecione um horário.").showAndWait();
        }
    }
}
