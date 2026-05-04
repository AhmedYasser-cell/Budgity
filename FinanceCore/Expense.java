package FinanceCore;

import java.sql.Date;

public class Expense extends Transaction {
    private String paymentMethod;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Expense(int transactionId, double amount, Date date, String category, String paymentMethod) {
        super(transactionId, amount, date, category);
        this.paymentMethod = paymentMethod;
    }

    void addExpense(double amount, String method) {
        if (validateAmount()) {
            if (method.equalsIgnoreCase("Credit Card") || method.equalsIgnoreCase("Debit Card")
                    || method.equalsIgnoreCase("Cash")) {
                setAmount(amount * 1.02);
            } else {
                setAmount(amount * 1.05);
            }
        } else {
            // Handle invalid amount case
        }
    }
}
