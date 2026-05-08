package src.FinanceCore;

//import java.sql.Date;
import java.time.LocalDate;

/**
 * Class representing an expense transaction.
 * Extends the basic Transaction to include payment method details.
 */
public class Expense extends Transaction {
    private String paymentMethod;

    /**
     * Gets the payment method used for the expense.
     * @return the payment method (e.g., "Cash", "Credit Card")
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets the payment method used for the expense.
     * @param paymentMethod the payment method to set
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Constructs a new Expense transaction.
     * @param transactionId unique identifier
     * @param amount monetary value
     * @param date date of transaction
     * @param category expense category
     * @param paymentMethod method used for payment
     */
    public Expense(int transactionId, double amount, LocalDate date, Category category, String paymentMethod) {
        super(transactionId, amount, date, category);
        this.paymentMethod = paymentMethod;
    }

    /**
     * Adds an expense and calculates the final amount based on the payment method's processing fee.
     * @param amount the base amount of the expense
     * @param method the payment method used
     */
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
