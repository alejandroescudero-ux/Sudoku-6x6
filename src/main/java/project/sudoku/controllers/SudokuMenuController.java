package project.sudoku.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import project.sudoku.models.AlertBox;
import project.sudoku.models.IAlertBox;
import project.sudoku.views.MenuView;
import java.io.IOException;

/**
 * Controller class for the Sudoku menu view.
 * This class manages the menu interactions such as starting the game,
 * showing instructions, and exiting the application.
 */

public class SudokuMenuController {

    /**
     * Handles the action when the "Play" button is clicked.
     * Opens the game menu view and closes the current window.
     *
     * @param event the {@link ActionEvent} triggered by clicking the Play button
     * @throws IOException if the FXML file for the next view cannot be loaded
     */

    @FXML
    public void playButton(ActionEvent event) throws IOException {
        MenuView menuView = MenuView.getInstance();
        menuView.show();

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Displays an informational alert with the game instructions.
     *
     * @param event the {@link ActionEvent} triggered by clicking the Info button
     */

    public void infobutton(ActionEvent event) {
        IAlertBox alertBox = new AlertBox();
        alertBox.showAlertBox(
                "Instrucciones",
                "Tu objetivo es llenar los espacios en blanco (del 1 al 6) sin repetir número en el cuadrante, columna y fila",
                "Este es un Sudoku 6x6"
        );
    }

    /**
     * Closes the application when the "Quit" button is clicked.
     */

    @FXML
    private void quitButton() {
        System.exit(0);
    }
}
