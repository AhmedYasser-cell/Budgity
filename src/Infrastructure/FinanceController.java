package Infrastructure;

import UserManagement.User;
import FinanceCore.Transaction;
import FinanceCore.Expense;
import FinanceCore.Report;
import FinanceCore.Income;


public class FinanceController {

    private final User currentUser;
    private final IPersistence storage;

    public FinanceController(User user, IPersistence storage) {
        this.currentUser = user;
        this.storage = storage;
    }

    public void addRecord(Transaction t) {
        if (t == null)
        {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        if (!t.validateAmount())
        {
            throw new IllegalArgumentException("Invalid transaction amount");
        }

        currentUser.addTransaction(t);

        if (t instanceof Expense && !validateBalance()) {
            currentUser.removeTransaction(t.getTransactionId());
            throw new IllegalArgumentException("Insufficient balance");
        }

        storage.saveData(currentUser);
    }

    public void deleteRecord(int  transactionID) {
        if (transactionID <= 0)
        {
            throw new IllegalArgumentException("Invalid transaction ID");
        }
        if (!currentUser.hasTransaction(transactionID))
        {
            throw new IllegalArgumentException("Transaction ID does not exist");
        }   

        currentUser.removeTransaction(transactionID);
        storage.saveData(currentUser);

    }

    public Report getMonthlySummary(int month) {
    
        if (month < 1 || month > 12)   
        {
            throw new IllegalArgumentException("Invalid month");
        }
        if (currentUser.getTransactions().isEmpty())
        {
            throw new IllegalStateException("No transactions available for summary");
        }

        double totalIncome = 0;
        double totalExpense = 0;

        
        for(Transaction t : currentUser.getTransactions())
        {
            if (t.getDate().getMonthValue() == month)
            {
                if (t instanceof Income)
                {
                    totalIncome += t.getAmount();
                }
            
            else if (t instanceof Expense)
                {
                    totalExpense += t.getAmount();
                }
            }
        }

        double netBalance = totalIncome - totalExpense;
        Report report = new Report(month, totalIncome, totalExpense, netBalance);
        return report;
    }

    public boolean validateBalance() {
            double totalIncome = 0;
            double totalExpense = 0;
            for(Transaction t : currentUser.getTransactions())
            {
                if (t instanceof Income)
                {
                    totalIncome += t.getAmount();
                }
            
            else if (t instanceof Expense)
                {
                    totalExpense += t.getAmount();
                }
            }
           
        double netBalance = totalIncome - totalExpense;

        return netBalance >= 0;
    }
}