package View;

import Style.StyleProperties;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class StartPage extends BorderPane {
	Button start, load, exit, options;
	GridPane buttons;
	Controller control;

	public StartPage(Controller c) {
		this.control = c;
		start = new Button("Start");
		load = new Button("Load");
		exit = new Button("Exit");
		options = new Button("Options");
		buttons = new GridPane();
		Text sudoku = new Text("SUDOKU");

		SimpleDoubleProperty fontSize = new SimpleDoubleProperty();
		fontSize.bind(control.widthProperty().multiply(.09));
		sudoku.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));

		setButtonStyle();
		setButtonAction();

		this.setCenter(sudoku);
		this.setBottom(buttons);
		this.getStyleClass().add(StyleProperties.STARTPAGE);
	}

	private void setButtonAction() {
		start.setOnAction(event -> control.startGame());
		exit.setOnAction(event -> System.exit(0));
		load.setOnAction(event -> control.loadGame());
	}

	private void setButtonStyle() {
		start.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		load.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		exit.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		options.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		buttons.add(start, 0, 0);
		buttons.add(load, 1, 0);
		buttons.add(options, 0, 1);
		buttons.add(exit, 1, 1);

		buttons.setAlignment(Pos.CENTER);
		buttons.setHgap(5);
		buttons.setVgap(5);

	}
}
