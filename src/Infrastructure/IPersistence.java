package Infrastructure;

import UserManagement.User;

public interface IPersistence {
    boolean saveData(User user);
    User loadData(String userId);
}