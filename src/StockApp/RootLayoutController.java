package StockApp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;

public class RootLayoutController {
    // Reference to the main application
    private MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() throws IOException {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
         fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Text files (*.txt)", "*.txt"));

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".txt")) {
                file = new File(file.getPath() + ".txt");
            }
            mainApp.saveCompaniesDataToFile(file);
        }

        // Show success alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Stock Explorer");
        alert.setHeaderText("Success");
        alert.setContentText("File saved successfully");

        alert.showAndWait();
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Stock Explorer");
        alert.setHeaderText("About");
        alert.setContentText("Author: Agnieszka Tarsa");

        alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
