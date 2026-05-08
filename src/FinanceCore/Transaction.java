package src.FinanceCore;

//import java.sql.Date;
import java.time.LocalDate;

/**
 * Abstract class representing a financial transaction.
 * Base class for Income and Expense.
 */
public abstract class Transaction {
    private int transactionId;
    private double amount;
    private Category category;
    private LocalDate date;

    /**
     * Gets the unique identifier for the transaction.
     * @return the transaction ID
     */
    public int getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the unique identifier for the transaction.
     * @param transactionId the transaction ID to set
     */
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Gets the monetary amount of the transaction.
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the monetary amount of the transaction.
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the date the transaction occurred.
     * @return the transaction date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date the transaction occurred.
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets the category assigned to the transaction.
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the category assigned to the transaction.
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Constructs a new Transaction.
     * @param transactionId unique identifier
     * @param amount monetary value
     * @param date date of transaction
     * @param category transaction category
     */
    public Transaction(int transactionId, double amount, LocalDate date, Category category) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    /**
     * Validates that the transaction amount is positive.
     * @return true if amount > 0, false otherwise
     */
    public boolean validateAmount() {
        return getAmount() > 0;
    }

}
