package org.example.projetopoo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.example.projetopoo.model.Servico;
import org.example.projetopoo.model.Usuario;
import java.io.IOException;

public class ClientDashboardController {
    private Usuario usuarioLogado;

    @FXML
    private Label welcomeLabel;

    @FXML
    private ListView<Servico> servicosListView;

    @FXML
    private Button agendarButton;

    @FXML
    public void initialize() {
        loadServicos();

        agendarButton.setDisable(true);
        servicosListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            agendarButton.setDisable(newVal == null);
        });
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        if (usuario != null) {
            welcomeLabel.setText("Olá, " + usuario.getNome() + "!");
        }
    }

    private void loadServicos() {
        ObservableList<Servico> servicos = FXCollections.observableArrayList(
                new Servico(1, "Instalação de Ar-Condicionado", "Instalação profissional para todos os tipos de aparelhos.", 350.00),
                new Servico(2, "Manutenção Preventiva", "Limpeza e verificação completa para o bom funcionamento do seu equipamento.", 150.00),
                new Servico(3, "Conserto de Vazamentos", "Identificação e reparo de vazamentos em sistemas de refrigeração.", 200.00)
        );
        servicosListView.setItems(servicos);
    }

    @FXML
    public void handleAgendarButtonAction() {
        Servico servicoSelecionado = servicosListView.getSelectionModel().getSelectedItem();
        if (servicoSelecionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projetopoo/views/agendamento-view.fxml"));
                Parent root = loader.load();

                AgendamentoController agendamentoController = loader.getController();
                agendamentoController.initData(usuarioLogado, servicoSelecionado);

                Stage stage = (Stage) agendarButton.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Agendar Serviço: " + servicoSelecionado.getNome());
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Por favor, selecione um serviço para agendar.").showAndWait();
        }
    }

    @FXML
    public void handleMeusAgendamentosButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projetopoo/views/clientAppointments-view.fxml"));
            Parent root = loader.load();

            ClientAppointmentsController controller = loader.getController();
            controller.initData(usuarioLogado);

            Stage stage = (Stage) agendarButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Meus Agendamentos");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
