package src.Infrastructure;

import javafx.scene.Parent;

/**
 * Utility class for managing the application's visual theme (Light/Dark mode).
 * Handles applying stylesheets and style classes to JavaFX components.
 */
public class ThemeManager {
    /**
     * Default constructor for ThemeManager.
     */
    public ThemeManager() {}

    private static boolean isDarkMode = false;

    /**
     * Sets the global dark mode state.
     * @param darkMode true to enable dark mode, false for light mode
     */
    public static void setDarkMode(boolean darkMode) {
        isDarkMode = darkMode;
    }

    /**
     * Checks if dark mode is currently enabled.
     * @return true if dark mode is on, false otherwise
     */
    public static boolean isDarkMode() {
        return isDarkMode;
    }

    /**
     * Applies the current theme (stylesheet and style class) to the provided root node.
     * @param root the root node of the scene
     */
    public static void applyTheme(Parent root) {
        // Link the stylesheet if not already linked
        String stylesheet = ThemeManager.class.getResource("/src/style.css").toExternalForm();
        if (!root.getStylesheets().contains(stylesheet)) {
            root.getStylesheets().add(stylesheet);
        }

        // Toggle the 'dark' style class
        if (isDarkMode) {
            if (!root.getStyleClass().contains("dark")) {
                root.getStyleClass().add("dark");
            }
        } else {
            root.getStyleClass().remove("dark");
        }
    }

    /**
     * Applies the current theme to a JavaFX Dialog.
     * @param dialog the dialog to style
     */
    public static void applyThemeToDialog(javafx.scene.control.Dialog<?> dialog) {
        javafx.scene.control.DialogPane dialogPane = dialog.getDialogPane();
        String stylesheet = ThemeManager.class.getResource("/src/style.css").toExternalForm();
        
        if (!dialogPane.getStylesheets().contains(stylesheet)) {
            dialogPane.getStylesheets().add(stylesheet);
        }

        if (isDarkMode) {
            dialogPane.getStyleClass().add("dark");
        } else {
            dialogPane.getStyleClass().remove("dark");
        }
    }
}
