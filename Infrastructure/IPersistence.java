package Infrastructure;

import UserManagement.User;

public interface IPersistence {
    boolean saveToFile(User user);
    User loadData(String userId);
}