package src.Infrastructure;

import src.UserManagement.User;

public interface IPersistence {
    boolean saveData(User user);

    User loadData(String userId);
}