package src.UserManagement;

/**
 * Utility class for managing user authentication and password operations.
 */
public class AuthenticationManager {
    /**
     * Default constructor for AuthenticationManager.
     */
    public AuthenticationManager() {}

    /**
     * Verifies user credentials against the database.
     * @param email the user's email
     * @param password the user's password
     * @return true if credentials match a user in the database
     */
    public static boolean verifyCredentials(String email, String password) {
        return src.Infrastructure.DatabaseManager.getInstance().verifyLogin(email, password);
    }

    /**
     * Resets the password for a specific user.
     * @param userID the ID of the user
     * @param newPassword the new password to set
     * @return true if the password was successfully reset and saved
     */
    public boolean resetPassword(int userID, String newPassword) {
        User user = src.Infrastructure.DatabaseManager.getInstance().loadData(String.valueOf(userID));
        if (user != null) {
            user.setPassword(newPassword);
            return src.Infrastructure.DatabaseManager.getInstance().saveData(user);
        }
        return false;
    }

}
