package src.UserManagement;

import src.FinanceCore.Transaction;
import src.FinanceCore.Budget;
import src.FinanceCore.FinancialGoal;
import java.util.List;
import java.util.ArrayList;

/**
 * Class representing a user of the budgeting system.
 * Contains user profile information, transactions, budgets, and financial goals.
 * Implements IUser for authentication operations.
 */
public class User implements IUser {
    private int userId;
    private String username;
    private String password;
    private String email;
    private List<Transaction> transactions;
    private List<Budget> budgets;
    private List<FinancialGoal> goals;

    /**
     * Constructs a new User.
     * @param userId unique identifier
     * @param username user's display name
     * @param password user's secret password
     * @param email user's unique email address
     */
    public User(int userId, String username, String password, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.transactions = new ArrayList<>();
        this.budgets = new ArrayList<>();
        this.goals = new ArrayList<>();
    }

    /**
     * Gets the user's unique identifier.
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user's unique identifier.
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the user's display name.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's display name.
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the user's password.
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's unique email address.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's unique email address.
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the list of all transactions associated with the user.
     * @return list of transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Retrieves a specific transaction by its ID.
     * @param transactionId the ID of the transaction to find
     * @return the transaction if found, null otherwise
     */
    public Transaction getTransaction(int transactionId) {
        for (Transaction t : transactions) {
            if (t.getTransactionId() == transactionId) {
                return t;
            }
        }
        return null;
    }

    /**
     * Adds a new transaction to the user's record.
     * @param transaction the transaction to add
     */
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    /**
     * Gets the list of all budgets set by the user.
     * @return list of budgets
     */
    public List<Budget> getBudgets() {
        return budgets;
    }

    /**
     * Adds a new budget to the user's record.
     * @param budget the budget to add
     */
    public void addBudget(Budget budget) {
        this.budgets.add(budget);
    }

    /**
     * Gets the list of all financial goals set by the user.
     * @return list of financial goals
     */
    public List<FinancialGoal> getGoals() {
        return goals;
    }

    /**
     * Adds a new financial goal to the user's record.
     * @param goal the goal to add
     */
    public void addGoal(FinancialGoal goal) {
        this.goals.add(goal);
    }

    /**
     * Checks if a transaction with the given ID exists for this user.
     * @param transactionId the ID to check
     * @return true if it exists, false otherwise
     */
    public boolean hasTransaction(int transactionId) {
        for (Transaction t : transactions) {
            if (t.getTransactionId() == transactionId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a transaction from the user's record by its ID.
     * @param transactionId the ID of the transaction to remove
     */
    public void removeTransaction(int transactionId) {
        transactions.removeIf(t -> t.getTransactionId() == transactionId);
    }

    /**
     * Registers the user by saving their profile to the database.
     * @param name display name
     * @param email unique email
     * @param pass secret password
     * @return true if registration was successful
     */
    public boolean register(String name, String email, String pass) {
        this.username = name;
        this.email = email;
        this.password = pass;
        return src.Infrastructure.DatabaseManager.getInstance().saveData(this);
    }

    /**
     * Logs the user in by verifying their credentials.
     * @param email the user's email
     * @param pass the user's password
     * @return true if credentials are valid
     */
    @Override
    public boolean login(String email, String pass) {
        return AuthenticationManager.verifyCredentials(email, pass);
    }

    /**
     * Updates the user's profile information and saves changes to the database.
     * @param name new display name (null to keep current)
     * @param email new email address (null to keep current)
     */
    public void updateProfile(String name, String email) {
        if (name != null) {
            this.username = name;
        }
        if (email != null) {
            this.email = email;
        }
        src.Infrastructure.DatabaseManager.getInstance().saveData(this);
    }
}
