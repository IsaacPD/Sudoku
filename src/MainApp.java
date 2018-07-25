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
        Scene scene = new Scene(control);
        primaryStage.setScene(scene);

        control.prefWidthProperty().bind(scene.widthProperty());
        control.prefHeightProperty().bind(scene.heightProperty());

        primaryStage.getScene().getStylesheets().add(getClass().getResource("/Style/style.css").toExternalForm());
        primaryStage.setWidth(380);
        primaryStage.setHeight(500);
        primaryStage.show();
        primaryStage.setTitle("Sudoku");
    }
}
