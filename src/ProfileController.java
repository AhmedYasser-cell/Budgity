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
import java.util.Optional;

import src.UserManagement.*;
import src.fxmlContrroller;
import src.Infrastructure.DatabaseManager;
import src.Infrastructure.ThemeManager;

import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

public class ProfileController implements Initializable {

    @FXML
    private Button mainButton;

    @FXML
    private Button editButton;

    @FXML
    private Button transactionButton;

    @FXML
    private Button goalButton;

    @FXML
    private Button changePasswordButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Slider themeSlider;

    @FXML
    private void goToMain() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        ThemeManager.applyTheme(root);
        Stage stage = (Stage) mainButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void goToTransaction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("transaction.fxml"));
        ThemeManager.applyTheme(root);
        Stage stage = (Stage) transactionButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void goToGoal() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("setFinancialGoal.fxml"));
        ThemeManager.applyTheme(root);
        Stage stage = (Stage) goalButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void handleEditProfile() {
        if (fxmlContrroller.currentUser == null)
            return;

        // Create the custom dialog.
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Profile");
        dialog.setHeaderText("Update your profile information.\nLeave unchanged to keep current values.");
        ThemeManager.applyThemeToDialog(dialog);

        // Set the button types.
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the name and email labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setText(fxmlContrroller.currentUser.getUsername());
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setText(fxmlContrroller.currentUser.getEmail());

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == saveButtonType) {
            String newName = nameField.getText().trim();
            String newEmail = emailField.getText().trim();

            if (newName.isEmpty())
                newName = fxmlContrroller.currentUser.getUsername();
            if (newEmail.isEmpty())
                newEmail = fxmlContrroller.currentUser.getEmail();

            fxmlContrroller.currentUser.updateProfile(newName, newEmail);
            usernameLabel.setText(newName);
            emailLabel.setText(newEmail);
        }
    }

    @FXML
    private void handleChangePassword() {
        if (fxmlContrroller.currentUser == null)
            return;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Password");
        dialog.setHeaderText("Update your password");
        dialog.setContentText("Please enter your new password:");
        ThemeManager.applyThemeToDialog(dialog);

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(password -> {
            if (!password.trim().isEmpty()) {
                fxmlContrroller.currentUser.setPassword(password);
                DatabaseManager.getInstance().saveData(fxmlContrroller.currentUser);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Password updated successfully!");
                alert.showAndWait();
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = null;
        if (fxmlContrroller.currentUser != null) {
            user = DatabaseManager.getInstance().loadData(fxmlContrroller.currentUser.getEmail());
        }

        if (user != null) {
            fxmlContrroller.currentUser = user;
            setUserData(user.getUsername(), user.getEmail());
        } else if (fxmlContrroller.currentUser != null) {
            setUserData(fxmlContrroller.currentUser.getUsername(), fxmlContrroller.currentUser.getEmail());
        } else {
            setUserData("Guest", "Not Available");
        }

        // Initialize theme slider
        themeSlider.setValue(ThemeManager.isDarkMode() ? 1 : 0);
        themeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            boolean darkMode = newValue.doubleValue() > 0.5;
            ThemeManager.setDarkMode(darkMode);
            ThemeManager.applyTheme(themeSlider.getScene().getRoot());
        });
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
