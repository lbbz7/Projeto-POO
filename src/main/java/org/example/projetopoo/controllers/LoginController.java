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
import java.io.IOException;
import java.util.List;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    @FXML
    public void handleLoginButtonAction() {
        String email = emailField.getText();
        String senha = senhaField.getText();

        // Pega a lista de usuários cadastrados
        List<Usuario> usuariosCadastrados = RegisterController.getUsuarios();

        Usuario usuarioAutenticado = null;
        for (Usuario usuario : usuariosCadastrados) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                usuarioAutenticado = usuario;
                break;
            }
        }

        if (usuarioAutenticado != null) {
            System.out.println("Login de " + usuarioAutenticado.getNome() + " realizado com sucesso!");

            // Carrega a tela do dashboard correspondente
            if (usuarioAutenticado.getTipo().equals(org.example.projetopoo.model.TipoUsuario.ADMIN)) {
                loadNextScene("adminDashboard-view.fxml", "Painel do Administrador", usuarioAutenticado);
            } else {
                loadNextScene("clientDashboard-view.fxml", "Painel do Cliente", usuarioAutenticado);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Login");
            alert.setHeaderText(null);
            alert.setContentText("Email ou senha incorretos. Por favor, tente novamente.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleRegisterButtonAction() {
        loadNextScene("register-view.fxml", "Cadastro de Usuário", null);
    }

    private void loadNextScene(String fxmlFile, String title, Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projetopoo/views/" + fxmlFile));
            Parent root = loader.load();

            // Passa o objeto do usuário para o controlador correto
            if (fxmlFile.equals("clientDashboard-view.fxml") && usuario != null) {
                ClientDashboardController controller = loader.getController();
                controller.setUsuarioLogado(usuario);
            } else if (fxmlFile.equals("adminDashboard-view.fxml") && usuario != null) {
                AdminDashboardController controller = loader.getController();
                controller.setUsuarioLogado(usuario);
            }

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