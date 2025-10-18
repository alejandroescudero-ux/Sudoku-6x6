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

public class SudokuMainController {

    @FXML
    private Button hintButton;

    @FXML
    void hintButton(ActionEvent event) {

    }
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

                textArea.getStyleClass().add("sudoku-cell"); // ✅ CSS

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
                if (random.nextDouble() < 0.65) {
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
                } else {
                    textArea.clear();
                    textArea.setEditable(true);
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

    // Regresa al menú principal
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
    @FXML
    private Button exitButton;
    // Genera otra plantilla
    @FXML
    public void restartbutton(ActionEvent event) throws IOException {
        reiniciarTablero();
    }
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

    // Comprueba si todas las celdas están llenas
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

    // Valida que todas las reglas del sudoku se cumplan
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

    // Verifica que un valor no se repita en fila, columna o cuadrante 2x3
    private boolean esValido(int fila, int columna) {
        String texto = celdas[fila][columna].getText().trim();
        if (texto.isEmpty()) return true;
        int valor = Integer.parseInt(texto);

        // Validar fila
        for (int j = 0; j < 6; j++) {
            if (j != columna && celdas[fila][j].getText().trim().equals(texto)) {
                return false;
            }
        }

        // Validar columna
        for (int i = 0; i < 6; i++) {
            if (i != fila && celdas[i][columna].getText().trim().equals(texto)) {
                return false;
            }
        }

        // Validar subcuadrante (2x3)
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





