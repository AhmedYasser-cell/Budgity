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

public class fxmlContrroller implements Initializable {

    static User currentUser;
    @FXML
    private TextField UsernameField;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private Button loginButton;

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

    @FXML
    private void handleSignUpNavigation() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
