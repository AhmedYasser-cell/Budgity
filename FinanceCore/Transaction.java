package FinanceCore;

import java.sql.Date;

public abstract class Transaction {
    private int transactionId;
    private double amount;
    private Date date;
    private String category;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Transaction(int transactionId, double amount, Date date, String category) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    public boolean validateAmount() {
        return getAmount() > 0;
    }

}
