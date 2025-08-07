package org.example.projetopoo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.example.projetopoo.data.DataService;
import org.example.projetopoo.model.Usuario;
import java.io.IOException;
import java.util.List;

public class ChangePasswordController {

    private Usuario usuarioLogado;
    private DataService dataService = new DataService();

    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button changePasswordButton;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    @FXML
    private void handleChangePasswordButton() {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Todos os campos devem ser preenchidos.").showAndWait();
            return;
        }

        if (!currentPassword.equals(usuarioLogado.getSenha())) {
            new Alert(Alert.AlertType.ERROR, "A senha atual está incorreta.").showAndWait();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            new Alert(Alert.AlertType.ERROR, "A nova senha e a confirmação não coincidem.").showAndWait();
            return;
        }

        // Alterar a senha do usuário logado
        usuarioLogado.setSenha(newPassword);

        // Salvar a lista de usuários atualizada
        List<Usuario> usuarios = dataService.loadUsuarios();
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(usuarioLogado.getEmail())) {
                u.setSenha(newPassword);
                break;
            }
        }
        dataService.saveUsuarios(usuarios);

        new Alert(Alert.AlertType.INFORMATION, "Senha alterada com sucesso!").showAndWait();
        handleBackButton();
    }

    @FXML
    private void handleBackButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projetopoo/clientDashboard-view.fxml"));
            Parent root = loader.load();

            ClientDashboardController controller = loader.getController();
            controller.setUsuarioLogado(usuarioLogado);

            Stage stage = (Stage) changePasswordButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Painel do Cliente");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}