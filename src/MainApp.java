import View.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
	private Controller control;

	public MainApp() {
		control = new Controller();
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setScene(new Scene(control));
		primaryStage.show();
		primaryStage.setTitle("Sudoku");
	}
}
