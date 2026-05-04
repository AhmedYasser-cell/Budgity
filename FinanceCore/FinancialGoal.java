package FinanceCore;

public class FinancialGoal {
    private int goalId;

    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    private String name;
    private double targetAmount;
    private double currentAmount;

    public FinancialGoal(int goalId, String name, double targetAmount, double currentAmount) {
        this.goalId = goalId;
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
    }

    void addContribution(double amount) {
        if (amount > 0) {
            setCurrentAmount(getCurrentAmount() + amount);
        } else {
            // Handle invalid amount case
        }
    }

    double getPercentageAchieved() {
        if (getTargetAmount() > 0) {
            return (getCurrentAmount() / getTargetAmount()) * 100;
        } else {
            return 0;
        }
    }
}
