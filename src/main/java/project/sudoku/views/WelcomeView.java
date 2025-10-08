package project.sudoku.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeView extends Stage {
    public WelcomeView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/project/sudoku/sudokuMain.fxml")
        );
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setTitle("Sudoku 6x6");
        this.getIcons().add(new Image(
                getClass().getResourceAsStream("/project/sudoku/images/sudokuLogo.png")
        ));
    }

    public static WelcomeView getInstance() throws IOException {
        if (WelcomeView.WelcomeViewHolder.INSTANCE == null) {
            WelcomeView.WelcomeViewHolder.INSTANCE = new WelcomeView();
        }
        return WelcomeView.WelcomeViewHolder.INSTANCE;
    }

    /**
     * Clase estática interna que implementa el patrón Holder
     * para almacenar la instancia única de @link WelcomeView
     */
    private static class WelcomeViewHolder {
        private static WelcomeView INSTANCE = null;
    }
}
