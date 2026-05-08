package src.Infrastructure;

import src.UserManagement.User;

/**
 * Interface for data persistence operations.
 * Allows for different storage implementations (e.g., Database, File).
 */
public interface IPersistence {
    /**
     * Saves user data to the storage medium.
     * @param user the user whose data is to be saved
     * @return true if save was successful, false otherwise
     */
    boolean saveData(User user);

    /**
     * Loads user data from the storage medium.
     * @param email the unique email/identifier of the user to load
     * @return the populated User object, or null if not found
     */
    User loadData(String email);
}