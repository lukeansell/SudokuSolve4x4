
import java.awt.Font;

public class SudokuSolve {

	private static Stopwatch sw = new Stopwatch();
	private static final int X = 4;
	private static final int Y = 4;
	private static final int D = X * Y;
	private static int[][] board = new int[D][D];
	private static boolean[][] empty = new boolean[D][D];
	private static String LINE = "";
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
		// Stopwatch sw = new Stopwatch();
		getPossibleValuesAll();
		// int[] values = getPossibleValues(0, 2);
		// for (int i = 0; i < values.length; i++)
		// StdOut.print(values[i] + " ");
		// StdOut.println();
		// for (int i = 0; i < 10000; i++) {
		// validBlocks();
		// }
		// StdOut.println("validRowsCols(): " + validRowsCols());
		// StdOut.println("validBlock(0, 0): " + validBlock(0, 0));
		// solveBT();
		// boardPrintNice();
		// if (textMode) {
		// boardPrintNice();
		// StdOut.println();
		solveBTAdv();
		boardPrintNice();
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
		for (int i = 0; i < (D + Y - 1); i++)
			LINE += "---";
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
			drawGrid();
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
				return;
			}
			board[nRow][nCol] = 0;
		}
	}

	// changed to 4x4
	// TODO improve
	public static void solveBT() {
		int nRow = D;
		int nCol = D;
		for (int i = 0; i < D && nRow == D; i++)
			for (int j = 0; j < D && nRow == D; j++)
				if (board[i][j] == 0) {
					nRow = i;
					nCol = j;
					break;
				}

		if (nCol == D)
			return;

		for (int i = 1; i <= D; i++) {
			board[nRow][nCol] = i;
			if (validBoard())
				solveBT();

			if (complete())
				return;

			board[nRow][nCol] = 0;
		}
	}

	public static void solveBTAdv() {
		if (complete())
			return;

		int nRow = D;
		int nCol = D;
		int min = 17;
		// find lowest num of values
		int allVals[][][] = getPossibleValuesAll();
		// int total = 0;
		// int num = 0;
		for (int i = 0; i < D; i++)
			for (int j = 0; j < D; j++) {
				// total += allVals[i][j].length;
				// num += board[i][j] == 0 ? 1 : 0;
				if (board[i][j] == 0 && allVals[i][j].length < min) {
					nRow = i;
					nCol = j;
					min = allVals[i][j].length;
				}
				if (board[i][j] == 0 && allVals[i][j].length == 0)
					return;
			}
		// StdOut.println(num + " " + total);
		// if (num == 0) {
			// StdOut.println("HERE");
			// return;
		// }
		if (min == 17)
			return;

		// StdOut.println("row; " + nRow + " col: " + nCol);
		// allVals[nRow][nCol].length);
		int values[] = allVals[nRow][nCol];
		for (int i = 0; i < values.length; i++) {
			// if ((nRow == 0 && nCol == 2) || (nRow == 8 && nCol == 1))
			// 	StdOut.println("row; " + nRow + " col: " + nCol + " trying: " + values[i]);
			board[nRow][nCol] = values[i];
			if (validBoard())
				solveBTAdv();

			if (complete())
				return;

			board[nRow][nCol] = 0;
		}

		// StdOut.println();
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
		sRow = sRow / X * X;
		sCol = sCol / Y * Y;
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

	public static boolean validRowCol(int row, int col) {
		boolean[] numsR = new boolean[D + 1];
		boolean[] numsC = new boolean[D + 1];
		for (int i = 0; i < D; i++) {
			int numR = board[row][i];
			if (numsR[numR])
				return false;
			numsR[numR] = numR != 0;

			int numC = board[i][col];
			if (numsC[numC])
				return false;
			numsC[numC] = numC != 0;
		}

		return true;
	}

	public static boolean validRow(int row) {
		boolean[] nums = new boolean[D + 1];
		for (int i = 0; i < D; i++) {
			int num = board[row][i];
			if (nums[num])
				return false;
			nums[num] = num != 0;
		}

		return true;
	}

	public static boolean validCol(int col) {
		boolean[] nums = new boolean[D + 1];
		for (int i = 0; i < D; i++) {
			int num = board[i][col];
			if (nums[num])
				return false;
			nums[num] = num != 0;
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
			if (i % X == 0)
				str += LINE + "\n";

			for (int j = 0; j < D; j++) {
				if (j % Y == 0)
					str += "| ";

				str += String.format("%3d", board[i][j]);
			}
			str += "|\n";
		}
		str += LINE + "\n";
		StdOut.print(str);
	}

	public static int[][][] getPossibleValuesAll() {
		int[][][] temp = new int[D][D][16];
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board.length; j++)
				temp[i][j] = getPossibleValues(i, j);

		// for (int i = 0; i < board.length; i++)
		// for (int j = 0; j < board.length; j++) {
		// StdOut.println("row: " + i + " col: " + j + "\t" + temp[i][j].length);
		// for (int k = 0; k < temp[i][j].length; k++)
		// StdOut.print(temp[i][j][k] + " ");
		// StdOut.println();
		// }
		return temp;
	}

	public static int[] getPossibleValues(int row, int col) {
		if (board[row][col] != 0) {
			int t[] = {};
			return t;
		}
		boolean[] nums = new boolean[16];
		for (int i = 1; i <= D; i++) {
			board[row][col] = i;
			nums[i - 1] = validRowCol(row, col) && validBlock(row, col);
		}
		board[row][col] = 0;

		// for (int i = 0; i < nums.length; i++)
		// if (nums[i])
		// StdOut.println(i + 1 + ": " + nums[i]);

		int len = 0;
		for (int i = 0; i < nums.length; i++)
			len += nums[i] ? 1 : 0;

		int[] temp = new int[len];
		int count = 0;
		for (int i = 0; i < nums.length; i++)
			if (nums[i]) {
				temp[count] = i + 1;
				count++;
			}
		return temp;
	}

}
