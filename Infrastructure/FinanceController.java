package Infrastructure;

import UserManagement.User;
import FinanceCore.Transaction;
import FinanceCore.Expense;
import FinanceCore.Report;
import FinanceCore.Income;


public class FinanceController {

    private User currentUser;
    private IPersistence storage;

    public FinanceController(User user, IPersistence storage) {
        this.currentUser = user;
        this.storage = storage;
    }

    public void addRecord(Transaction t) {}

    public void deleteRecord(Transaction t) {}

    public Report getMonthlySummary(int month) {
        return null;
    }

    public boolean validateBalance() {
        return false;
    }
}