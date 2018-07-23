package Sudoku;

import View.Controller;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Cell extends GridPane {
	SimpleIntegerProperty num;
	Text integer;
	public final int row, column;
	public final boolean set;

	public Cell(int row, int col, SimpleIntegerProperty num, Controller control) {
		this.row = row;
		this.column = col;
		this.num = num;

		this.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white");
		setPadding(new Insets(5, 12, 5, 12));

		this.integer = new Text();
		set = num.get() != 0;

		StringBinding binding = new StringBinding() {
			{
				super.bind(num);
			}

			@Override
			protected String computeValue() {
				return num.get() == 0 ? "  " : "" + num.get();
			}
		};

		integer.textProperty().bind(binding);
		this.getChildren().add(integer);
		integer.setTextAlignment(TextAlignment.CENTER);
		integer.setFont(Font.font(20));

		if (!set) {
			setOnMouseEntered(e -> {
				control.highlight(this, false);
			});
			setOnMouseExited(e -> {
				control.unhighlight(this);
			});
			setOnMouseClicked(e -> {
				control.highlight(this, true);
			});
		}
	}

	public boolean getSet() {
		return set;
	}
}
