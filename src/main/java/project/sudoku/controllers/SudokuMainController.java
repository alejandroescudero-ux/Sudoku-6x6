package project.sudoku.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import project.sudoku.models.SudokuModel;

public class SudokuMainController {

    /**
     * El modelo del sudoku que contiene la logica del juego y el estado del tablero
     * Sigue el patron MVC (Modelo-Vista-Controlador) donde el controlador actua como intermediario
     * entre la vista (interfaz grafica) y el modelo (logica del juego)
     */
    private SudokuModel sudokuModel;

    /**
     * Declaracion de todos los TextArea que representan las celdas del tablero de sudoku
     * Cada variable corresponde a una celda especifica en la interfaz grafica
     * La nomenclatura cell_fila_columna hace referencia a la posicion en el tablero 6x6
     * Estas variables se inyectan automaticamente por JavaFX usando los fx:id del archivo FXML
     */
    @FXML private TextArea cell_0_0, cell_0_1, cell_0_2, cell_0_3, cell_0_4, cell_0_5;
    @FXML private TextArea cell_1_0, cell_1_1, cell_1_2, cell_1_3, cell_1_4, cell_1_5;
    @FXML private TextArea cell_2_0, cell_2_1, cell_2_2, cell_2_3, cell_2_4, cell_2_5;
    @FXML private TextArea cell_3_0, cell_3_1, cell_3_2, cell_3_3, cell_3_4, cell_3_5;
    @FXML private TextArea cell_4_0, cell_4_1, cell_4_2, cell_4_3, cell_4_4, cell_4_5;
    @FXML private TextArea cell_5_0, cell_5_1, cell_5_2, cell_5_3, cell_5_4, cell_5_5;

    /**
     * Matriz bidimensional que organiza todas las celdas del sudoku en una estructura 6x6
     * Esto facilita el acceso a las celdas usando coordenadas [fila][columna] en lugar de
     * tener que usar variables individuales para cada celda
     */
    private TextArea[][] celdas;

    /**
     * Metodo inicializador que JavaFX ejecuta automaticamente despues de que el archivo FXML se carga
     * Este metodo se llama una sola vez cuando la interfaz grafica esta lista para ser usada
     * Aqui se configura el estado inicial del controlador y se prepara la vista
     */
    @FXML
    public void initialize() {
        /**
         * Inicializamos la matriz celdas con todas las TextArea individuales
         * Esta organizacion nos permite acceder a cualquier celda usando celdas[fila][columna]
         * lo que hace mucho mas facil recorrer y manipular todas las celdas del tablero
         * La estructura sigue el formato:
         * Fila 0: [cell_0_0, cell_0_1, cell_0_2, cell_0_3, cell_0_4, cell_0_5]
         * Fila 1: [cell_1_0, cell_1_1, cell_1_2, cell_1_3, cell_1_4, cell_1_5]
         * ... y asi sucesivamente hasta la fila 5
         */
        celdas = new TextArea[][] {
                {cell_0_0, cell_0_1, cell_0_2, cell_0_3, cell_0_4, cell_0_5},
                {cell_1_0, cell_1_1, cell_1_2, cell_1_3, cell_1_4, cell_1_5},
                {cell_2_0, cell_2_1, cell_2_2, cell_2_3, cell_2_4, cell_2_5},
                {cell_3_0, cell_3_1, cell_3_2, cell_3_3, cell_3_4, cell_3_5},
                {cell_4_0, cell_4_1, cell_4_2, cell_4_3, cell_4_4, cell_4_5},
                {cell_5_0, cell_5_1, cell_5_2, cell_5_3, cell_5_4, cell_5_5}
        };

        /**
         * Iniciamos un nuevo juego llamando a este metodo
         * Esto crea un nuevo modelo de sudoku y actualiza la vista para mostrar el tablero inicial
         */
        empezarJuegoNuevo();
    }

    /**
     * Metodo para comenzar un nuevo juego de sudoku
     * Crea una nueva instancia del modelo y actualiza la interfaz grafica
     * Este metodo puede ser llamado desde botones como "Nuevo Juego" o "Reiniciar"
     */
    public void empezarJuegoNuevo() {
        /**
         * Creamos una nueva instancia del modelo SudokuModel con tamaño 6 (para un sudoku 6x6)
         * El modelo se encargara de generar un nuevo tablero valido con algunos numeros visibles
         * y otros ocultos para que el jugador los complete
         */
        sudokuModel = new SudokuModel(6);
        /**
         * Actualizamos la vista para reflejar el nuevo estado del tablero
         * Esto mostrara los numeros iniciales (pistas) en azul y dejara vacias las celdas editables
         */
        actualizarVista();
    }

    /**
     * Actualiza toda la interfaz grafica para mostrar el estado actual del tablero
     * Recorre todas las celdas del tablero y las actualiza individualmente
     * mostrando los numeros del modelo en las TextArea correspondientes
     */
    private void actualizarVista() {
        /**
         * Obtenemos el tablero actual del modelo
         * Este es una matriz 6x6 de enteros donde 0 representa una celda vacia
         * y los numeros 1-6 representan los valores de las celdas llenas
         */
        int[][] tablero = sudokuModel.getBoard();

        /**
         * Recorremos todas las filas y columnas del tablero usando dos bucles anidados
         * Para cada posicion [fila][columna], obtenemos la celda correspondiente de la matriz 'celdas'
         * y la actualizamos con el valor del tablero en esa misma posicion
         */
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                actualizarCelda(celdas[fila][columna], tablero[fila][columna]);
            }
        }
    }

    /**
     * Actualiza una celda individual de la interfaz grafica
     * Configura el texto, estilo y editabilidad de la celda segun si tiene un valor o esta vacia
     *
     * @param celda el objeto TextArea que representa la celda en la interfaz grafica
     * @param valor el numero que debe mostrarse en la celda (0 para celda vacia)
     */
    private void actualizarCelda(TextArea celda, int valor) {
        /**
         * Verificacion de seguridad: si la celda es null, salimos del metodo
         * Esto previene NullPointerException en caso de que alguna celda no se haya inicializado correctamente
         */
        if (celda == null) return;

        /**
         * Si el valor es diferente de 0, significa que esta celda tiene un numero (es una pista)
         * Las celdas con numeros se muestran en azul y negrita, y no son editables por el jugador
         */
        if (valor != 0) {
            /**
             * Establecemos el texto de la celda con el numero convertido a String
             */
            celda.setText(String.valueOf(valor));
            /**
             * Aplicamos estilos CSS para hacer el texto azul y en negrita
             * -fx-font-weight: bold hace el texto en negrita
             * -fx-text-fill: blue cambia el color del texto a azul
             */
            celda.setStyle("-fx-font-weight: bold; -fx-text-fill: blue;");
            /**
             * Hacemos la celda no editable para que el jugador no pueda modificar las pistas
             */
            celda.setEditable(false);
        } else {
            /**
             * Si el valor es 0, significa que esta celda esta vacia
             * Limpiamos cualquier texto que pudiera tener la celda
             */
            celda.setText("");
            /**
             * Removemos cualquier estilo aplicado anteriormente
             * Esto restaura el estilo por defecto de la TextArea
             */
            celda.setStyle("");
            /**
             * Hacemos la celda editable para que el jugador pueda ingresar numeros
             */
            celda.setEditable(true);
        }
    }
}