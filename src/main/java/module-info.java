module project.sudoku {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens project.sudoku to javafx.fxml;
    opens project.sudoku.controllers to javafx.fxml;
    exports project.sudoku;
}