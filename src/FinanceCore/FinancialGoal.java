package src.FinanceCore;

/**
 * Class representing a financial goal with a target amount and current savings.
 */
public class FinancialGoal {
    private int goalId;
    private String name;
    private double targetAmount;
    private double currentAmount;

    /**
     * Gets the unique identifier for the financial goal.
     * @return the goal ID
     */
    public int getGoalId() {
        return goalId;
    }

    /**
     * Sets the unique identifier for the financial goal.
     * @param goalId the goal ID to set
     */
    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    /**
     * Gets the name of the financial goal.
     * @return the goal name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the financial goal.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the target monetary amount to reach the goal.
     * @return the target amount
     */
    public double getTargetAmount() {
        return targetAmount;
    }

    /**
     * Sets the target monetary amount to reach the goal.
     * @param targetAmount the target amount to set
     */
    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    /**
     * Gets the current amount saved towards the goal.
     * @return the current amount
     */
    public double getCurrentAmount() {
        return currentAmount;
    }

    /**
     * Sets the current amount saved towards the goal.
     * @param currentAmount the current amount to set
     */
    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    /**
     * Constructs a new FinancialGoal.
     * @param goalId unique identifier
     * @param name name of the goal
     * @param targetAmount target savings amount
     * @param currentAmount current savings amount
     */
    public FinancialGoal(int goalId, String name, double targetAmount, double currentAmount) {
        this.goalId = goalId;
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
    }

    /**
     * Adds a contribution amount to the current progress of the goal.
     * @param amount the amount to contribute
     */
    void addContribution(double amount) {
        if (amount > 0) {
            setCurrentAmount(getCurrentAmount() + amount);
        } else {
            // Handle invalid amount case
        }
    }

    /**
     * Calculates the percentage of the target amount that has been achieved.
     * @return the percentage achieved (0-100)
     */
    public double getPercentageAchieved() {
        if (getTargetAmount() > 0) {
            return (getCurrentAmount() / getTargetAmount()) * 100;
        } else {
            return 0;
        }
    }
}
