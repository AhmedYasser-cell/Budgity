package src.FinanceCore;

//import java.sql.Date;
import java.time.LocalDate;

public abstract class Transaction {
    private int transactionId;
    private double amount;
    private Category category;
    private LocalDate date;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Transaction(int transactionId, double amount, LocalDate date, Category category) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    public boolean validateAmount() {
        return getAmount() > 0;
    }

}
