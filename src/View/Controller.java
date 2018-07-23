package View;

import Sudoku.Cell;
import javafx.geometry.Insets;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Controller extends VBox {
	ToolBar toolBar;
	BorderPane borderPane;
	Menu menu;
	GameBoard gameBoard;

	public Controller() {
		toolBar = new ToolBar();
		menu = new Menu(this);
		gameBoard = new GameBoard(this);
		borderPane = new BorderPane();

		borderPane.setCenter(menu);
		borderPane.setPadding(new Insets(10));

		this.setPrefHeight(480);
		this.setPrefWidth(640);
		this.getChildren().add(toolBar);
		this.getChildren().add(borderPane);
	}

	public void startGame() {
		borderPane.setCenter(gameBoard);
	}

	public void highlight(Cell cell, boolean clicked) {
		if (clicked) {
			if (gameBoard.selected != null)
				gameBoard.selected.setStyle("-fx-border-color: black; -fx-border-width: 1;-fx-background-color: white");
			gameBoard.selected = cell;
			gameBoard.setOptions(cell);
		}
		cell.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: #fff4a8;");
	}

	public void unhighlight(Cell cell) {
		if (gameBoard.selected != cell)
			cell.setStyle("-fx-border-color: black; -fx-border-width: 1;-fx-background-color: white");
	}
}
