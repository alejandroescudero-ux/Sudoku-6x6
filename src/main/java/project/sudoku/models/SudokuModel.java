package project.sudoku.models;

import java.util.Arrays;
import java.util.Random;

public class SudokuModel {
    private int[][] grid;
    private final int SIZE = 6;

    public SudokuModel() {
        grid = new int[SIZE][SIZE];
        generarSudokuConSolucion();
    }

    // Método principal que garantiza que el sudoku tenga solución
    public void generarSudokuConSolucion() {
        // Primero creamos un sudoku completo válido
        crearSudokuCompleto();

        // Luego quitamos algunos números para crear el puzzle
        crearPuzzleDesdeSolucion();
    }

    private void crearSudokuCompleto() {
        // Limpiar el grid
        for (int i = 0; i < SIZE; i++) {
            Arrays.fill(grid[i], 0);
        }

        // Generar una solución completa
        resolverSudoku(0, 0);
    }

    private boolean resolverSudoku(int fila, int columna) {
        if (fila == SIZE) {
            return true; // Se completó todo el sudoku
        }

        if (columna == SIZE) {
            return resolverSudoku(fila + 1, 0); // Pasar a la siguiente fila
        }

        if (grid[fila][columna] != 0) {
            return resolverSudoku(fila, columna + 1); // Celda ya tiene número
        }

        // Probar números del 1 al 6 en orden aleatorio
        int[] numeros = {1, 2, 3, 4, 5, 6};
        mezclarArray(numeros);

        for (int num : numeros) {
            if (esValido(fila, columna, num)) {
                grid[fila][columna] = num;

                if (resolverSudoku(fila, columna + 1)) {
                    return true;
                }

                grid[fila][columna] = 0; // Retroceder si no funciona
            }
        }
        return false;
    }

    private boolean esValido(int fila, int columna, int numero) {
        // Verificar fila
        for (int i = 0; i < SIZE; i++) {
            if (grid[fila][i] == numero) return false;
        }

        // Verificar columna
        for (int i = 0; i < SIZE; i++) {
            if (grid[i][columna] == numero) return false;
        }

        // Verificar bloque 3x2
        int inicioFila = fila - fila % 2;
        int inicioColumna = columna - columna % 3;

        for (int i = inicioFila; i < inicioFila + 2; i++) {
            for (int j = inicioColumna; j < inicioColumna + 3; j++) {
                if (grid[i][j] == numero) return false;
            }
        }

        return true;
    }

    private void crearPuzzleDesdeSolucion() {
        Random random = new Random();
        int celdasAQuitar = 12; // Nivel de dificultad

        for (int i = 0; i < celdasAQuitar; i++) {
            int fila, columna;
            do {
                fila = random.nextInt(SIZE);
                columna = random.nextInt(SIZE);
            } while (grid[fila][columna] == 0);

            grid[fila][columna] = 0;
        }
    }

    private void mezclarArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int indice = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[indice];
            array[indice] = temp;
        }
    }

    // Método para verificar si el sudoku actual tiene solución
    public boolean tieneSolucion() {
        // Hacemos una copia del grid actual
        int[][] copiaGrid = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(grid[i], 0, copiaGrid[i], 0, SIZE);
        }

        boolean resultado = resolverSudoku(0, 0);

        // Restauramos el grid original
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(copiaGrid[i], 0, grid[i], 0, SIZE);
        }

        return resultado;
    }

    // Getters y setters básicos
    public int getValor(int fila, int columna) {
        return grid[fila][columna];
    }

    public void setValor(int fila, int columna, int valor) {
        if (valor >= 0 && valor <= 6) {
            grid[fila][columna] = valor;
        }
    }

    public int getTamaño() {
        return SIZE;
    }
}