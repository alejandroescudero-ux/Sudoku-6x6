package project.sudoku.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import project.sudoku.models.AlertBox;
import project.sudoku.models.IAlertBox;
import project.sudoku.views.MenuView;
import java.io.IOException;

public class SudokuMenuController {

    @FXML
    public void playButton(ActionEvent event) throws IOException {
        MenuView menuView = MenuView.getInstance();
        menuView.show();

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    // Muestra las instrucciones del juego
    public void infobutton(ActionEvent event) {
        IAlertBox alertBox = new AlertBox();
        alertBox.showAlertBox(
                "Instrucciones",
                "Tu objetivo es llenar los espacios en blanco (del 1 al 6) sin repetir número en el cuadrante, columna y fila",
                "Este es un Sudoku 6x6"
        );
    }

    @FXML
    private void quitButton() {
        System.exit(0);
    }
}
