package UserManagement;

import Infrastructure.DataStorage;

public class AuthenticationManager {
    public static boolean verifyCredentials(String email, String password) {
        User user = DataStorage.getInstance().loadData(email);
        if (user == null) {
            return false;
        }
        return true;
    }

    public boolean resetPassword(int userID, String newPassword) {
        User user = DataStorage.getInstance().loadData(String.valueOf(userID));
        if (user != null) {
            user.setPassword(newPassword);
            return DataStorage.getInstance().saveData(user);
        }
        return false;
    }

}
