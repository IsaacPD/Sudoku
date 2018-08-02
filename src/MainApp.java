import View.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
        scene.setOnKeyPressed(control::keyPress);

        primaryStage.getIcons().addAll(new Image(getClass().getResource("/192.png").toString()));
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/Style/style.css").toExternalForm());
        primaryStage.setMinWidth(380);
        primaryStage.setMinHeight(500);
        primaryStage.show();
        primaryStage.setTitle("Sudoku");
    }
}
