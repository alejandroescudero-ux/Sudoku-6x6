package project.sudoku.models;

import java.util.Random;

public class SudokuModel {

    /**
     * primero inicializamos una matriz vacia, el size es una variable que se le pasa al modelo para que
     * el tamaño sea dinamico y escalable al futuro, random lo utilizamos para poner numero aleatorios en
     * el tablero
     */
    private int[][] board;
    private int size;
    private Random random;


    /**
     * constructor donde iniciamos todo
     */
    public SudokuModel(int size) {
        this.size = size;
        this.board = new int[size][size];
        this.random = new Random();
        crearPuzzleValido();
    }

    /**
     *  funcion para crear un tablero con solucion y posteriormente quitar algunos numeros para que sea jugable
     */
    private void crearPuzzleValido() {
        resolverYCrearPuzzle(0, 0);
        quitarNumerosParaPuzzle(15);
    }


    private boolean resolverYCrearPuzzle(int fila, int columna) {
        /**
         *este if es para que una vez todo el tablero este creado y resuelto por el algoritmo, se detenga
         */
        if (fila == size) {
            return true;
        }
        /**
         * este if hace que una vez todas las columnas de una fila esten llenas, vuelva a iniciar el bucle en la
         * fila siguiente
         */
        if (columna == size) {
            return resolverYCrearPuzzle(fila + 1, 0);
        }

        /**
         * este for es la recursion, lo que hace es probar 6 veces (6 casillas) poner un numero aleatorio
         */
        for (int i = 0; i < 6; i++) {

            /**
             * se usa el random para que elija un numero aleatorio y ese sea el que se ponga
             */
            int num = random.nextInt(6) + 1;

            /**
             * este if llama a la funcion puedoPonerNumero para validar que este numero no este ni dentro de su
             * cuadricula (2x3) ni tampoco en la fila general del sudoku (esta es la validacion que pidio el profe)
             */
            if (puedoPonerNumero(fila, columna, num)) {

                /**
                 *si el numero se puede poner, se coloca en esa casilla de la matriz
                 */
                board[fila][columna] = num;

                /**
                 * este if es parte de la recursion, una vez ya se puso el numero volvemos a ejecutar esta funcion
                 * pero en la columna siguiente
                 */
                if (resolverYCrearPuzzle(fila, columna + 1)) {
                    return true;
                }

                /**
                 * aqui esta linea lo que hace es que si no se encontro ningun numero valido para las celdas siguientes
                 * lo convierte en 0. Esto es el BACKTRACKING: si la eleccion actual (el numero que pusimos)
                 * lleva a un callejon sin salida, deshacemos esa decision y probamos con otro numero.
                 * El bucle for continuara con la siguiente iteracion, probando un numero diferente
                 */
                board[fila][columna] = 0;
            }
        }
        /**
         * este false esta ligado al if que da return true, si esto da false el codigo se devuelve hasta crear
         * una combinacion que si tenga solucion. Si llegamos a este punto, significa que hemos probado los 6 numeros
         * posibles (1-6) en esta celda y NINGUNO funciono. Esto le indica a la celda anterior que su eleccion
         * de numero esta causando problemas y debe intentar con un numero diferente.
         * El return false hace que el control vuelva a la celda anterior, la cual entonces probara su siguiente
         * numero en su propio bucle for.
         */
        return false;
    }

    /**
     * Esta funcion verifica si un numero puede ser colocado en una posicion especifica del tablero
     * siguiendo las reglas del Sudoku 6x6:
     * 1. El numero no debe aparecer en la misma fila
     * 2. El numero no debe aparecer en la misma columna
     * 3. El numero no debe aparecer en el mismo bloque 2x3
     */
    private boolean puedoPonerNumero(int fila, int columna, int numero) {
        /**
         * Verificacion de la FILA: recorremos todas las columnas de la fila actual
         * para asegurarnos que el numero no se repita en esta fila
         */
        for (int c = 0; c < size; c++) {
            if (board[fila][c] == numero) {
                return false;
            }
        }
        /**
         * Verificacion de la COLUMNA: recorremos todas las filas de la columna actual
         * para asegurarnos que el numero no se repita en esta columna
         */
        for (int f = 0; f < size; f++) {
            if (board[f][columna] == numero) {
                return false;
            }
        }
        /**
         * Verificacion del BLOQUE 2x3: calculamos el inicio del bloque 2x3 donde esta la celda
         * Para filas: (fila / 2) * 2 - esto nos da 0, 2, o 4 (el inicio de cada bloque de 2 filas)
         * Para columnas: (columna / 3) * 3 - esto nos da 0 o 3 (el inicio de cada bloque de 3 columnas)
         * Luego recorremos las 2 filas y 3 columnas de ese bloque especifico
         */
        int inicioFila = (fila / 2) * 2;
        int inicioColumna = (columna / 3) * 3;

        for (int f = inicioFila; f < inicioFila + 2; f++) {
            for (int c = inicioColumna; c < inicioColumna + 3; c++) {
                if (board[f][c] == numero) {
                    return false;
                }
            }
        }
        /**
         * Si pasamos todas las verificaciones (fila, columna y bloque), entonces el numero
         * puede ser colocado en esta posicion de manera valida
         */
        return true;
    }

    /**
     * Esta funcion toma la matriz y quita algunos numeros para hacer que el sudoku sea jugable
     */
    private void quitarNumerosParaPuzzle(int numerosAMantener) {

        /**
         * calcula las celdas totales y cuantos numeros debemos quitar
         */
        int totalCeldas = size * size;
        int numerosAQuitar = totalCeldas - numerosAMantener;

        int quitados = 0;

        /**
         * Usamos un bucle while para ir quitando numeros uno por uno hasta alcanzar
         * la cantidad deseada de numeros quitados
         */
        while (quitados < numerosAQuitar) {

            /**
             * Elegimos una posicion aleatoria en el tablero
             * random.nextInt(size) genera un numero entre 0-5 para filas y columnas
             */
            int fila = random.nextInt(size);
            int columna = random.nextInt(size);

            /**
             * Solo quitamos el numero si la celda no esta vacia (tiene un numero)
             * Esto evita intentar quitar numeros de celdas que ya estan vacias
             */
            if (board[fila][columna] != 0) {
                board[fila][columna] = 0;
                quitados++;
            }
        }
    }
    /**
     * Metodo publico que permite a otros componentes (como el controlador) obtener el tablero actual
     */
    public int[][] getBoard() {
        return board;
    }
}