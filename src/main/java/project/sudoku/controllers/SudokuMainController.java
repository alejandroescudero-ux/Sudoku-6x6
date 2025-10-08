package project.sudoku.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import project.sudoku.models.SudokuModel;

import java.net.URL;
import java.util.ResourceBundle;

public class SudokuMainController implements Initializable {

    // Referencias a los bloques del FXML
    @FXML private GridPane Block1;
    @FXML private GridPane Block2;
    @FXML private GridPane Block3;
    @FXML private GridPane Block4;
    @FXML private GridPane Block5;
    @FXML private GridPane Block6;

    // Botones del FXML
    @FXML private javafx.scene.control.Button hintButton;
    @FXML private javafx.scene.control.Button infoButton;

    // EL MODELO va directo en el controller
    private SudokuModel model;
    private TextArea[][] celdas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicializar el modelo QUE GARANTIZA SOLUCIÓN
        this.model = new SudokuModel();
        this.celdas = new TextArea[6][6];

        // Verificar que tenga solución (medida de seguridad)
        if (!model.tieneSolucion()) {
            model.generarSudokuConSolucion();
        }

        // Inicializar la interfaz
        inicializarVista();
    }

    private void inicializarVista() {
        // Configurar cada bloque con sus coordenadas reales
        inicializarBloque(Block1, 0, 0);  // Block1: Filas 0-1, Columnas 0-2
        inicializarBloque(Block2, 0, 3);  // Block2: Filas 0-1, Columnas 3-5
        inicializarBloque(Block3, 2, 0);  // Block3: Filas 2-3, Columnas 0-2
        inicializarBloque(Block4, 2, 3);  // Block4: Filas 2-3, Columnas 3-5
        inicializarBloque(Block5, 4, 0);  // Block5: Filas 4-5, Columnas 0-2
        inicializarBloque(Block6, 4, 3);  // Block6: Filas 4-5, Columnas 3-5

        // Mostrar los valores iniciales del sudoku
        actualizarVista();
    }

    private void inicializarBloque(GridPane bloque, int filaInicio, int columnaInicio) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                int filaReal = filaInicio + i;
                int columnaReal = columnaInicio + j;

                TextArea textArea = (TextArea) bloque.getChildren().get(i * 3 + j);
                celdas[filaReal][columnaReal] = textArea;

                configurarTextArea(textArea, filaReal, columnaReal);
            }
        }
    }

    private void configurarTextArea(TextArea textArea, int fila, int columna) {
        textArea.setWrapText(true);

        int valor = model.getValor(fila, columna);

        if (valor != 0) {
            // Celda con número inicial - solo lectura
            textArea.setEditable(false);
            textArea.setText(String.valueOf(valor));
            textArea.setStyle("-fx-font-weight: bold; -fx-control-inner-background: #f0f0f0;");
        } else {
            // Celda vacía - editable por el usuario
            textArea.setEditable(true);
            textArea.setText("");
        }

        // Listener para cambios en el texto
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("[1-6]")) {
                int valorIngresado = Integer.parseInt(newValue);
                model.setValor(fila, columna, valorIngresado);
            } else if (newValue.isEmpty()) {
                model.setValor(fila, columna, 0);
            } else {
                textArea.setText(oldValue);
            }
        });
    }

    private void actualizarVista() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                int valor = model.getValor(i, j);
                if (celdas[i][j] != null) {
                    if (valor != 0) {
                        celdas[i][j].setText(String.valueOf(valor));
                    } else {
                        celdas[i][j].setText("");
                    }
                }
            }
        }
    }

    // Método para el botón "Reiniciar"
    @FXML
    private void manejarReiniciar() {
        // Generar un NUEVO sudoku con solución garantizada
        model.generarSudokuConSolucion();
        actualizarVista();
    }

    // Método para el botón "Pista"
    @FXML
    private void manejarPista() {
        // Para implementar después
        System.out.println("Función de pista - por implementar");
    }

    // Método para el botón "Info" (?)
    @FXML
    private void manejarInfo() {
        // Para implementar después
        System.out.println("Instrucciones - por implementar");
    }

    // Métodos públicos para acceder desde otras partes si es necesario
    public int getValorCelda(int fila, int columna) {
        return model.getValor(fila, columna);
    }

    public void setValorCelda(int fila, int columna, int valor) {
        model.setValor(fila, columna, valor);
    }

    public boolean verificarSolucionCompleta() {
        return model.tieneSolucion();
    }
}