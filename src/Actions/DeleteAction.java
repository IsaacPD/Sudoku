package Actions;

import Sudoku.Cell;
import View.Controller;
import View.GameBoard;

public class DeleteAction implements Action {
    GameBoard gameBoard;
    Cell cell;
    Controller controller;
    int val;
    public DeleteAction(GameBoard gameBoard, Cell cell, Controller controller){
        this.gameBoard = gameBoard;
        this.cell = cell;
        this.controller = controller;
        this.val = cell.getNum();
    }

    @Override
    public void doit() {
        gameBoard.game.delete(cell.row, cell.column);
        controller.highlight(cell, true);
    }

    @Override
    public void undo() {
        gameBoard.update(cell.row, cell.column, val);
    }
}
