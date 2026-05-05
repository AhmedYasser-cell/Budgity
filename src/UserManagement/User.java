package src.UserManagement;

import src.Infrastructure.DataStorage;
import src.FinanceCore.Transaction;
import java.util.List;
import java.util.ArrayList;

public class User implements IUser {
    private int userId;
    private String username;
    private String password;
    private String email;
    private List<Transaction> transactions;

    public User(int userId, String username, String password, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.transactions = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public boolean hasTransaction(int transactionId) {
        for (Transaction t : transactions) {
            if (t.getTransactionId() == transactionId) {
                return true;
            }
        }
        return false;
    }

    public void removeTransaction(int transactionId) {
        transactions.removeIf(t -> t.getTransactionId() == transactionId);
    }

    public boolean register(String name, String email, String pass) {
        this.username = name;
        this.email = email;
        this.password = pass;
        return DataStorage.getInstance().saveData(this);
    }

    @Override
    public boolean login(String email, String pass) {
        return AuthenticationManager.verifyCredentials(email, pass);
    }

    public void updateProfile(String name, String email) {
        this.username = name;
        this.email = email;
        DataStorage.getInstance().saveData(this);
    }
}