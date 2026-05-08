package src;

import src.Infrastructure.DatabaseManager;
import src.UserManagement.User;
import src.FinanceCore.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * A separate administrative utility class to manipulate the database directly.
 * Run this class to reset tables, list data, or inject test records.
 */
/**
 * A separate administrative utility class to manipulate the database directly.
 * Run this class to reset tables, list data, or inject test records via a CLI interface.
 */
public class DatabaseAdmin {
    /**
     * Default constructor for DatabaseAdmin.
     */
    public DatabaseAdmin() {}

    private static final String DB_URL = "jdbc:sqlite:budgeting.db";

    /**
     * Main method providing a command-line interface for database administration.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Budgeting System Database Admin Tool ===");

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Initialize/Reset Database (Create Tables)");
            System.out.println("2. List All Users");
            System.out.println("3. List All Transactions");
            System.out.println("4. List All Financial Goals");
            System.out.println("5. Wipe All Data (TRUNCATE all tables)");
            System.out.println("6. Assign all Goals to User ID 1 (Quick Fix)");
            System.out.println("7. Exit");
            System.out.print("Choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    DatabaseManager.getInstance().initializeDatabase();
                    break;
                case "2":
                    listUsers();
                    break;
                case "3":
                    listTransactions();
                    break;
                case "4":
                    listGoals();
                    break;
                case "5":
                    wipeData();
                    break;
                case "6":
                    assignGoalsToUser1();
                    break;
                case "7":
                    System.out.println("Exiting Admin Tool.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    /**
     * Prints all registered users in the database to the console.
     */
    private static void listUsers() {
        String sql = "SELECT * FROM Users";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Users ---");
            System.out.printf("%-5s | %-15s | %-25s | %-15s\n", "ID", "Name", "Email", "Password");
            System.out.println("---------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-5d | %-15s | %-25s | %-15s\n",
                        rs.getInt("userId"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"));
            }
        } catch (SQLException e) {
            System.err.println("Error listing users: " + e.getMessage());
        }
    }

    /**
     * Prints all transactions in the database to the console.
     */
    private static void listTransactions() {
        String sql = "SELECT * FROM Transactions";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Transactions ---");
            System.out.printf("%-5s | %-10s | %-10s | %-10s | %-15s | %-10s\n",
                    "ID", "UserID", "Amount", "Type", "Category", "Date");
            System.out.println("---------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-5d | %-10d | %-10.2f | %-10s | %-15s | %-10s\n",
                        rs.getInt("transactionId"),
                        rs.getInt("userId"),
                        rs.getDouble("amount"),
                        rs.getString("type"),
                        rs.getString("category"),
                        rs.getString("date"));
            }
        } catch (SQLException e) {
            System.err.println("Error listing transactions: " + e.getMessage());
        }
    }

    /**
     * Prints all financial goals in the database to the console.
     */
    private static void listGoals() {
        String sql = "SELECT * FROM FinancialGoals";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Financial Goals ---");
            System.out.printf("%-5s | %-10s | %-20s | %-10s | %-10s\n",
                    "ID", "UserID", "Name", "Target", "Current");
            System.out.println("----------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-5d | %-10d | %-20s | %-10.2f | %-10.2f\n",
                        rs.getInt("goalId"),
                        rs.getInt("userId"),
                        rs.getString("name"),
                        rs.getDouble("targetAmount"),
                        rs.getDouble("currentAmount"));
            }
        } catch (SQLException e) {
            System.err.println("Error listing goals: " + e.getMessage());
        }
    }

    /**
     * Deletes all data from the Transactions, Budgets, FinancialGoals, and Users tables.
     * Also resets SQLite autoincrement sequences.
     */
    private static void wipeData() {
        String[] tables = { "Transactions", "Budgets", "FinancialGoals", "Users" };
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement()) {

            for (String table : tables) {
                stmt.execute("DELETE FROM " + table);
                // Reset autoincrement
                stmt.execute("DELETE FROM sqlite_sequence WHERE name='" + table + "'");
            }
            System.out.println("All data wiped and IDs reset.");
        } catch (SQLException e) {
            System.err.println("Error wiping data: " + e.getMessage());
        }
    }

    /**
     * Utility method to reassign all orphan goals to User ID 1.
     */
    private static void assignGoalsToUser1() {
        String sql = "UPDATE FinancialGoals SET userId = 1";

        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement()) {

            int rows = stmt.executeUpdate(sql);
            System.out.println("Successfully updated " + rows + " goal(s) to belong to User ID 1.");

        } catch (SQLException e) {
            System.err.println("Error updating goals: " + e.getMessage());
        }
    }
}
