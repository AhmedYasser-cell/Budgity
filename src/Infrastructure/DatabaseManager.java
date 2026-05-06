package src.Infrastructure;

import src.UserManagement.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager implements IPersistence {

    private static DatabaseManager instance;
    private String dbUrl;

    private DatabaseManager() {
        this.dbUrl = "jdbc:sqlite:budgeting.db"; 
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(dbUrl);
    }

    public void initializeDatabase() {
        System.out.println("Connecting to database at " + dbUrl);
        String createUsersTable = "CREATE TABLE IF NOT EXISTS Users (" +
                "userId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "email TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL);";

        String createTransactionsTable = "CREATE TABLE IF NOT EXISTS Transactions (" +
                "transactionId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER, " +
                "amount REAL NOT NULL, " +
                "category TEXT, " +
                "type TEXT, " +
                "date TEXT, " +
                "FOREIGN KEY(userId) REFERENCES Users(userId));";

        String createBudgetsTable = "CREATE TABLE IF NOT EXISTS Budgets (" +
                "budgetId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER, " +
                "limitAmount REAL NOT NULL, " +
                "spentAmount REAL NOT NULL, " +
                "alertThreshold INTEGER, " +
                "FOREIGN KEY(userId) REFERENCES Users(userId));";

        String createGoalsTable = "CREATE TABLE IF NOT EXISTS FinancialGoals (" +
                "goalId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER, " +
                "name TEXT NOT NULL, " +
                "targetAmount REAL NOT NULL, " +
                "currentAmount REAL NOT NULL, " +
                "FOREIGN KEY(userId) REFERENCES Users(userId));";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
             
            stmt.execute(createUsersTable);
            stmt.execute(createTransactionsTable);
            stmt.execute(createBudgetsTable);
            stmt.execute(createGoalsTable);
            System.out.println("Database tables initialized successfully.");
            
        } catch (SQLException e) {
            System.out.println("Database initialization failed: " + e.getMessage());
        }
    }

    @Override
    public boolean saveData(User user) {
        // Upsert User
        String upsertUserSQL = "INSERT INTO Users (userId, name, email, password) VALUES (?, ?, ?, ?) " +
                               "ON CONFLICT(userId) DO UPDATE SET name=excluded.name, email=excluded.email, password=excluded.password;";
        
        try (Connection conn = connect();
             java.sql.PreparedStatement pstmtUser = conn.prepareStatement(upsertUserSQL, Statement.RETURN_GENERATED_KEYS)) {
             
            if (user.getUserId() == 0) {
                pstmtUser.setNull(1, java.sql.Types.INTEGER);
            } else {
                pstmtUser.setInt(1, user.getUserId());
            }
            pstmtUser.setString(2, user.getUsername());
            pstmtUser.setString(3, user.getEmail());
            pstmtUser.setString(4, user.getPassword());
            pstmtUser.executeUpdate();
            
            if (user.getUserId() == 0) {
                try (java.sql.ResultSet generatedKeys = pstmtUser.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserId(generatedKeys.getInt(1));
                    }
                }
            }

            // Sync Transactions
            String deleteTxSQL = "DELETE FROM Transactions WHERE userId = ?";
            try (java.sql.PreparedStatement pstmtDel = conn.prepareStatement(deleteTxSQL)) {
                pstmtDel.setInt(1, user.getUserId());
                pstmtDel.executeUpdate();
            }
            String insertTxSQL = "INSERT INTO Transactions (userId, amount, category, type, date) VALUES (?, ?, ?, ?, ?)";
            try (java.sql.PreparedStatement pstmtTx = conn.prepareStatement(insertTxSQL)) {
                for (src.FinanceCore.Transaction t : user.getTransactions()) {
                    pstmtTx.setInt(1, user.getUserId());
                    pstmtTx.setDouble(2, t.getAmount());
                    pstmtTx.setString(3, t.getCategory().name());
                    pstmtTx.setString(4, t instanceof src.FinanceCore.Income ? "INCOME" : "EXPENSE");
                    pstmtTx.setString(5, t.getDate() != null ? t.getDate().toString() : java.time.LocalDate.now().toString());
                    pstmtTx.addBatch();
                }
                pstmtTx.executeBatch();
            }

            // Sync Budgets
            String deleteBudgetsSQL = "DELETE FROM Budgets WHERE userId = ?";
            try (java.sql.PreparedStatement pstmtDel = conn.prepareStatement(deleteBudgetsSQL)) {
                pstmtDel.setInt(1, user.getUserId());
                pstmtDel.executeUpdate();
            }
            String insertBudgetSQL = "INSERT INTO Budgets (userId, limitAmount, spentAmount, alertThreshold) VALUES (?, ?, ?, ?)";
            try (java.sql.PreparedStatement pstmtB = conn.prepareStatement(insertBudgetSQL)) {
                for (src.FinanceCore.Budget b : user.getBudgets()) {
                    pstmtB.setInt(1, user.getUserId());
                    pstmtB.setDouble(2, b.getLimitAmount());
                    pstmtB.setDouble(3, b.getSpentAmount());
                    pstmtB.setInt(4, b.getAlertThreshold());
                    pstmtB.addBatch();
                }
                pstmtB.executeBatch();
            }

            // Sync Goals
            String deleteGoalsSQL = "DELETE FROM FinancialGoals WHERE userId = ?";
            try (java.sql.PreparedStatement pstmtDel = conn.prepareStatement(deleteGoalsSQL)) {
                pstmtDel.setInt(1, user.getUserId());
                pstmtDel.executeUpdate();
            }
            String insertGoalSQL = "INSERT INTO FinancialGoals (userId, name, targetAmount, currentAmount) VALUES (?, ?, ?, ?)";
            try (java.sql.PreparedStatement pstmtG = conn.prepareStatement(insertGoalSQL)) {
                for (src.FinanceCore.FinancialGoal g : user.getGoals()) {
                    pstmtG.setInt(1, user.getUserId());
                    pstmtG.setString(2, g.getName());
                    pstmtG.setDouble(3, g.getTargetAmount());
                    pstmtG.setDouble(4, g.getCurrentAmount());
                    pstmtG.addBatch();
                }
                pstmtG.executeBatch();
            }
            
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to save data: " + e.getMessage());
            return false;
        }
    }

    @Override
    public User loadData(String email) { 
        String sql = "SELECT * FROM Users WHERE email = ?";
        try (Connection conn = connect();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, email);
            java.sql.ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                User user = new User(
                    rs.getInt("userId"),
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getString("email")
                );

                // Load Transactions
                String txSQL = "SELECT * FROM Transactions WHERE userId = ?";
                try (java.sql.PreparedStatement pstmtTx = conn.prepareStatement(txSQL)) {
                    pstmtTx.setInt(1, user.getUserId());
                    java.sql.ResultSet rsTx = pstmtTx.executeQuery();
                    while (rsTx.next()) {
                        int id = rsTx.getInt("transactionId");
                        double amount = rsTx.getDouble("amount");
                        String catStr = rsTx.getString("category");
                        String type = rsTx.getString("type");
                        java.time.LocalDate date = java.time.LocalDate.parse(rsTx.getString("date"));
                        
                        src.FinanceCore.Category cat = src.FinanceCore.Category.valueOf(catStr);
                        
                        if ("INCOME".equals(type)) {
                            user.addTransaction(new src.FinanceCore.Income(id, amount, date, cat, "System")); 
                        } else {
                            user.addTransaction(new src.FinanceCore.Expense(id, amount, date, cat, "System")); 
                        }
                    }
                }

                // Load Budgets
                String budgetSQL = "SELECT * FROM Budgets WHERE userId = ?";
                try (java.sql.PreparedStatement pstmtB = conn.prepareStatement(budgetSQL)) {
                    pstmtB.setInt(1, user.getUserId());
                    java.sql.ResultSet rsB = pstmtB.executeQuery();
                    while (rsB.next()) {
                        int id = rsB.getInt("budgetId");
                        double limit = rsB.getDouble("limitAmount");
                        double spent = rsB.getDouble("spentAmount");
                        int threshold = rsB.getInt("alertThreshold");
                        src.FinanceCore.Budget budget = new src.FinanceCore.Budget(id, limit, spent, threshold);
                        user.addBudget(budget);
                    }
                }

                // Load Goals
                String goalSQL = "SELECT * FROM FinancialGoals WHERE userId = ?";
                try (java.sql.PreparedStatement pstmtG = conn.prepareStatement(goalSQL)) {
                    pstmtG.setInt(1, user.getUserId());
                    java.sql.ResultSet rsG = pstmtG.executeQuery();
                    while (rsG.next()) {
                        int id = rsG.getInt("goalId");
                        String name = rsG.getString("name");
                        double target = rsG.getDouble("targetAmount");
                        double current = rsG.getDouble("currentAmount");
                        src.FinanceCore.FinancialGoal goal = new src.FinanceCore.FinancialGoal(id, name, target, current);
                        user.addGoal(goal);
                    }
                }
                
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Failed to load user: " + e.getMessage());
        }
        return null;
    }

    public boolean verifyLogin(String email, String password) {
        String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";
        try (Connection conn = connect();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            java.sql.ResultSet rs = pstmt.executeQuery();
            
            return rs.next(); // True if a record matches
        } catch (SQLException e) {
            System.out.println("Login verification failed: " + e.getMessage());
            return false;
        }
    }
}
