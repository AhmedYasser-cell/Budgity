package src;

import src.UserManagement.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import src.UserManagement.*;
import src.Infrastructure.DatabaseManager;
import src.Infrastructure.ThemeManager;

/**
 * Controller for the login view of the application.
 * Manages user authentication and navigation to the signup screen or home screen.
 */
public class fxmlContrroller implements Initializable {
    /**
     * Default constructor for fxmlContrroller.
     */
    public fxmlContrroller() {}

    /** The user currently logged into the system. Accessible by other controllers. */
    static User currentUser;
    @FXML
    private TextField UsernameField;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private Button loginButton;

    /**
     * Handles the login button click. Validates credentials and loads user data.
     */
    @FXML
    private void handleLogin() {
        String email = UsernameField.getText();
        String password = PasswordField.getText();
        User user = new User(1, "ee", password, email);
        boolean success = user.login(email, password);
        if (success) {
            // Fetch the full user data from the database so we have the real username
            currentUser = DatabaseManager.getInstance().loadData(email);

            try {
                Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
                ThemeManager.applyTheme(root);
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
                // handle error loading FXML
            }
        } else {
            // label.setText("Login failed!");
        }
    }

    /**
     * Navigates to the signup (registration) view.
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    private void handleSignUpNavigation() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        ThemeManager.applyTheme(root);
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    /**
     * Initializes the controller class.
     * @param location the location used to resolve relative paths for the root object
     * @param resources the resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
