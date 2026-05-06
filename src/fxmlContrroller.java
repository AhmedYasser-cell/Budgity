package src;

import src.UserManagement.User;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class fxmlContrroller implements Initializable {

    @FXML
    private TextField UsernameField;
    @FXML
    private PasswordField PasswordField;

    @FXML
    private void handleLogin() {
        String username = UsernameField.getText();
        String password = PasswordField.getText();
        User user = new User(1, username, password, "ee");
        boolean success = user.login(username, password);
        if (success) {
            // label.setText("Login successful!");
            // Navigate to the next scene or perform other actions
        } else {
            // label.setText("Login failed!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
