package src.FinanceCore;

/**
 * Class representing a financial report for a specific month.
 */
public class Report {

    private int month;
    private double totalIncome;
    private double totalExpense;
    private double netBalance;

    /**
     * Constructs a new Report.
     * @param month the month the report covers (1-12)
     * @param totalIncome total income for the month
     * @param totalExpense total expenses for the month
     * @param netBalance net balance (income - expense)
     */
    public Report(int month, double totalIncome, double totalExpense, double netBalance) {
        this.month = month;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.netBalance = netBalance;
    }

    /**
     * Gets the month value of the report.
     * @return the month (1-12)
     */
    public int getMonthValue() {
        return month;
    }

    /**
     * Gets the total income recorded in the report.
     * @return total income
     */
    public double getTotalIncome() {
        return totalIncome;
    }

    /**
     * Gets the total expense recorded in the report.
     * @return total expense
     */
    public double getTotalExpense() {
        return totalExpense;
    }

    /**
     * Gets the net balance recorded in the report.
     * @return net balance
     */
    public double getNetBalance() {
        return netBalance;
    }

    /**
     * Sets the month value for the report.
     * @param month the month to set (1-12)
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Sets the total income value for the report.
     * @param totalIncome total income to set
     */
    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    /**
     * Sets the total expense value for the report.
     * @param totalExpense total expense to set
     */
    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    /**
     * Sets the net balance value for the report.
     * @param netBalance net balance to set
     */
    public void setNetBalance(double netBalance) {
        this.netBalance = netBalance;
    }
}