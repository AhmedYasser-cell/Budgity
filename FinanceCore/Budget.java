package FinanceCore;

public class Budget {
    private int budgetId;
    private double limitAmount;
    private double spentAmount;
    private int alertThreshold;

    public Budget(int budgetId, double limitAmount, double spentAmount, int alertThreshold) {
        this.budgetId = budgetId;
        this.limitAmount = limitAmount;
        this.spentAmount = spentAmount;
        this.alertThreshold = alertThreshold;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public double getLimitAmount() {
        return limitAmount;
    }

    public double getSpentAmount() {
        return spentAmount;
    }

    public int getAlertThreshold() {
        return alertThreshold;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public void setLimitAmount(double limitAmount) {
        this.limitAmount = limitAmount;
    }

    public void setSpentAmount(double spentAmount) {
        this.spentAmount = spentAmount;
    }

    public void setAlertThreshold(int alertThreshold) {
        this.alertThreshold = alertThreshold;
    }

    public double calculateRemaining() {
        return getLimitAmount() - getSpentAmount();
    }

    public String checkLimitStatus() {
        double remaining = calculateRemaining();
        if (remaining <= 0) {
            return "Budget exceeded";
        } else if (remaining <= getLimitAmount() * getAlertThreshold() / 100) {
            return "Approaching budget limit";
        } else {
            return "Within budget";
        }
    }
}
