package src.FinanceCore;

/**
 * Class representing a budget with a limit, spent amount, and alert threshold.
 */
public class Budget {
    private int budgetId;
    private double limitAmount;
    private double spentAmount;
    private int alertThreshold;

    /**
     * Constructs a new Budget.
     * @param budgetId unique identifier
     * @param limitAmount maximum allowed spending
     * @param spentAmount amount already spent
     * @param alertThreshold percentage of limit at which to trigger an alert
     */
    public Budget(int budgetId, double limitAmount, double spentAmount, int alertThreshold) {
        this.budgetId = budgetId;
        this.limitAmount = limitAmount;
        this.spentAmount = spentAmount;
        this.alertThreshold = alertThreshold;
    }

    /**
     * Gets the budget identifier.
     * @return the budget ID
     */
    public int getBudgetId() {
        return budgetId;
    }

    /**
     * Gets the maximum spending limit for this budget.
     * @return the limit amount
     */
    public double getLimitAmount() {
        return limitAmount;
    }

    /**
     * Gets the total amount spent under this budget.
     * @return the spent amount
     */
    public double getSpentAmount() {
        return spentAmount;
    }

    /**
     * Gets the percentage threshold for budget alerts.
     * @return the alert threshold percentage
     */
    public int getAlertThreshold() {
        return alertThreshold;
    }

    /**
     * Sets the budget identifier.
     * @param budgetId the budget ID to set
     */
    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    /**
     * Sets the maximum spending limit for this budget.
     * @param limitAmount the limit amount to set
     */
    public void setLimitAmount(double limitAmount) {
        this.limitAmount = limitAmount;
    }

    /**
     * Sets the total amount spent under this budget.
     * @param spentAmount the spent amount to set
     */
    public void setSpentAmount(double spentAmount) {
        this.spentAmount = spentAmount;
    }

    /**
     * Sets the percentage threshold for budget alerts.
     * @param alertThreshold the alert threshold percentage to set
     */
    public void setAlertThreshold(int alertThreshold) {
        this.alertThreshold = alertThreshold;
    }

    /**
     * Calculates the remaining budget amount.
     * @return the difference between the limit and spent amount
     */
    double calculateRemaining() {
        return getLimitAmount() - getSpentAmount();
    }

    /**
     * Checks the current status of the budget relative to its limit and alert threshold.
     * @return a string status message ("Budget exceeded", "Approaching budget limit", or "Within budget")
     */
    String checkLimitStatus() {
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
