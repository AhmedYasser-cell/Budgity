package src;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import src.UserManagement.*;
import src.fxmlContrroller;
import src.Infrastructure.DatabaseManager;

public class ProfileController implements Initializable {

    @FXML
    private Button mainButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private void goToMain() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Stage stage = (Stage) mainButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = null;
        if (fxmlContrroller.currentUser != null) {
            user = DatabaseManager.getInstance().loadData(fxmlContrroller.currentUser.getEmail());

            // Note: loadData expects an email, so we pass getEmail() instead of
            // getUsername()
        }

        if (user != null) {
            fxmlContrroller.currentUser = user;
            setUserData(user.getUsername(), user.getEmail());
        } else if (fxmlContrroller.currentUser != null) {
            setUserData(fxmlContrroller.currentUser.getUsername(), fxmlContrroller.currentUser.getEmail());
        } else {
            setUserData("Guest", "Not Available");
        }
    }

    public void setUserData(String username, String email) {
        if (usernameLabel != null) {
            usernameLabel.setText(username);
        }
        if (emailLabel != null) {
            emailLabel.setText(email);
        }
    }
}
