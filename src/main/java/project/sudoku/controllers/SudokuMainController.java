package project.sudoku.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import project.sudoku.models.AlertBox;
import project.sudoku.models.Celda;
import project.sudoku.models.IAlertBox;
import project.sudoku.views.WelcomeView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * Main controller for the 6x6 Sudoku game.
 * Handles board initialization, validation, random puzzle generation,
 * and user interaction through the JavaFX interface.
 *
 * @author Alejandro Escudero - Jesus Tovar
 * @version 2.8.4
 * @since 5-10-2025
 */

public class SudokuMainController {

    /** The GridPane layout representing the Sudoku board. */
    @FXML
    private GridPane sudokuGrid;

    /** Two-dimensional array holding references to the board's text areas. */
    private TextArea[][] celdas = new TextArea[6][6];

    /** List containing logical representations of the board's cells. */
    private ArrayList<Celda> listaCeldas = new ArrayList<>();

    /**
     * Base solved Sudoku pattern for a valid 6x6 grid.
     * This is used as the foundation to generate random puzzles.
     */
    private final int[][] solucionBase = {
            {1, 2, 3, 4, 5, 6},
            {4, 5, 6, 1, 2, 3},
            {2, 3, 1, 5, 6, 4},
            {5, 6, 4, 2, 3, 1},
            {3, 1, 2, 6, 4, 5},
            {6, 4, 5, 3, 1, 2}
    };

    /**
     * Initializes the Sudoku board when the controller is loaded.
     * It maps UI elements, generates a random puzzle, and loads it into the grid.
     */

    @FXML
    public void initialize() {
        mapearCeldasDesdeFXML();
        generarPlantillaAleatoria();
        cargarPlantillaDesdeLista();
    }

    /**
     * Creates TextAreas for each cell and adds them to the GridPane.
     * Configures input validation and cell styling.
     */

