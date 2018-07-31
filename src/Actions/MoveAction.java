package Actions;

import View.GameBoard;

public class MoveAction implements Action{
    private GameBoard board;
    private int row, col, num, pnum;

    public MoveAction(GameBoard gameBoard, int ni) {
        this.board = gameBoard;
        this.num = ni;
        this.row = gameBoard.selected.row;
        this.col = gameBoard.selected.column;
        this.pnum = gameBoard.selected.getNum();
    }

    @Override
    public void doit() {
        board.update(row, col, num);
        int temp = num;
        num = pnum;
        pnum = temp;
    }

    @Override
    public void undo() {
        doit();
    }
}
