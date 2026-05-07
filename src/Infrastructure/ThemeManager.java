package src.Infrastructure;

import javafx.scene.Parent;

public class ThemeManager {
    private static boolean isDarkMode = false;

    public static void setDarkMode(boolean darkMode) {
        isDarkMode = darkMode;
    }

    public static boolean isDarkMode() {
        return isDarkMode;
    }

    /**
     * Applies the current theme to the provided root node.
     * @param root The root node of the scene.
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
     * @param dialog The dialog to style.
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
