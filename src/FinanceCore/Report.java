package src.FinanceCore;

public class Report {

    private int month;
    private double totalIncome;
    private double totalExpense;
    private double netBalance;

    // Constructor
    public Report(int month, double totalIncome, double totalExpense, double netBalance) {
        this.month = month;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.netBalance = netBalance;
    }

    // Getters
    public int getMonthValue() {
        return month;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public double getNetBalance() {
        return netBalance;
    }

    // Setters
    public void setMonth(int month) {
        this.month = month;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public void setNetBalance(double netBalance) {
        this.netBalance = netBalance;
    }
}