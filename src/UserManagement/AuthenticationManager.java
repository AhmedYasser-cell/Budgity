package src.UserManagement;

public class AuthenticationManager {
    public static boolean verifyCredentials(String email, String password) {
        return src.Infrastructure.DatabaseManager.getInstance().verifyLogin(email, password);
    }

    public boolean resetPassword(int userID, String newPassword) {
        User user = src.Infrastructure.DatabaseManager.getInstance().loadData(String.valueOf(userID));
        if (user != null) {
            user.setPassword(newPassword);
            return src.Infrastructure.DatabaseManager.getInstance().saveData(user);
        }
        return false;
    }

}
