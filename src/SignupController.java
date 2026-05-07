package src;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import src.UserManagement.User;
import src.Infrastructure.ThemeManager;
import java.io.IOException;

public class SignupController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signupButton;
    @FXML
    private Button backButton;

    @FXML
    private void handleSignup() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill all fields.");
            return;
        }

        // Create a new user and register
        User newUser = new User(0, username, password, email);
        boolean success = newUser.register(username, email, password);

        if (success) {
            showAlert("Success", "Account created successfully!");
            goToLogin();
        } else {
            showAlert("Error", "Registration failed. Email might already be in use.");
        }
    }

    @FXML
    private void goToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
            ThemeManager.applyTheme(root);
            Stage stage = (Stage) signupButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        ThemeManager.applyThemeToDialog(alert);
        alert.showAndWait();
    }
}
