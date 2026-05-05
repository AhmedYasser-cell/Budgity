package src.FinanceCore;

//import java.sql.Date;
import java.time.LocalDate;

public class Income extends Transaction {
    private String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Income(int transactionId, double amount, LocalDate date, String category, String source) {
        super(transactionId, amount, date, category);
        this.source = source;
    }

    void addIncome() {
        if (validateAmount()) {
            // Logic to add income to the system
        } else {
            // Handle invalid amount case
        }
    }
}
