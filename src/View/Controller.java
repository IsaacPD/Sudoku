package View;

import Actions.Actions;
import Actions.DeleteAction;
import Style.StyleProperties;
import Sudoku.Cell;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.*;

public class Controller extends HBox {
    MenuBar menuBar;
    BorderPane borderPane;
    StartPage startPage;
    GameBoard gameBoard;
    Actions actions;

    public Controller() {
        menuBar = new MenuBar();
        startPage = new StartPage(this);
        gameBoard = new GameBoard(this);
        borderPane = new BorderPane();
        actions = new Actions();

        borderPane.setTop(menuBar);
        borderPane.setCenter(startPage);

        this.initMenuBar();
        this.setOnKeyPressed(this::keyPress);
        this.getChildren().add(borderPane);
        this.getStyleClass().add(StyleProperties.CONTROLLER);
    }

    private void initMenuBar() {
        Menu file = new Menu("_File");
        MenuItem newg = new MenuItem("_New Game");
        MenuItem load = new MenuItem("_Load");
        MenuItem save = new MenuItem("_Save");
        MenuItem exit = new MenuItem("_Exit");
        load.setOnAction(e -> loadGame());
        save.setOnAction(e -> saveGame());
        newg.setOnAction(e -> newGame());
        exit.setOnAction(e -> System.exit(0));

        Menu game = new Menu("_Game");
        MenuItem undo = new MenuItem("_Undo");
        MenuItem redo = new MenuItem("_Redo");
        undo.setOnAction(e -> actions.undo());
        redo.setOnAction(e -> actions.redo());

        file.getItems().addAll(newg, load, save, exit);
        game.getItems().addAll(undo, redo);
        menuBar.getMenus().addAll(file, game);
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
        actions.add(new DeleteAction(gameBoard, cell, this));
    }

    public void keyPress(KeyEvent e) {
        if (e.isControlDown()) {
            switch (e.getCode()) {
                case Z:
                    actions.undo();
                    break;
                case Y:
                    actions.redo();
                    break;
                case S:
                    saveGame();
                    break;
            }
        }
    }

    private void saveGame() {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("sudoku.sav"));
            os.writeObject(gameBoard.game.getIntBoard());
            os.writeObject(gameBoard.game.asIntGrid());
        } catch (IOException ignored) {
        }
    }

    public void loadGame() {
        try {
            ObjectInputStream oin = new ObjectInputStream(new FileInputStream("sudoku.sav"));
            int[][] board = (int[][]) oin.readObject();
            int[][] played = (int[][]) oin.readObject();
            gameBoard.game.setBoard(board);
            gameBoard.loadCells(board);

            gameBoard.game.setBoard(played);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void newGame() {
        gameBoard.game.generate();
        gameBoard.loadCells(gameBoard.game.getIntBoard());
    }
}
