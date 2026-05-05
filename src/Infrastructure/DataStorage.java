package src.Infrastructure;

import src.UserManagement.User;
import java.util.HashMap;

public class DataStorage implements IPersistence {

    // Singleton instance
    private static DataStorage instance;

    // Temp in-memory storage for users
    private HashMap<String, User> users;

    private DataStorage() {
        users = new HashMap<>();
    }

    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    @Override
    public boolean saveData(User user) {
        users.put(user.getEmail(), user);
        return true;
    }

    @Override
    public User loadData(String userId) {
        return users.get(userId);
    }
}