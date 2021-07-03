
import java.awt.Font;

public class SudokuSolve {

	private static final int X = 4;
	private static final int Y = 4;
	private static final int D = X * Y;
	private static int[][] board = new int[D][D];
	private static boolean[][] empty = new boolean[D][D];
	private static String LINE;
	// do vis stuff later
	private static final int SIZE = 180;
	private static int[][][] boardPos = new int[9][9][2];
	private static final int DISPLAYDELAY = 0;
	private static final int GREENWAIT = 0;

	public static void main(String[] args) {
		
		final boolean textMode = true;
		String filename = "sudoku2.txt";
		populateLINE();
		populateBoard(filename);
		boardPrintNice();
		Stopwatch sw = new Stopwatch();
		for (int i = 0; i < 10000; i++) {
			validBlocks();
		}
		// StdOut.println("validRowsCols(): " + validRowsCols());
		// StdOut.println("validBlock(0, 0): " + validBlock(0, 0));
		// solve();
		// boardPrintNice();
		// if (textMode) {
		// boardPrintNice();
		// StdOut.println();
		// solve();
		// boardPrintNice();
		// } else {
		// StdDraw.enableDoubleBuffering();
		// StdDraw.setFont(new Font("Arial", Font.BOLD, 24));
		// drawGrid();
		// populateBoardPos();
		// visBoard();
		// boardPrintNice();
		// StdOut.println("\n\n");
		// solveVis();
		// boardPrintNice();
		// visBoard();
		// }
		// StdOut.println("Solved");
		StdOut.println(sw.elapsedTime());
	}

	public static void populateLINE() {
		LINE = "";
		for (int i = 0; i < (D + Y - 1); i++) {
			LINE += "---";
		}
	}

	public static void drawGrid() {
		StdDraw.setXscale(0, SIZE);
		StdDraw.setYscale(0, SIZE);
		StdDraw.setPenColor(StdDraw.BLACK);
		int s = SIZE / 9;
		StdDraw.setPenRadius(0.004);
		StdDraw.line(0, 180, 180, 180);
		for (int i = 0; i < 9; i++) {
			StdDraw.setPenRadius(0.004);
			if (i % 3 == 0) {
				StdDraw.setPenRadius(0.008);
			}
			StdDraw.line(0, i * s, SIZE, i * s);
			StdDraw.line(i * s, 0, i * s, SIZE);
		}
		StdDraw.show();
	}

	public static void highlightSquare(int row, int col) {
		drawGrid();
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.square(boardPos[row][col][0], boardPos[row][col][1], SIZE / 18.0);
	}

	public static void highlightSquareG(int row, int col) {
		drawGrid();
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.square(boardPos[row][col][0], boardPos[row][col][1], SIZE / 18.0);
		StdDraw.show();
		StdDraw.pause(GREENWAIT);
	}

	// change to 4x4
	public static void populateBoardPos() {
		int s = SIZE / 9;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				boardPos[i][j][1] = SIZE - (i + 1) * s + s / 2;
				boardPos[i][j][0] = (j + 1) * s - s / 2;
			}
		}
	}

	public static void visBoard() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				StdDraw.setPenColor(StdDraw.WHITE);
				StdDraw.filledSquare(boardPos[i][j][0], boardPos[i][j][1], SIZE / 18 - 4);
				if (board[i][j] != 0) {
					if (empty[i][j]) {
						StdDraw.setPenColor(StdDraw.GRAY);
					} else {
						StdDraw.setPenColor(StdDraw.BLACK);
					}
					StdDraw.text(boardPos[i][j][0], boardPos[i][j][1], "" + board[i][j]);
				}
			}
		}
		StdDraw.show();
		StdDraw.pause(DISPLAYDELAY);
	}

	// changed to 4x4
	public static boolean complete() {
		for (int i = 0; i < D; i++)
			for (int j = 0; j < D; j++)
				if (board[i][j] == 0)
					return false;

		return validBoard();
	}

	public static void solveVis() {
		int nRow = 9;
		int nCol = 9;
		for (int i = 0; i < 9 && nRow == 9; i++) {
			for (int j = 0; j < 9 && nRow == 9; j++) {
				if (board[i][j] == 0) {
					nRow = i;
					nCol = j;
				}
			}
		}
		if (nCol == 9) {
			return;
		}
		for (int i = 1; i < 10; i++) {
			board[nRow][nCol] = i;
			highlightSquare(nRow, nCol);
			visBoard();
			if (validBoard()) {
				highlightSquareG(nRow, nCol);
				solveVis();
			}
			if (complete()) {
				drawGrid();
				return;
			}
			board[nRow][nCol] = 0;
		}
	}

	// changed to 4x4
	public static void solve() {
		int nRow = D;
		int nCol = D;
		for (int i = 0; i < D && nRow == D; i++)
			for (int j = 0; j < D && nRow == D; j++)
				if (board[i][j] == 0) {
					nRow = i;
					nCol = j;
					// StdOut.println((nRow * X + nCol) * 100 / (D * D));
					break;
				}

		if (nCol == D)
			return;

		for (int i = 1; i <= D; i++) {
			board[nRow][nCol] = i;
			if (validBoard())
				solve();

			if (complete())
				return;

			board[nRow][nCol] = 0;
		}
	}

	public static boolean validBoard() {
		return validRowsCols() && validBlocks();
	}

	// changed to 4x4
	public static boolean validBlocks() {
		for (int i = 0; i < Y; i++)
			for (int j = 0; j < X; j++)
				if (!validBlock(i * Y, j * X))
					return false;

		return true;
	}

	// TODO improve
	public static boolean validBlock(int sRow, int sCol) {
		boolean ls[] = new boolean[D + 1];
		for (int i = sRow; i < sRow + Y; i++)
			for (int j = sCol; j < sCol + X; j++) {
				int num = board[i][j];
					if (ls[num])
						return false;

					ls[num] = board[i][j] != 0;
			}

		return true;
	}

	// changed to 4x4
	public static boolean validRowsCols() {
		for (int i = 0; i < D; i++) {
			boolean[] numsRow = new boolean[D + 1];
			boolean[] numsCol = new boolean[D + 1];
			for (int j = 0; j < D; j++) {
				int numR = board[i][j];
				if (numsRow[numR])
					return false;

				numsRow[numR] = numR != 0;

				int numC = board[j][i];
				if (numsCol[numC])
					return false;

				numsCol[numC] = numC != 0;
			}
		}
		return true;
	}

	// changed to 4x4
	public static void populateBoard(String filename) {
		In in = new In(filename);
		for (int i = 0; i < D; i++) {
			for (int j = 0; j < D; j++) {
				board[i][j] = in.readInt();
				empty[i][j] = board[i][j] == 0;
			}
		}
	}

	// changed to 4x4
	public static void boardPrint() {
		String str = "";
		for (int i = 0; i < D; i++) {
			for (int j = 0; j < D; j++) {
				str += String.format("%3d", board[i][j]);
			}
			str += "\n";
		}
		StdOut.println(str);
	}

	// changed to 4x4
	public static void boardPrintNice() {
		String str = "";
		for (int i = 0; i < D; i++) {
			if (i % X == 0) {
				str += LINE + "\n";
			}
			for (int j = 0; j < D; j++) {
				if (j % Y == 0) {
					str += "| ";
				}
				str += String.format("%3d", board[i][j]);
			}
			str += "|\n";
		}
		str += LINE + "\n";
		StdOut.print(str);
	}

}
