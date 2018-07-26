package View;

import Style.StyleProperties;
import Sudoku.Cell;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Controller extends HBox {
	ToolBar toolBar;
	BorderPane borderPane;
	Menu menu;
	GameBoard gameBoard;

	public Controller() {
		toolBar = new ToolBar();
		menu = new Menu(this);
		gameBoard = new GameBoard(this);
		borderPane = new BorderPane();

		borderPane.setTop(toolBar);
		borderPane.setCenter(menu);

		this.getChildren().add(borderPane);
		this.getStyleClass().add(StyleProperties.CONTROLLER);
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

	public void delete(Cell cell) {
		gameBoard.game.delete(cell.row, cell.column);
		highlight(cell, true);
	}
}
