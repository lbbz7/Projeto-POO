package org.example.projetopoo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.projetopoo.data.DataService;
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

    private DataService dataService = new DataService();
    private List<Agendamento> agendamentos;

    public void initData(Usuario usuario, Servico servico) {
        this.usuarioLogado = usuario;
        this.servicoSelecionado = servico;
        servicoLabel.setText("Agendando: " + servico.getNome());

        dataPicker.setValue(LocalDate.now());

        dataPicker.valueProperty().addListener((observable, oldValue, newValue) -> loadAvailableHours(newValue));

        // Carrega os agendamentos do arquivo
        this.agendamentos = dataService.loadAgendamentos(dataService.loadUsuarios(), loadServicos());

        loadAvailableHours(LocalDate.now());

        confirmarButton.setDisable(true);
        horariosListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            confirmarButton.setDisable(newVal == null);
        });
    }

    private List<Servico> loadServicos() {
        // Simulação dos serviços disponíveis
        List<Servico> servicos = new ArrayList<>();
        servicos.add(new Servico(1, "Instalação de Ar-Condicionado", "Instalação profissional para todos os tipos de aparelhos.", 350.00));
        servicos.add(new Servico(2, "Manutenção Preventiva", "Limpeza e verificação completa para o bom funcionamento do seu equipamento.", 150.00));
        servicos.add(new Servico(3, "Conserto de Vazamentos", "Identificação e reparo de vazamentos em sistemas de refrigeração.", 200.00));
        return servicos;
    }

    private void loadAvailableHours(LocalDate date) {
        List<LocalTime> availableHours = new ArrayList<>();
        if (date.isAfter(LocalDate.now().minusDays(1))) {
            availableHours.add(LocalTime.of(9, 0));
            availableHours.add(LocalTime.of(10, 30));
            availableHours.add(LocalTime.of(14, 0));
        }

        // Simulação de horários ocupados. Em um projeto real, você filtraria os horários
        // que já estão na lista de agendamentos para a data selecionada.

        ObservableList<LocalTime> horarios = FXCollections.observableArrayList(availableHours);
        horariosListView.setItems(horarios);
    }

    @FXML
    public void handleConfirmarButtonAction() {
        LocalTime horarioSelecionado = horariosListView.getSelectionModel().getSelectedItem();
        if (horarioSelecionado != null) {
            LocalDateTime dataHoraAgendamento = LocalDateTime.of(dataPicker.getValue(), horarioSelecionado);

            Agendamento novoAgendamento = new Agendamento(
                    (int) (Math.random() * 1000),
                    usuarioLogado,
                    servicoSelecionado,
                    dataHoraAgendamento,
                    StatusAgendamento.PENDENTE
            );

            // Adiciona o novo agendamento à lista
            agendamentos.add(novoAgendamento);

            // Salva a lista atualizada de agendamentos no arquivo
            dataService.saveAgendamentos(agendamentos);

            System.out.println("Novo agendamento criado e salvo: " + novoAgendamento.getCliente().getNome());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Seu agendamento foi solicitado com sucesso! Aguarde a confirmação do administrador.");
            alert.showAndWait();

            // TODO: Voltar para a tela inicial do cliente

        } else {
            new Alert(Alert.AlertType.WARNING, "Por favor, selecione um horário.").showAndWait();
        }
    }
}