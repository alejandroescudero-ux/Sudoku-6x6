package project.sudoku.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import project.sudoku.models.Celda;
import javafx.scene.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SudokuMainController {

    @FXML
    private GridPane sudokuGrid;

    private TextArea[][] celdas = new TextArea[6][6];
    private ArrayList<Celda> listaCeldas = new ArrayList<>();

    // Solución base (válida para un sudoku 6x6)
    private final int[][] solucionBase = {
            {1, 2, 3, 4, 5, 6},
            {4, 5, 6, 1, 2, 3},
            {2, 3, 1, 5, 6, 4},
            {5, 6, 4, 2, 3, 1},
            {3, 1, 2, 6, 4, 5},
            {6, 4, 5, 3, 1, 2}
    };

    @FXML
    public void initialize() {
        mapearCeldasDesdeFXML();
        generarPlantillaAleatoria();
        cargarPlantillaDesdeLista();
    }

    //Crea los TextArea del tablero y los añade al GridPane
    private void mapearCeldasDesdeFXML() {
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                TextArea textArea = new TextArea();
                textArea.setPrefSize(50, 50);
                textArea.setStyle("-fx-font-size: 16px; -fx-alignment: center; -fx-text-alignment: center;");
                sudokuGrid.add(textArea, columna, fila);
                celdas[fila][columna] = textArea;
            }
        }
    }

    // Genera una plantilla aleatoria usando una permutación de números y posiciones vacías

    private void generarPlantillaAleatoria() {
        listaCeldas.clear();
        Random random = new Random();

        // Crear una permutación aleatoria de los números 1–6
        List<Integer> numeros = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
        Collections.shuffle(numeros);
        int[] permutacion = numeros.stream().mapToInt(Integer::intValue).toArray();

        // Generar el tablero aplicando la permutación y dejando algunas celdas vacías
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                int valorOriginal = solucionBase[fila][columna];
                int valorPermutado = permutacion[valorOriginal - 1];

                // 55% de probabilidad de dejar la celda vacía
                if (random.nextDouble() < 0.55) {
                    listaCeldas.add(new Celda(fila, columna, 0));
                } else {
                    listaCeldas.add(new Celda(fila, columna, valorPermutado));
                }
            }
        }
    }

    // Carga los valores del ArrayList (plantilla aleatoria) al GridPane
    private void cargarPlantillaDesdeLista() {
        for (Celda celda : listaCeldas) {
            int fila = celda.getFila();
            int columna = celda.getColumna();
            int valor = celda.getValor();

            TextArea textArea = celdas[fila][columna];
            if (textArea != null) {
                if (valor != 0) {
                    textArea.setText(String.valueOf(valor));
                    textArea.setEditable(false);
                    textArea.setStyle("-fx-control-inner-background: #93c0ff; -fx-font-weight: bold; -fx-text-fill: #002d7e;");
                } else {
                    textArea.clear();
                    textArea.setEditable(true);
                    textArea.setStyle("");
                }
            }
        }
    }

    // Reinicia el tablero generando una nueva plantilla aleatoria
    @FXML
    private void reiniciarTablero() {
        generarPlantillaAleatoria();
        cargarPlantillaDesdeLista();
    }

    // Muestra las instrucciones del juego
    @FXML
    public void infobutton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instrucciones");
        alert.setHeaderText("Este es un Sudoku 6x6");
        alert.setContentText("Tu objetivo es llenar los espacios en blanco (del 1 al 6) sin repetir numero en el cuadrante, columna y fila");
        alert.showAndWait();
    }

    // Regresa al menú principal
    @FXML
    public void backbutton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/project/sudoku/sudokuMenu.fxml"));
        Scene play = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(play);
        stage.show();
    }

    // Genera otra plantilla
    @FXML
    public void restartbutton(ActionEvent event) throws IOException {
        reiniciarTablero();
    }
}



