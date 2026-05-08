package src.Infrastructure;

import src.UserManagement.User;
import java.util.HashMap;

/**
 * In-memory implementation of IPersistence using a HashMap.
 * Useful for testing or temporary data storage.
 */
public class DataStorage implements IPersistence {

    // Singleton instance
    private static DataStorage instance;

    // Temp in-memory storage for users
    private HashMap<String, User> users;

    /**
     * Private constructor for Singleton pattern.
     */
    private DataStorage() {
        users = new HashMap<>();
    }

    /**
     * Gets the single instance of DataStorage.
     * @return the DataStorage instance
     */
    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    /**
     * Saves user data to the in-memory map.
     * @param user the user whose data is to be saved
     * @return true always (as it is in-memory)
     */
    @Override
    public boolean saveData(User user) {
        users.put(user.getEmail(), user);
        return true;
    }

    /**
     * Loads user data from the in-memory map.
     * @param userId the unique identifier (email) of the user to load
     * @return the User object, or null if not found
     */
    @Override
    public User loadData(String userId) {
        return users.get(userId);
    }
}