package src.FinanceCore;

//import java.sql.Date;
import java.time.LocalDate;

/**
 * Class representing an income transaction.
 * Extends the basic Transaction to include source information.
 */
public class Income extends Transaction {
    private String source;

    /**
     * Gets the source of the income.
     * @return the income source (e.g., "Salary", "Freelance")
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the source of the income.
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Constructs a new Income transaction.
     * @param transactionId unique identifier
     * @param amount monetary value
     * @param date date of transaction
     * @param category income category
     * @param source source of the income
     */
    public Income(int transactionId, double amount, LocalDate date, Category category, String source) {
        super(transactionId, amount, date, category);
        this.source = source;
    }

    /**
     * Adds the income to the system.
     */
    void addIncome() {
        if (validateAmount()) {
            // Logic to add income to the system
        } else {
            // Handle invalid amount case
        }
    }
}
