package Sudoku;

import Style.StyleProperties;
import View.Controller;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
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

		this.getStyleClass().add(StyleProperties.CELL);
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

		integer.setFont(Font.font(20));
		SimpleDoubleProperty fontSize = new SimpleDoubleProperty();
		/*fontSize.bind(control.widthProperty().multiply(.052));
		integer.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(),";"));*/

		this.setAlignment(Pos.CENTER);
		this.getChildren().add(integer);

		if (!set) {
			setOnMouseEntered(e -> {
				control.highlight(this, false);
			});
			setOnMouseExited(e -> {
				control.unhighlight(this);
			});
			setOnMouseClicked(e -> {
				if (e.getButton() == MouseButton.SECONDARY)
					control.delete(this);
				else control.highlight(this, true);
			});
		}
	}

	public boolean getSet() {
		return set;
	}
}
