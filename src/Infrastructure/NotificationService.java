package src.Infrastructure;

/**
 * Singleton service for handling system notifications and alerts.
 */
public class NotificationService {

    private static NotificationService instance;

    /**
     * Private constructor to enforce Singleton pattern.
     */
    private NotificationService() {
    }

    /**
     * Gets the single instance of NotificationService.
     * @return the NotificationService instance
     */
    public static NotificationService getInstance() {
        if (instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }

    /**
     * Sends an alert message (currently prints to console).
     * @param msg the message to be sent as an alert
     */
    public void sendAlert(String msg) {
        System.out.println("ALERT: " + msg);
    }
}
