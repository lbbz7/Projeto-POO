package org.example.projetopoo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.projetopoo.model.Usuario;
import org.example.projetopoo.model.TipoUsuario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterController {

    // Lista estática para simular um "banco de dados" de usuários.
    private static List<Usuario> usuarios = new ArrayList<>();

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
            new Alert(Alert.AlertType.ERROR, "Todos os campos devem ser preenchidos.").showAndWait();
            return;
        }

        // Simula o cadastro
        Usuario novoUsuario = new Usuario(nome, email, senha, TipoUsuario.CLIENTE);
        usuarios.add(novoUsuario);

        System.out.println("Novo usuário cadastrado: " + novoUsuario.getNome());
        new Alert(Alert.AlertType.INFORMATION, "Cadastro realizado com sucesso!").showAndWait();

        handleBackButtonAction(); // Volta para a tela de login
    }

    @FXML
    public void handleBackButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projetopoo/views/login-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) nomeField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para ser usado pelo LoginController, se necessário.
    public static List<Usuario> getUsuarios() {
        return usuarios;
    }
}