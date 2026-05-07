package src;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import src.Infrastructure.ThemeManager;

import src.FinanceCore.Category;
import src.FinanceCore.Expense;
import src.FinanceCore.Income;
import src.FinanceCore.Transaction;

public class TransactionController {

    @FXML private TableView<Transaction> transactionsTable;
    @FXML private TableColumn<Transaction, LocalDate> dateColumn;
    @FXML private TableColumn<Transaction, String> typeColumn;
    @FXML private TableColumn<Transaction, Category> categoryColumn;
    @FXML private TableColumn<Transaction, Double> amountColumn;

    @FXML private Button backButton;
    @FXML private ComboBox<String> typeFilter;
    @FXML private TextField searchField;

    private ObservableList<Transaction> allTransactions =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        typeColumn.setCellValueFactory(cellData -> {
            Transaction transaction = cellData.getValue();

            if (transaction instanceof Income) {
                return new SimpleStringProperty("Income");
            } else if (transaction instanceof Expense) {
                return new SimpleStringProperty("Expense");
            } else {
                return new SimpleStringProperty("Unknown");
            }
        });

        typeFilter.setItems(
                FXCollections.observableArrayList("All", "Expense", "Income")
        );

        typeFilter.setValue("All");

        loadTransactions();

        typeFilter.setOnAction(event -> applyFilters());

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            applyFilters();
        });
    }

    private void loadTransactions() {

        allTransactions.clear();

        if (fxmlContrroller.currentUser == null) {
            return;
        }

        try {
            int userId = fxmlContrroller.currentUser.getUserId();

            Connection conn =
                    DriverManager.getConnection("jdbc:sqlite:budgeting.db");

            String sql =
                    "SELECT * FROM Transactions WHERE userId = ?";

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setInt(1, userId);

            ResultSet rs =
                    stmt.executeQuery();

            while (rs.next()) {

                int id =
                        rs.getInt("transactionId");

                double amount =
                        rs.getDouble("amount");

                LocalDate date =
                        LocalDate.parse(rs.getString("date"));

                Category category;
                try {
                    String catStr = rs.getString("category");
                    category = (catStr != null) ? Category.valueOf(catStr) : Category.OTHER;
                } catch (Exception e) {
                    category = Category.OTHER;
                }

                String type = rs.getString("type");
                if (type != null && type.equalsIgnoreCase("Income")) {
                    allTransactions.add(
                            new Income(id, amount, date, category, "Database")
                    );
                } else {
                    allTransactions.add(
                            new Expense(id, amount, date, category, "Cash")
                    );
                }
            }

            rs.close();
            stmt.close();
            conn.close();

            applyFilters();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void applyFilters() {

        String searchText = "";

        if (searchField != null && searchField.getText() != null) {
            searchText = searchField.getText().toLowerCase();
        }

        String selectedType = typeFilter.getValue();

        ObservableList<Transaction> filtered =
                FXCollections.observableArrayList();

        for (Transaction transaction : allTransactions) {

            String type;

            if (transaction instanceof Income) {
                type = "Income";
            } else {
                type = "Expense";
            }

            String category =
                    transaction.getCategory().toString().toLowerCase();

            String amount =
                    String.valueOf(transaction.getAmount()).toLowerCase();

            String date =
                    transaction.getDate().toString().toLowerCase();

            boolean matchesSearch =
                    searchText.isEmpty()
                    || type.toLowerCase().contains(searchText)
                    || category.contains(searchText)
                    || amount.contains(searchText)
                    || date.contains(searchText);

            boolean matchesType =
                    selectedType == null
                    || selectedType.equals("All")
                    || selectedType.equals(type);

            if (matchesSearch && matchesType) {
                filtered.add(transaction);
            }
        }

        transactionsTable.setItems(filtered);
    }

    @FXML
    private void goBack() throws IOException {

        Parent root =
                FXMLLoader.load(getClass().getResource("home.fxml"));
        ThemeManager.applyTheme(root);

        Stage stage =
                (Stage) backButton.getScene().getWindow();

        stage.setScene(new Scene(root));
    }
}