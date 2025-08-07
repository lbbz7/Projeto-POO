package org.example.projetopoo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.projetopoo.data.DataService;
import org.example.projetopoo.model.Servico;
import org.example.projetopoo.model.Usuario;

import java.io.IOException;
import java.util.List;

public class AdminServicesController {

    private DataService dataService = new DataService();
    private List<Servico> servicos;
    private Usuario usuarioLogado;

    @FXML
    private TableView<Servico> servicesTable;

    @FXML
    private TableColumn<Servico, String> nomeColumn;

    @FXML
    private TableColumn<Servico, String> descricaoColumn;

    @FXML
    private TableColumn<Servico, Double> precoColumn;

    @FXML
    private TextField nomeField;

    @FXML
    private TextArea descricaoField;

    @FXML
    private TextField precoField;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    public void initialize() {
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        descricaoColumn.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        precoColumn.setCellValueFactory(new PropertyValueFactory<>("preco"));

        loadServices();

        servicesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                nomeField.setText(newVal.getNome());
                descricaoField.setText(newVal.getDescricao());
                precoField.setText(String.valueOf(newVal.getPreco()));
            } else {
                clearFields();
            }
        });
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    private void loadServices() {
        this.servicos = dataService.loadServicos();
        servicesTable.getItems().setAll(this.servicos);
    }

    @FXML
    public void handleAddService() {
        if (validateInput()) {
            String nome = nomeField.getText();
            String descricao = descricaoField.getText();
            double preco = Double.parseDouble(precoField.getText());

            // Gerar um novo ID simples (o maior ID atual + 1)
            int newId = servicos.isEmpty() ? 1 : servicos.get(servicos.size() - 1).getId() + 1;

            Servico newServico = new Servico(newId, nome, descricao, preco);
            servicos.add(newServico);

            dataService.saveServicos(servicos);
            loadServices();
            clearFields();
            new Alert(Alert.AlertType.INFORMATION, "Serviço adicionado com sucesso!").showAndWait();
        }
    }

    @FXML
    public void handleUpdateService() {
        Servico selectedService = servicesTable.getSelectionModel().getSelectedItem();
        if (selectedService != null && validateInput()) {
            selectedService.setNome(nomeField.getText());
            selectedService.setDescricao(descricaoField.getText());
            selectedService.setPreco(Double.parseDouble(precoField.getText()));

            dataService.saveServicos(servicos);
            loadServices();
            clearFields();
            new Alert(Alert.AlertType.INFORMATION, "Serviço atualizado com sucesso!").showAndWait();
        } else {
            new Alert(Alert.AlertType.WARNING, "Por favor, selecione um serviço para atualizar.").showAndWait();
        }
    }

    @FXML
    public void handleDeleteService() {
        Servico selectedService = servicesTable.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            servicos.remove(selectedService);

            dataService.saveServicos(servicos);
            loadServices();
            clearFields();
            new Alert(Alert.AlertType.INFORMATION, "Serviço removido com sucesso!").showAndWait();
        } else {
            new Alert(Alert.AlertType.WARNING, "Por favor, selecione um serviço para remover.").showAndWait();
        }
    }

    @FXML
    public void handleBackButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projetopoo/adminDashboard-view.fxml"));
            Parent root = loader.load();

            AdminDashboardController controller = loader.getController();
            controller.setUsuarioLogado(usuarioLogado);

            Stage stage = (Stage) nomeField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Painel do Administrador");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        nomeField.clear();
        descricaoField.clear();
        precoField.clear();
    }

    private boolean validateInput() {
        String nome = nomeField.getText();
        String descricao = descricaoField.getText();
        String precoStr = precoField.getText();

        if (nome.isEmpty() || descricao.isEmpty() || precoStr.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Todos os campos devem ser preenchidos.").showAndWait();
            return false;
        }

        try {
            Double.parseDouble(precoStr);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "O preço deve ser um número válido.").showAndWait();
            return false;
        }

        return true;
    }
}