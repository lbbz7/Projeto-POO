package org.example.projetopoo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.projetopoo.data.DataService;
import org.example.projetopoo.model.TipoUsuario;
import org.example.projetopoo.model.Usuario;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class LoginController {

    private DataService dataService = new DataService();

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    @FXML
    public void handleLoginButtonAction() {
        String email = emailField.getText();
        String senha = senhaField.getText();

        List<Usuario> usuarios = dataService.loadUsuarios();

        Optional<Usuario> usuarioEncontrado = usuarios.stream()
                .filter(u -> u.getEmail().equals(email) && u.getSenha().equals(senha))
                .findFirst();

        if (usuarioEncontrado.isPresent()) {
            Usuario usuarioLogado = usuarioEncontrado.get();
            if (usuarioLogado.getTipo() == TipoUsuario.ADMIN) {
                navigateToAdminDashboard(usuarioLogado);
            } else {
                navigateToClientDashboard(usuarioLogado);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "E-mail ou senha incorretos.").showAndWait();
        }
    }

    private void navigateToAdminDashboard(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projetopoo/adminDashboard-view.fxml"));
            Parent root = loader.load();
            AdminDashboardController controller = loader.getController();
            controller.setUsuarioLogado(usuario);
            Stage stage = (Stage) emailField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Painel do Administrador");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToClientDashboard(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projetopoo/clientDashboard-view.fxml"));
            Parent root = loader.load();
            ClientDashboardController controller = loader.getController();
            controller.setUsuarioLogado(usuario);
            Stage stage = (Stage) emailField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Painel do Cliente");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRegisterButtonAction() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/projetopoo/register-view.fxml"));
            Stage stage = (Stage) emailField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Cadastro");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}