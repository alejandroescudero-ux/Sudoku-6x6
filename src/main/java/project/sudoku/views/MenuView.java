package project.sudoku.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuView extends Stage {
    public MenuView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/project/sudoku/sudokuMain.fxml")
        );
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setTitle("MiniDoku: en juego");
        this.getIcons().add(new Image(
                getClass().getResourceAsStream("/project/sudoku/images/sudokuLogo.png")
        ));
    }

    public static MenuView getInstance() throws IOException {
        if (MenuViewHolder.INSTANCE == null) {
            MenuViewHolder.INSTANCE = new MenuView();
        }
        return MenuViewHolder.INSTANCE;
    }

    /**
     * Clase estática interna que implementa el patrón Holder
     * para almacenar la instancia única de @link MenuView
     */
    private static class MenuViewHolder {
        private static MenuView INSTANCE = null;
    }
}
