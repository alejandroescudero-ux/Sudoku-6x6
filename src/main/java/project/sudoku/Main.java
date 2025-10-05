package project.sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("sudokuMain.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Image icon = new Image(Main.class.getResourceAsStream("sudokuLogo.png"));
        stage.getIcons().add(icon);
        stage.setTitle("Sudoku 6x6");
        stage.setScene(scene);
        stage.show();
    }
}
