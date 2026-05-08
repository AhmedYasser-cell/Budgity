package src.UserManagement;

/**
 * Interface defining core user operations like registration and login.
 */
public interface IUser {
    /**
     * Registers a new user in the system.
     * @param name user's display name
     * @param email user's unique email
     * @param password user's password
     * @return true if registration was successful
     */
    public boolean register(String name, String email, String password);

    /**
     * Authenticates a user in the system.
     * @param email user's email
     * @param password user's password
     * @return true if login was successful
     */
    public boolean login(String email, String password);
}
