package Sudoku;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.*;

public class Game {
	private static final int DEFAULT = 9;
	private static final int SQRDEFAULT = 3;
	private static final int MEDIUM = 54;
	private static final Integer[] ONETONINE = {1, 2, 3, 4, 5, 6, 7, 8, 9};

	public SimpleIntegerProperty[][] board;
	private ArrayList<HashSet<Integer>> rows, cols, squares;

	public Game() {
		rows = new ArrayList<>(DEFAULT);
		cols = new ArrayList<>(DEFAULT);
		squares = new ArrayList<>(DEFAULT);

		for (int i = 0; i < DEFAULT; i++) {
			rows.add(new HashSet<>());
			cols.add(new HashSet<>());
			squares.add(new HashSet<>());
		}

		board = new SimpleIntegerProperty[DEFAULT][DEFAULT];
		for (int i = 0; i < DEFAULT; i++) {
			for (int j = 0; j < DEFAULT; j++)
				board[i][j] = new SimpleIntegerProperty();
		}
		generate();
	}

	public void generate() {
		for (int i = 0; i < DEFAULT; i += SQRDEFAULT) {
			fillDiag(i);
			squares.get(i / 3 * 3 + i / 3).addAll(Arrays.asList(ONETONINE));
		}

		fillRest(0, 0);

		removeRandom(MEDIUM);
	}

	private void removeRandom(int h) {
		for (int i = 0; i < h; i++) {
			int row, col;
			do {
				row = (int) (9 * Math.random());
				col = (int) (9 * Math.random());
			} while (board[row][col].get() == 0);
			int old = board[row][col].get();
			rows.get(row).remove(old);
			cols.get(col).remove(old);
			squares.get(row / 3 * 3 + col / 3).remove(old);
			board[row][col].set(0);
		}
	}

	private boolean fillRest(int row, int col) {
		if (row < DEFAULT - 1 && col >= DEFAULT) {
			col = 0;
			row++;
		}

		//Skip Diagonals
		for (int i = 3; i <= 9; i += 3) {
			if (i == 9) {
				if (col == 6) {
					row++;
					col = 0;
					if (row == i)
						return true;
					break;
				}
			}
			if (row < i) {
				if (col == i - 3)
					col = i;
				break;
			}
		}

		for (int num = 1; num <= 9; num++) {
			ArrayList<Integer> nums = new ArrayList<>(Arrays.asList(ONETONINE));

			nums.removeAll(rows.get(row));
			nums.removeAll(cols.get(col));
			nums.removeAll(squares.get(row / 3 * 3 + col / 3));

			if (nums.contains(num)) {
				board[row][col].set(num);
				rows.get(row).add(num);
				cols.get(col).add(num);
				squares.get(row / 3 * 3 + col / 3).add(num);
				if (fillRest(row, col + 1)) {
					return true;
				}
				board[row][col].set(0);
				rows.get(row).remove(num);
				cols.get(col).remove(num);
				squares.get(row / 3 * 3 + col / 3).remove(num);
			}
		}
		return false;
	}

	private void fillDiag(int row) {
		ArrayList<Integer> nums = new ArrayList<>(Arrays.asList(ONETONINE));
		for (int i = 0; i < SQRDEFAULT; i++) {
			for (int j = 0; j < SQRDEFAULT; j++) {
				int n = nums.remove((int) (nums.size() * Math.random()));
				board[row + i][row + j].set(n);
				rows.get(row + i).add(n);
				cols.get(row + j).add(n);
			}
		}
	}

	public ArrayList<Integer> getCell(int row, int col) {
		ArrayList<Integer> nums = new ArrayList<>(Arrays.asList(ONETONINE));

		nums.removeAll(rows.get(row));
		nums.removeAll(cols.get(col));
		nums.removeAll(squares.get(row / 3 * 3 + col / 3));
		return nums;
	}

	public String toString() {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < DEFAULT; i++) {
			for (int j = 0; j < DEFAULT; j++) {
				b.append((board[i][j] == null ? 0 : board[i][j]) + " ");
			}
			b.append("\n");
		}
		return b.toString();
	}

	public static void main(String... args) {
		Game g = new Game();
		System.out.println(g);
	}

	public void update(int row, int column, int newNum) {
		int old = board[row][column].get();
		rows.get(row).remove(old);
		rows.get(row).add(newNum);
		cols.get(column).remove(old);
		cols.get(column).add(newNum);
		squares.get(row / 3 * 3 + column / 3).remove(old);
		squares.get(row / 3 * 3 + column / 3).add(newNum);
	}

	public boolean win() {
		boolean win = true;
		for (HashSet<Integer> s : rows)
			win &= s.size() == 9;
		for (HashSet<Integer> s : cols)
			win &= s.size() == 9;
		for (HashSet<Integer> s : squares)
			win &= s.size() == 9;
		return win;
	}
}
