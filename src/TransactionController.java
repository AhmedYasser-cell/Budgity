package src;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class TransactionController {

    @FXML
    private TableView transactionsTable;

    @FXML
    private TableColumn dateColumn;

    @FXML
    private TableColumn typeColumn;

    @FXML
    private TableColumn categoryColumn;

    @FXML
    private TableColumn amountColumn;

    @FXML
    private Button backButton;

    @FXML
    private void goBack() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}