    private void mapearCeldasDesdeFXML() {
        sudokuGrid.getChildren().clear();
        sudokuGrid.getColumnConstraints().clear();
        sudokuGrid.getRowConstraints().clear();

        for (int i = 0; i < 6; i++) {
            sudokuGrid.getColumnConstraints().add(new javafx.scene.layout.ColumnConstraints(55));
            sudokuGrid.getRowConstraints().add(new javafx.scene.layout.RowConstraints(55));
        }

        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                TextArea textArea = new TextArea();
                textArea.setWrapText(false);
                textArea.setPrefWidth(55);
                textArea.setPrefHeight(55);
                textArea.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
                textArea.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

                textArea.getStyleClass().add("sudoku-cell");

                // Allow only numbers 1-6
                textArea.textProperty().addListener((obs, oldValue, newValue) -> {
                    if (!newValue.matches("[1-6]?")) {
                        textArea.setText(oldValue);
                    }
                });

                sudokuGrid.add(textArea, columna, fila);
                celdas[fila][columna] = textArea;
            }
        }

        sudokuGrid.setHgap(5);
        sudokuGrid.setVgap(5);
    }

    /**
     * Generates a random Sudoku puzzle based on the base solution pattern.
     * It applies a random number permutation and randomly empties certain cells.
     */

    private void generarPlantillaAleatoria() {
        listaCeldas.clear();
        Random random = new Random();

        // Create a random permutation of numbers 1–6
        List<Integer> numeros = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
        Collections.shuffle(numeros);
        int[] permutacion = numeros.stream().mapToInt(Integer::intValue).toArray();

        // Fill cells applying permutation and leaving some empty
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                int valorOriginal = solucionBase[fila][columna];
                int valorPermutado = permutacion[valorOriginal - 1];

                // 65% chance to leave the cell empty
                if (random.nextDouble() < 0.65) {
                    listaCeldas.add(new Celda(fila, columna, 0));
                } else {
                    listaCeldas.add(new Celda(fila, columna, valorPermutado));
                }
            }
        }
    }

    /**
     * Loads cell values from the generated puzzle (ArrayList) into the GridPane.
     * Non-empty cells become non-editable.
     */

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
                } else {
                    textArea.clear();
                    textArea.setEditable(true);
                }
            }
        }
    }

    /**
     * Regenerates the Sudoku puzzle and reloads it into the board.
     */

    @FXML
    private void reiniciarTablero() {
        generarPlantillaAleatoria();
        cargarPlantillaDesdeLista();
    }

    /**
     * Returns to the main menu view.
     *
     * @param event the ActionEvent triggered by the exit button
     */

    @FXML
    void onExitButton(ActionEvent event) {
        Stage currentStage = (Stage) exitButton.getScene().getWindow();
        currentStage.close();
        try {
            WelcomeView welcomeView = new WelcomeView();
            welcomeView.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Button that closes the Sudoku view and returns to the main menu. */
    @FXML
    private Button exitButton;

    /**
     * Generates a new random puzzle when the restart button is pressed.
     *
     * @param event the ActionEvent triggered by the restart button
     * @throws IOException if there is an issue reloading the board
     */

    @FXML
    public void restartbutton(ActionEvent event) throws IOException {
        reiniciarTablero();
    }

    /**
     * Validates the current Sudoku solution when the verify button is pressed.
     * Displays alerts depending on the result.
     *
     * @param event the ActionEvent triggered by the verify button
     */

    @FXML
    void onVerify(ActionEvent event) {
        IAlertBox alertBox = new AlertBox();

        if (!tableroCompleto()) {
            alertBox.showWarningAlertBox(
                    "Sudoku Incompleto",
                    "Aún quedan celdas vacías. Llena todas las casillas antes de verificar.",
                    "Tablero incompleto"
            );
            return;
        }

        if (validarTablero()) {
            alertBox.showAlertBox(
                    "¡Felicidades!",
                    "Has completado correctamente el Sudoku.",
                    "Juego Terminado"
            );
        } else {
            alertBox.showWarningAlertBox(
                    "Sudoku Incorrecto",
                    "Algunas celdas tienen valores repetidos o inválidos. Intenta corregirlos.",
                    "Verifica tu solución"
            );
        }
    }

    /**
     * Checks if all cells in the Sudoku grid are filled.
     *
     * @return true if the board is completely filled; false otherwise
     */

    private boolean tableroCompleto() {
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                String texto = celdas[fila][columna].getText().trim();
                if (texto.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Validates that the Sudoku grid follows all standard Sudoku rules.
     * Ensures no repeated numbers in rows, columns, or 2x3 subgrids.
     *
     * @return true if the board is valid; false otherwise
     */

    private boolean validarTablero() {
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                String texto = celdas[fila][columna].getText().trim();
                if (texto.isEmpty()) continue;

                try {
                    int valor = Integer.parseInt(texto);
                    if (valor < 1 || valor > 6) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }

                if (!esValido(fila, columna)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Verifies that a specific cell’s value does not violate Sudoku constraints.
     * Checks the row, column, and 2x3 subgrid for duplicates.
     *
     * @param fila the row index of the cell
     * @param columna the column index of the cell
     * @return true if the value is valid according to Sudoku rules
     */

    private boolean esValido(int fila, int columna) {
        String texto = celdas[fila][columna].getText().trim();
        if (texto.isEmpty()) return true;
        int valor = Integer.parseInt(texto);

        // Validate row
        for (int j = 0; j < 6; j++) {
            if (j != columna && celdas[fila][j].getText().trim().equals(texto)) {
                return false;
            }
        }

        // Validate column
        for (int i = 0; i < 6; i++) {
            if (i != fila && celdas[i][columna].getText().trim().equals(texto)) {
                return false;
            }
        }

        // Validate 2x3 subgrid
        int inicioFila = (fila / 2) * 2;
        int inicioColumna = (columna / 3) * 3;

        for (int i = inicioFila; i < inicioFila + 2; i++) {
            for (int j = inicioColumna; j < inicioColumna + 3; j++) {
                if ((i != fila || j != columna) &&
                        celdas[i][j].getText().trim().equals(texto)) {
                    return false;
                }
            }
        }

        return true;
    }
}





