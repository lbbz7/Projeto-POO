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

public class RegisterController {

    private DataService dataService = new DataService();

    @FXML
    private TextField nomeField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    @FXML
    public void handleRegisterButtonAction() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Todos os campos são obrigatórios!").showAndWait();
            return;
        }

        List<Usuario> usuarios = dataService.loadUsuarios();

        boolean emailExists = usuarios.stream().anyMatch(u -> u.getEmail().equals(email));
        if (emailExists) {
            new Alert(Alert.AlertType.ERROR, "Este e-mail já está cadastrado!").showAndWait();
            return;
        }

        Usuario novoUsuario = new Usuario(nome, email, senha, TipoUsuario.CLIENTE);
        usuarios.add(novoUsuario);

        dataService.saveUsuarios(usuarios);

        new Alert(Alert.AlertType.INFORMATION, "Cadastro realizado com sucesso!").showAndWait();
        handleBackButtonAction();
    }

    @FXML
    public void handleBackButtonAction() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/projetopoo/login-view.fxml"));
            Stage stage = (Stage) nomeField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}