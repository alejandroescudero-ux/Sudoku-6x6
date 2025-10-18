package project.sudoku.models;

import javafx.scene.control.Alert;

/**
 * Implementation of the {@link IAlertBox} interface using JavaFX {@link Alert}.
 * This class provides methods to display informational and warning alert dialogs
 * throughout the application.
 */

public class AlertBox implements IAlertBox {

    /**
     * Displays an information alert dialog with the given title, message, and header.
     *
     * @param title   the title of the alert window
     * @param message the main content message displayed inside the alert
     * @param header  the header text shown at the top of the alert
     */

    @Override
    public void showAlertBox(String title, String message, String header){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a warning alert dialog with the given title, message, and header.
     *
     * @param title   the title of the alert window
     * @param message the main content message displayed inside the alert
     * @param header  the header text shown at the top of the alert
     */

    @Override
    public void showWarningAlertBox(String title, String message, String header){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
