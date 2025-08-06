package org.example.projetopoo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    @FXML
    public void handleLoginButtonAction() {
        String email = emailField.getText();
        String senha = senhaField.getText();

        // Lógica de login simulada
        if ("admin@empresa.com".equals(email) && "12345".equals(senha)) {
            System.out.println("Login do Administrador realizado com sucesso!");
            loadNextScene("AdminDashboard.fxml", "Painel do Administrador");
        } else if ("cliente@email.com".equals(email) && "123".equals(senha)) {
            System.out.println("Login do Cliente realizado com sucesso!");
            loadNextScene("ClientDashboard.fxml", "Painel do Cliente");
        } else {
            // Exibe um alerta de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Login");
            alert.setHeaderText(null);
            alert.setContentText("Email ou senha incorretos. Por favor, tente novamente.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleRegisterButtonAction() {
        loadNextScene("Register.fxml", "Cadastro de Usuário");
    }

    private void loadNextScene(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projetopoo/views/" + fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) emailField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela: " + fxmlFile);
        }
    }
}