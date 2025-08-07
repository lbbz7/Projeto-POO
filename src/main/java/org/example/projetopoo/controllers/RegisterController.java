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

    @FXML
    private TextField nomeField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    private DataService dataService = new DataService();
    private List<Usuario> usuarios;

    @FXML
    public void initialize() {
        // Carrega os usuários salvos do arquivo ao inicializar o controlador
        this.usuarios = dataService.loadUsuarios();
    }

    @FXML
    public void handleRegisterButtonAction() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Todos os campos devem ser preenchidos.").showAndWait();
            return;
        }

        // Cria o novo usuário e o adiciona à lista
        Usuario novoUsuario = new Usuario(nome, email, senha, TipoUsuario.CLIENTE);
        this.usuarios.add(novoUsuario);

        // Salva a lista atualizada de usuários no arquivo
        dataService.saveUsuarios(this.usuarios);

        System.out.println("Novo usuário cadastrado e salvo: " + novoUsuario.getNome());
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

    // Este método agora é público e não estático, pois a lista de usuários
    // pertence à instância do controlador. Ele é usado pelo LoginController.
    public List<Usuario> getUsuarios() {
        return this.usuarios;
    }
}