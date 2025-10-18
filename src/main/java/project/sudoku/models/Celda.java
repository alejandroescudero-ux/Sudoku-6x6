package project.sudoku.models;

/**
 * Represents a single cell within the Sudoku grid.
 * Each cell has a row, a column, and a value (from 1 to 6, or 0 if empty).
 */

public class Celda {
    /** The row index of the cell (0–5). */
    private int fila;
    /** The column index of the cell (0–5). */
    private int columna;
    /** The numeric value of the cell (1–6). A value of 0 represents an empty cell. */
    private int valor;

    /**
     * Constructs a new {@code Celda} with the given position and value.
     *
     * @param fila    the row index of the cell
     * @param columna the column index of the cell
     * @param valor   the numeric value stored in the cell (0 if empty)
     */

    public Celda(int fila, int columna, int valor) {
        this.fila = fila;
        this.columna = columna;
        this.valor = valor;
    }

    /**
     * Returns the row index of this cell.
     *
     * @return the row index (0–5)
     */

    public int getFila() {
        return fila;
    }

    /**
     * Returns the column index of this cell.
     *
     * @return the column index (0–5)
     */

    public int getColumna() {
        return columna;
    }

    /**
     * Returns the current value stored in this cell.
     *
     * @return the cell value (1–6), or 0 if empty
     */

    public int getValor() {
        return valor;
    }

    /**
     * Sets a new value for this cell.
     *
     * @param valor the new numeric value to assign (1–6, or 0 for empty)
     */

    public void setValor(int valor) {
        this.valor = valor;
    }
}