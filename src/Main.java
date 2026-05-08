package src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;

/**
 * Main entry point for the Budgeting System application.
 * Extends JavaFX Application to launch the graphical user interface.
 */
public class Main extends Application {
    /**
     * Default constructor for Main application.
     */
    public Main() {}

    /**
     * Initializes the database and starts the primary JavaFX stage.
     * @param stage the primary stage for this application
     * @throws Exception if FXML loading or initialization fails
     */
    @Override
    public void start(Stage stage) throws Exception {

        // Ensure database tables exist before doing anything else
        src.Infrastructure.DatabaseManager.getInstance().initializeDatabase();

        Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
        src.Infrastructure.ThemeManager.applyTheme(root);

        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("file:Assets/budgity_logo.png"));
        stage.setTitle("Budgeting System");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Main method to launch the application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}