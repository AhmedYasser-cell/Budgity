package src.Infrastructure;

import src.UserManagement.User;
import src.FinanceCore.Transaction;
import src.FinanceCore.Expense;
import src.FinanceCore.Report;
import src.FinanceCore.Income;

/**
 * Controller responsible for managing financial operations like adding records,
 * deleting records, and generating reports for a specific user.
 */
public class FinanceController {

    private final User currentUser;
    private final IPersistence storage;

    /**
     * Constructs a FinanceController.
     * @param user the current user session
     * @param storage the persistence layer for saving and loading data
     */
    public FinanceController(User user, IPersistence storage) {
        this.currentUser = user;
        this.storage = storage;
    }

    /**
     * Adds a transaction record to the current user's profile.
     * Validates the transaction and ensures the user has sufficient balance for expenses.
     * @param t the transaction to add
     * @throws IllegalArgumentException if the transaction is null, has an invalid amount, or causes a negative balance
     */
    public void addRecord(Transaction t) {
        if (t == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        if (!t.validateAmount()) {
            throw new IllegalArgumentException("Invalid transaction amount");
        }

        currentUser.addTransaction(t);

        if (t instanceof Expense && !validateBalance()) {
            currentUser.removeTransaction(t.getTransactionId());
            throw new IllegalArgumentException("Insufficient balance");
        }

        storage.saveData(currentUser);
    }

    /**
     * Deletes a transaction record from the current user's profile.
     * @param transactionID the ID of the transaction to delete
     * @throws IllegalArgumentException if the transaction ID is invalid or does not exist
     */
    public void deleteRecord(int transactionID) {
        if (transactionID <= 0) {
            throw new IllegalArgumentException("Invalid transaction ID");
        }
        if (!currentUser.hasTransaction(transactionID)) {
            throw new IllegalArgumentException("Transaction ID does not exist");
        }

        currentUser.removeTransaction(transactionID);
        storage.saveData(currentUser);

    }

    /**
     * Generates a monthly summary report for the current user.
     * @param month the month for which to generate the report (1-12)
     * @return a Report object containing total income, total expense, and net balance
     * @throws IllegalArgumentException if the month is invalid
     * @throws IllegalStateException if there are no transactions available for the summary
     */
    public Report getMonthlySummary(int month) {

        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month");
        }
        if (currentUser.getTransactions().isEmpty()) {
            throw new IllegalStateException("No transactions available for summary");
        }

        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction t : currentUser.getTransactions()) {
            if (t.getDate().getMonthValue() == month) {
                if (t instanceof Income) {
                    totalIncome += t.getAmount();
                }

                else if (t instanceof Expense) {
                    totalExpense += t.getAmount();
                }
            }
        }

        double netBalance = totalIncome - totalExpense;
        Report report = new Report(month, totalIncome, totalExpense, netBalance);
        return report;
    }

    /**
     * Validates if the user's total balance is non-negative.
     * @return true if balance >= 0, false otherwise
     */
    public boolean validateBalance() {
        double totalIncome = 0;
        double totalExpense = 0;
        for (Transaction t : currentUser.getTransactions()) {
            if (t instanceof Income) {
                totalIncome += t.getAmount();
            }

            else if (t instanceof Expense) {
                totalExpense += t.getAmount();
            }
        }

        double netBalance = totalIncome - totalExpense;

        return netBalance >= 0;
    }
}