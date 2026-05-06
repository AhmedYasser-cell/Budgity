package src.Infrastructure;

public class NotificationService {

    private static NotificationService instance;

    // Private constructor to enforce Singleton pattern
    private NotificationService() {
    }

    public static NotificationService getInstance() {
        if (instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }

    public void sendAlert(String msg) {
        System.out.println("ALERT: " + msg);
    }
}
