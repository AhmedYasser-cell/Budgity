package src;

import src.UserManagement.User;
import src.FinanceCore.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import src.FinanceCore.Expense;
import src.FinanceCore.Income;
import src.FinanceCore.Category;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import src.Infrastructure.DatabaseManager;

public class homeController implements Initializable {

    @FXML
    private Button logout;

    @FXML
    private Button profileButton;

    @FXML
    private Button expenseButton;
    @FXML
    private Button incomeButton;

    @FXML
    private Button goalButton;

    @FXML
    private Button transactionButton;

    @FXML
    private void goToTransaction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("transaction.fxml"));
        Stage stage = (Stage) transactionButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void goToProfile() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("profile.fxml"));
        Stage stage = (Stage) profileButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void handleLogout() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.setScene(new Scene(root));
        fxmlContrroller.currentUser = null;
    }

    @FXML
    private void goToGoals() throws IOException {

        Parent root = FXMLLoader.load(
                getClass().getResource("setFinancialGoal.fxml"));

        Stage stage = (Stage) goalButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void handleExpenses() {
        if (fxmlContrroller.currentUser == null)
            return;

        // Create the custom dialog.
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Expenses");
        dialog.setHeaderText("Enter your expenses information.");

        // Set the button types.
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the grid and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        // Using a ChoiceBox for the "method" selection (menu-like dropdown)
        ChoiceBox<String> methodChoice = new ChoiceBox<>();
        methodChoice.getItems().addAll("Cash", "Credit Card", "Debit Card", "Bank Transfer");
        methodChoice.setValue("Cash");

        ChoiceBox<String> categoryChoice = new ChoiceBox<>();
        for (Category category : Category.values()) {
            categoryChoice.getItems().add(category.toString());
        }
        categoryChoice.setValue(Category.OTHER.toString());

        grid.add(new Label("Amount:"), 0, 0);
        grid.add(amountField, 1, 0);
        grid.add(new Label("Method:"), 0, 2);
        grid.add(methodChoice, 1, 2);
        grid.add(new Label("Category:"), 0, 3);
        grid.add(categoryChoice, 1, 3);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == saveButtonType) {
            try {
                String amountStr = amountField.getText();
                String method = methodChoice.getValue();

                if (amountStr == null || amountStr.trim().isEmpty()) {
                    showError("Invalid Input", "Please enter an amount.");
                    return;
                }

                double amount = Double.parseDouble(amountStr);

                Expense expense = new Expense(
                        fxmlContrroller.currentUser.getTransactions().size() + 1,
                        amount,
                        LocalDate.now(),
                        Category.valueOf(categoryChoice.getValue()),
                        method);

                fxmlContrroller.currentUser.addTransaction(expense);
                DatabaseManager.getInstance().saveData(fxmlContrroller.currentUser);
                System.out.println("Expense Saved: " + amount + " via " + method);

            } catch (NumberFormatException e) {
                showError("Invalid Amount", "Please enter a valid numeric value for the amount.");
            }
        }
    }

    @FXML
    private void handleIncome() {
        if (fxmlContrroller.currentUser == null)
            return;

        // Create the custom dialog.
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Income");
        dialog.setHeaderText("Enter your income information.");

        // Set the button types.
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the grid and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        // Using a ChoiceBox for the "source" selection
        ChoiceBox<String> sourceChoice = new ChoiceBox<>();
        sourceChoice.getItems().addAll("Salary", "Investment", "Gift", "Other");
        sourceChoice.setValue("Salary");

        grid.add(new Label("Amount:"), 0, 0);
        grid.add(amountField, 1, 0);
        grid.add(new Label("Source:"), 0, 1);
        grid.add(sourceChoice, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == saveButtonType) {
            try {
                String amountStr = amountField.getText();
                String source = sourceChoice.getValue();

                if (amountStr == null || amountStr.trim().isEmpty()) {
                    showError("Invalid Input", "Please enter an amount.");
                    return;
                }

                double amount = Double.parseDouble(amountStr);

                Income income = new Income(
                        fxmlContrroller.currentUser.getTransactions().size() + 1,
                        amount,
                        LocalDate.now(),
                        Category.OTHER,
                        source);

                fxmlContrroller.currentUser.addTransaction(income);
                DatabaseManager.getInstance().saveData(fxmlContrroller.currentUser);
                System.out.println("Income Saved: " + amount + " from " + source);

            } catch (NumberFormatException e) {
                showError("Invalid Amount", "Please enter a valid numeric value for the amount.");
            }
        }
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}