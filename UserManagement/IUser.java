package UserManagement;

public interface IUser {
    public boolean register(String name, String email, String password);

    public boolean login(String email, String password);
}
