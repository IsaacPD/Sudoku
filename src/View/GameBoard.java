package View;

import Actions.MoveAction;
import Style.StyleProperties;
import Sudoku.Box;
import Sudoku.Cell;
import Sudoku.Game;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.util.ArrayList;

public class GameBoard extends BorderPane {
    GridPane grid;
    GridPane options;
    Box[][] board;
    VBox newGamePop;
    Game game;
    Controller control;
    Popup pop;

    public Cell selected;

    public GameBoard(Controller c) {
        this.control = c;

        board = new Box[3][3];
        game = new Game();
        grid = new GridPane();
        options = new GridPane();
        newGamePop = new VBox();
        pop = new Popup();

        Button button = new Button("Play Again?");
        newGamePop.getChildren().addAll(new Text("You Win!"), button);
        newGamePop.setStyle("-fx-background-color: pink");
        pop.getContent().addAll(newGamePop);
        button.setOnAction(e -> {
            c.newGame();
            pop.hide();
        });

        ColumnConstraints cc = new ColumnConstraints();
        cc.setFillWidth(true);
        cc.setPercentWidth(33.33);
        grid.getColumnConstraints().addAll(cc, cc, cc);

        RowConstraints rc = new RowConstraints();
        rc.setFillHeight(true);
        rc.setPercentHeight(33.33);
        grid.getRowConstraints().addAll(rc, rc, rc);


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = new Box(game.board, i, j, control);
                board[i][j].getColumnConstraints().addAll(cc, cc, cc);
                board[i][j].getRowConstraints().addAll(rc, rc, rc);
                grid.add(board[i][j], i, j);
            }
        }

        ColumnConstraints oc = new ColumnConstraints();
        oc.setFillWidth(true);
        oc.setPercentWidth(11.11);

        RowConstraints roc = new RowConstraints();
        roc.setFillHeight(true);
        options.setAlignment(Pos.CENTER);
        SimpleDoubleProperty fontSize = new SimpleDoubleProperty();
        fontSize.bind(control.widthProperty().multiply(.052));

        for (int i = 1; i <= 9; i++) {
            Text t = new Text("" + i);
            t.getStyleClass().add(StyleProperties.OPTIONTEXT);
            t.setVisible(false);
            t.setOnMouseEntered(e -> t.setFill(Color.rgb(206, 125, 190)));
            t.setOnMouseExited(e -> t.setFill(Color.BLACK));
            t.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));

            t.setOnMouseClicked(e -> {
                int ni = Integer.parseInt(t.getText());
                control.actions.add(new MoveAction(this, ni));
                if (game.win())
                    pop.show(this.getScene().getWindow());

            });
            t.setFont(Font.font(20));
            options.add(t, i - 1, 0);
            options.getColumnConstraints().add(oc);
        }
        options.getRowConstraints().add(roc);

        setMargin(options, new Insets(10, 0, 10, 0));
        setCenter(grid);
        setBottom(options);
        configStyle();
    }

    private void configStyle() {
        this.getStyleClass().add(StyleProperties.GAMEBOARD);
        options.getStyleClass().add(StyleProperties.FLOWPANE);

        this.prefWidthProperty().bind(control.widthProperty());
        grid.prefWidthProperty().bind(control.widthProperty());
    }

    public void update(int row, int column, int num) {
        game.update(row, column, num);
        game.board[row][column].set(num);
        setOptions(board[row / 3][column / 3].cells[row % 3][column % 3]);
    }

    public void setOptions(Cell cell) {
        for (Node n : options.getChildren()) n.setVisible(false);

        ArrayList<Integer> nums = game.getCell(cell.row, cell.column);
        for (int n : nums)
            options.getChildren().get(n - 1).setVisible(true);
    }

    public void loadCells(int[][] board) {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                this.board[i / 3][j / 3].cells[i % 3][j % 3].update(board[i][j]);
            }
    }

}
