package src;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import src.FinanceCore.FinancialGoal;
import src.Infrastructure.DatabaseManager;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class setFinancialGoalController {

    @FXML
    private TextField goalNameField;

    @FXML
    private TextField targetAmountField;

    @FXML
    private TextField currentAmountField;

    @FXML
    private TextField monthlyIncomeField;

    @FXML
    private DatePicker targetDatePicker;

    @FXML
    private Button backButton;

    @FXML
    private Button addGoalButton;

    @FXML
    private Button editGoalButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<FinancialGoal, String> goalNameColumn;

    @FXML
    private TableColumn<FinancialGoal, Double> targetColumn;

    @FXML
    private TableColumn<FinancialGoal, Double> currentColumn;

    @FXML
    private TableColumn<FinancialGoal, Double> incomeColumn;

    @FXML
    private TableColumn<FinancialGoal, String> progressColumn;

    @FXML
    private TableView<FinancialGoal> goalsTable;

    private ObservableList<FinancialGoal> goalsList = FXCollections.observableArrayList();

    private final String DB_URL = "jdbc:sqlite:budgeting.db";

    @FXML
    public void initialize() {

        goalNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        targetColumn.setCellValueFactory(
                cellData -> new SimpleDoubleProperty(cellData.getValue().getTargetAmount()).asObject());

        currentColumn.setCellValueFactory(
                cellData -> new SimpleDoubleProperty(cellData.getValue().getCurrentAmount()).asObject());

        // monthly income مش موجودة في FinancialGoal حاليا
        incomeColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(0).asObject());

        progressColumn.setCellValueFactory(cellData -> {
            double progress = cellData.getValue().getPercentageAchieved();
            return new SimpleStringProperty(String.format("%.1f%%", progress));
        });

        loadGoalsFromDatabase();

        goalsTable.setItems(goalsList);

        goalsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, selectedGoal) -> {
            if (selectedGoal != null) {
                goalNameField.setText(selectedGoal.getName());
                targetAmountField.setText(String.valueOf(selectedGoal.getTargetAmount()));
                currentAmountField.setText(String.valueOf(selectedGoal.getCurrentAmount()));
            }
        });
    }

    @FXML
    void handleAddGoal(ActionEvent event) {

        try {
            String goalName = goalNameField.getText();
            double targetAmount = Double.parseDouble(targetAmountField.getText());
            double currentAmount = Double.parseDouble(currentAmountField.getText());

            FinancialGoal goal = new FinancialGoal(0, goalName, targetAmount, currentAmount);

            saveGoalToDatabase(goal);

            goalsList.add(goal);

            clearFields();

            showAlert("Success", "Goal added successfully!");

        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid numbers.");
        }
    }

    @FXML
    void handleDeleteGoal(ActionEvent event) {

        FinancialGoal selectedGoal = goalsTable.getSelectionModel().getSelectedItem();

        if (selectedGoal == null) {
            showAlert("Error", "Please select a goal first.");
            return;
        }

        String sql = "DELETE FROM FinancialGoals WHERE goalId = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, selectedGoal.getGoalId());
            pstmt.executeUpdate();

            // 4.TableView (UI)
            goalsList.remove(selectedGoal);

            showAlert("Success", "Goal deleted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to delete goal.");
        }
    }

    @FXML
    private void handleBack() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));

        Stage stage = (Stage) backButton.getScene().getWindow();

        stage.setScene(new Scene(root));
    }

    @FXML
    void handleEditGoal(ActionEvent event) {

        FinancialGoal selectedGoal = goalsTable.getSelectionModel().getSelectedItem();

        if (selectedGoal == null) {
            showAlert("Error", "Please select a goal first.");
            return;
        }

        try {
            selectedGoal.setName(goalNameField.getText());
            selectedGoal.setTargetAmount(Double.parseDouble(targetAmountField.getText()));
            selectedGoal.setCurrentAmount(Double.parseDouble(currentAmountField.getText()));

            updateGoalInDatabase(selectedGoal);

            goalsTable.refresh();

            clearFields();

            showAlert("Success", "Goal updated successfully!");

        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid numbers.");
        }
    }

    private void saveGoalToDatabase(FinancialGoal goal) {

        String sql = "INSERT INTO FinancialGoals(name, targetAmount, currentAmount) VALUES(?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, goal.getName());
            pstmt.setDouble(2, goal.getTargetAmount());
            pstmt.setDouble(3, goal.getCurrentAmount());

            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();

            if (generatedKeys.next()) {
                goal.setGoalId(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateGoalInDatabase(FinancialGoal goal) {

        String sql = "UPDATE FinancialGoals SET name = ?, targetAmount = ?, currentAmount = ? WHERE goalId = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, goal.getName());
            pstmt.setDouble(2, goal.getTargetAmount());
            pstmt.setDouble(3, goal.getCurrentAmount());
            pstmt.setInt(4, goal.getGoalId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadGoalsFromDatabase() {

        goalsList.clear();

        String sql = "SELECT * FROM FinancialGoals";

        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                FinancialGoal goal = new FinancialGoal(
                        rs.getInt("goalId"),
                        rs.getString("name"),
                        rs.getDouble("targetAmount"),
                        rs.getDouble("currentAmount"));

                goalsList.add(goal);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        goalNameField.clear();
        targetAmountField.clear();
        currentAmountField.clear();
        monthlyIncomeField.clear();
        targetDatePicker.setValue(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}