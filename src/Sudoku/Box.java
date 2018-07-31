package Sudoku;

import View.Controller;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class Box extends GridPane {
	public Cell[][] cells;

	public Box(SimpleIntegerProperty[][] board, int row, int col, Controller control) {
		cells = new Cell[3][3];

		setPrefWidth(40);
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++) {
				int r = row * 3 + i;
				int c = col * 3 + j;
				cells[i][j] = new Cell(r, c, board[r][c], control);
				add(cells[i][j], i, j);
			}
		}
		setStyle("-fx-border-color: black;");
		setAlignment(Pos.CENTER);
	}
}
