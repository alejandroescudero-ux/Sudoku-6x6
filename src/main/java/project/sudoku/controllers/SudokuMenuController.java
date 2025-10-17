package project.sudoku.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import project.sudoku.views.MenuView;
import java.io.IOException;

public class SudokuMenuController {

    @FXML
    public void playButton(ActionEvent event) {
        try {
            MenuView menuView = MenuView.getInstance();
            menuView.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void quitButton() {
        System.exit(0);
    }
}
