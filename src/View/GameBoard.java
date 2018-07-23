package View;

import Sudoku.Box;
import Sudoku.Cell;
import Sudoku.Game;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GameBoard extends BorderPane {
	GridPane grid;
	FlowPane options;
	Box[][] board;
	Game game;
	Controller control;

	public Cell selected;

	public GameBoard(Controller c) {
		this.control = c;

		board = new Box[3][3];
		game = new Game();
		grid = new GridPane();
		options = new FlowPane();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = new Box(game.board, i, j, control);
				grid.add(board[i][j], i, j);
			}
		}

		for (int i = 1; i <= 9; i++) {
			Text t = new Text("" + i);
			t.setOnMouseClicked(e -> {
				int ni = Integer.parseInt(t.getText());
				game.update(selected.row, selected.column, ni);
				game.board[selected.row][selected.column].set(ni);
				setOptions(selected);
				if (game.win())
					System.out.println("You Win");
			});
			t.setFont(Font.font(20));
			options.getChildren().add(t);
		}

		setCenter(grid);
		setBottom(options);
		configStyle();
	}

	private void configStyle() {
	}

	public void setOptions(Cell cell) {
		for (Node n : options.getChildren()) n.setVisible(false);

		ArrayList<Integer> nums = game.getCell(cell.row, cell.column);
		for (int n : nums)
			options.getChildren().get(n - 1).setVisible(true);
	}
}
