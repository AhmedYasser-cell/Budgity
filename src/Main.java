package src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;

public class Main extends Application {

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

    public static void main(String[] args) {
        launch(args);
    }
}