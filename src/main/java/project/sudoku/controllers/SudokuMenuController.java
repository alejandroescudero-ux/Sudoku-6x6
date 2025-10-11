package project.sudoku.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.sudoku.views.WelcomeView;
import java.io.IOException;

public class SudokuMenuController {

    @FXML
    public void playButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WelcomeView.class.getResource("/project/sudoku/sudokuMain.fxml"));
        Scene play = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(play);
        stage.show();
    }

    @FXML
    private void quitButton() {
        System.exit(0);
    }
}

