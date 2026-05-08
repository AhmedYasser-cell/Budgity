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

/**
 * Controller for the user registration (signup) view.
 * Handles the creation of new user accounts and navigation back to login.
 */
public class SignupController {
    /**
     * Default constructor for SignupController.
     */
    public SignupController() {}

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

    /**
     * Handles the signup button click. Validates input and registers a new user.
     */
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

    /**
     * Navigates back to the login view.
     */
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

    /**
     * Utility method to show an information alert.
     * @param title the title of the alert
     * @param content the information message content
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        ThemeManager.applyThemeToDialog(alert);
        alert.showAndWait();
    }
}
