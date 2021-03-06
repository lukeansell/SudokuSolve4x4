
public class SudokuSolve {

	private static Stopwatch sw = new Stopwatch();
	private static final int X = 4;
	private static final int Y = 4;
	private static final int D = X * Y;
	// private static int[][] board = new int[D][D];1
	// private static boolean[][] empty = new boolean[D][D];
	private static String LINE = "";

	// private static int cellsFilled = 0;
	// private static int cellsStart = 0;
	// private static boolean complete = false;
	// private static boolean filled = false;
	private static final double SIZE = 180.0;
	// private static double[][][] boardPos = new double[D][D][2];
	// private static final int DISPLAYDELAY = 0;
	// private static final int GREENWAIT = 0;

	// private static double tFill = 0.0;
	// private static double tMinCell = 0.0;
	// private static double lTime = 0.0;

	public static void main(String[] args) {
		SudokuSolver4x4 ss = new SudokuSolver4x4("sudoku4.txt");
		ss.boardPrintNice();
		ss.startSolve();
		// for (int i = 0; i < 1000; i++)
		// ss.initOps();
		// ss.solve();
		ss.boardPrintNice();
		// System.out.println("tries: " + ss.tries + " (111708)");
		// System.out.println("valid board: " + ss.validBoard());
		// String filename = "sudoku2.txt";
		// populateLINE();
		// populateBoard(filename);
		// boardPrintNice();
		// visInit();
		// populateBoardPos();
		// solveBTAdv2();
		// visBoard();
		// solveBTAdv2Vis();
		// boardPrintNice();

		StdOut.println(sw.elapsedTime());
		// System.out.println("tMinCell:\t" + ss.tMinCell);
		// System.out.println("tComplete:\t" + ss.tComplete);
		// System.out.println("tL1:\t\t" + ss.tL1);
		// System.out.println("tL2:\t\t" + ss.tL2);
		// System.out.println("tL3:\t\t" + ss.tL3);
		// StdOut.println("complete: " + complete());
		// System.out.println("tFill: " + tFill);
		// System.out.println("tMinCell: " + tMinCell);
	}

	public static void populateLINE() {
		for (int i = 0; i < (D + Y - 1); i++)
			LINE += "---";
	}

	public static void visInit() {
		StdDraw.pause(50);
		StdDraw.setCanvasSize(650, 650);
		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(0, SIZE);
		drawGrid();
	}

	public static void drawGrid() {
		StdDraw.setPenColor(StdDraw.BLACK);
		double s = SIZE * 1.0 / D;
		StdDraw.setPenRadius(0.004);
		StdDraw.line(0, 180, 180, 180);
		for (int i = 0; i < D; i++) {
			StdDraw.setPenRadius(0.004);
			if (i % X == 0) {
				StdDraw.setPenRadius(0.008);
			}
			StdDraw.line(0, i * s, SIZE, i * s);
			StdDraw.line(i * s, 0, i * s, SIZE);
		}
		StdDraw.show();
	}

	// public static void highlightSquare(int row, int col) {
	// drawGrid();
	// StdDraw.setPenColor(StdDraw.RED);
	// StdDraw.square(boardPos[row][col][0], boardPos[row][col][1], SIZE / (D *
	// 2.0));
	// }

	// public static void highlightSquareG(int row, int col) {
	// drawGrid();
	// StdDraw.setPenColor(StdDraw.GREEN);
	// StdDraw.square(boardPos[row][col][0], boardPos[row][col][1], SIZE / (D *
	// 2.0));
	// StdDraw.show();
	// StdDraw.pause(GREENWAIT);
	// }

	// public static void populateBoardPos() {
	// double s = SIZE * 1.0 / D;
	// for (int i = 0; i < D; i++)
	// for (int j = 0; j < D; j++) {
	// boardPos[i][j][1] = SIZE - (i + 1) * s + s / 2;
	// boardPos[i][j][0] = (j + 1) * s - s / 2;
	// }
	// }

	// public static void visBoard() {
	// for (int i = 0; i < D; i++)
	// for (int j = 0; j < D; j++) {
	// StdDraw.setPenColor(StdDraw.WHITE);
	// StdDraw.filledSquare(boardPos[i][j][0], boardPos[i][j][1], SIZE / (D * 2.0) -
	// 1);
	// if (board[i][j] != 0) {
	// if (empty[i][j])
	// StdDraw.setPenColor(StdDraw.GRAY);
	// else
	// StdDraw.setPenColor(StdDraw.BLACK);
	// StdDraw.text(boardPos[i][j][0], boardPos[i][j][1], "" + board[i][j]);
	// }
	// }

	// StdDraw.show();
	// StdDraw.pause(DISPLAYDELAY);
	// }

	// public static void solveVis() {
	// int nRow = 9;
	// int nCol = 9;
	// for (int i = 0; i < 9 && nRow == 9; i++) {
	// for (int j = 0; j < 9 && nRow == 9; j++) {
	// if (board[i][j] == 0) {
	// nRow = i;
	// nCol = j;
	// }
	// }
	// }
	// if (nCol == 9) {
	// drawGrid();
	// return;
	// }
	// for (int i = 1; i < 10; i++) {
	// board[nRow][nCol] = i;
	// highlightSquare(nRow, nCol);
	// visBoard();
	// if (validBoard()) {
	// highlightSquareG(nRow, nCol);
	// solveVis();
	// }
	// if (complete()) {
	// return;
	// }
	// board[nRow][nCol] = 0;
	// }
	// }

	// public static void solveBT() {
	// int nRow = D;
	// int nCol = D;
	// for (int i = 0; i < D && nRow == D; i++)
	// for (int j = 0; j < D && nRow == D; j++)
	// if (board[i][j] == 0) {
	// nRow = i;
	// nCol = j;
	// break;
	// }

	// if (nCol == D)
	// return;

	// for (int i = 1; i <= D; i++) {
	// board[nRow][nCol] = i;
	// if (validBoard())
	// solveBT();

	// if (complete())
	// return;

	// board[nRow][nCol] = 0;
	// }
	// }

	// public static void solveBTAdv() {
	// int nRow = D;
	// int nCol = D;
	// int min = 17;
	// int allVals[][][] = getPossibleValuesAll();
	// for (int i = 0; i < D; i++)
	// for (int j = 0; j < D; j++) {
	// if (board[i][j] == 0)
	// if (allVals[i][j].length < min) {
	// nRow = i;
	// nCol = j;
	// min = allVals[i][j].length;
	// } else if (allVals[i][j].length == 0)
	// return;
	// }
	// if (nRow == D)
	// return;

	// int values[] = allVals[nRow][nCol];
	// for (int i = 0; i < values.length; i++) {
	// board[nRow][nCol] = values[i];
	// solveBTAdv();

	// if (complete || complete())
	// return;

	// }
	// board[nRow][nCol] = 0;

	// }

	// public static void solveBTAdv2() {
	// if (filled())
	// return;
	// lTime = sw.elapsedTime();
	// int[][] cell = getMinCell();
	// tMinCell += sw.elapsedTime() - lTime;
	// if (cell.length == 1)
	// return;

	// int values[] = cell[1];
	// for (int i = 0; i < values.length; i++) {
	// board[cell[0][0]][cell[0][1]] = values[i];

	// solveBTAdv2();

	// if (filled())
	// return;
	// }
	// board[cell[0][0]][cell[0][1]] = 0;
	// }

	// public static void solveBTAdv2Vis() {
	// if (filled())
	// return;
	// int[][] cell = getMinCell();
	// if (cell.length == 1)
	// return;

	// int values[] = cell[1];
	// for (int i = 0; i < values.length; i++) {
	// board[cell[0][0]][cell[0][1]] = values[i];
	// visBoard();
	// cellsFilled++;
	// StdOut.printf("%f%%\n", 100.0 * cellsFilled / cellsStart);
	// solveBTAdv2Vis();

	// if (filled())
	// return;
	// cellsFilled--;
	// }
	// board[cell[0][0]][cell[0][1]] = 0;
	// }

	// public static boolean validBoard() {
	// return validRowsCols() && validBlocks();
	// }

	// public static boolean validBlocks() {
	// for (int i = 0; i < Y; i++)
	// for (int j = 0; j < X; j++)
	// if (!validBlock(i * Y, j * X))
	// return false;

	// return true;
	// }

	// public static boolean validBlock(int sRow, int sCol) {
	// sRow = sRow / X * X;
	// sCol = sCol / Y * Y;
	// boolean ls[] = new boolean[D + 1];
	// for (int i = sRow; i < sRow + Y; i++)
	// for (int j = sCol; j < sCol + X; j++) {
	// int num = board[i][j];
	// if (ls[num])
	// return false;

	// ls[num] = board[i][j] != 0;
	// }

	// return true;
	// }

	// public static boolean validRowsCols() {
	// for (int i = 0; i < D; i++) {
	// boolean[] numsRow = new boolean[D + 1];
	// boolean[] numsCol = new boolean[D + 1];
	// for (int j = 0; j < D; j++) {
	// int numR = board[i][j];
	// if (numsRow[numR])
	// return false;

	// numsRow[numR] = numR != 0;

	// int numC = board[j][i];
	// if (numsCol[numC])
	// return false;

	// numsCol[numC] = numC != 0;
	// }
	// }
	// return true;
	// }

	// public static boolean validRowCol(int row, int col) {
	// boolean[] numsR = new boolean[D + 1];
	// boolean[] numsC = new boolean[D + 1];
	// for (int i = 0; i < D; i++) {
	// int numR = board[row][i];
	// if (numsR[numR])
	// return false;
	// numsR[numR] = numR != 0;

	// int numC = board[i][col];
	// if (numsC[numC])
	// return false;
	// numsC[numC] = numC != 0;
	// }

	// return true;
	// }

	// public static boolean validRow(int row) {
	// boolean[] nums = new boolean[D + 1];
	// for (int i = 0; i < D; i++) {
	// int num = board[row][i];
	// if (nums[num])
	// return false;
	// nums[num] = num != 0;
	// }

	// return true;
	// }

	// public static boolean validCol(int col) {
	// boolean[] nums = new boolean[D + 1];
	// for (int i = 0; i < D; i++) {
	// int num = board[i][col];
	// if (nums[num])
	// return false;
	// nums[num] = num != 0;
	// }
	// return true;
	// }

	// public static void populateBoard(String filename) {
	// In in = new In(filename);
	// for (int i = 0; i < D; i++)
	// for (int j = 0; j < D; j++) {
	// board[i][j] = in.readInt();
	// empty[i][j] = board[i][j] == 0;
	// if (empty[i][j])
	// cellsStart++;
	// }
	// }

	// public static void boardPrint() {
	// StringBuilder str = new StringBuilder();
	// for (int i = 0; i < D; i++) {
	// for (int j = 0; j < D; j++)
	// str.append(String.format("%3d", board[i][j]));
	// str.append("\n");
	// }
	// StdOut.println(str.toString());
	// }

	// public static void boardPrintNice() {
	// StringBuilder str = new StringBuilder();
	// for (int i = 0; i < D; i++) {
	// if (i % X == 0)
	// str.append(LINE + "\n");

	// for (int j = 0; j < D; j++) {
	// if (j % Y == 0)
	// str.append("| ");

	// str.append(String.format("%3d", board[i][j]));
	// }

	// str.append("|\n");
	// }
	// str.append(LINE + "\n");
	// StdOut.print(str.toString());
	// }

	// public static int[][][] getPossibleValuesAll() {
	// int[][][] temp = new int[D][D][16];
	// for (int i = 0; i < board.length; i++)
	// for (int j = 0; j < board.length; j++)
	// temp[i][j] = getPossibleValues(i, j);

	// // for (int i = 0; i < board.length; i++)
	// // for (int j = 0; j < board.length; j++) {
	// // StdOut.println("row: " + i + " col: " + j + "\t" + temp[i][j].length);
	// // for (int k = 0; k < temp[i][j].length; k++)
	// // StdOut.print(temp[i][j][k] + " ");
	// // if (temp[i][j].length == 1)
	// // StdOut.print("---------------------------");
	// // StdOut.println();
	// // }
	// return temp;
	// }

	// public static int[] getPossibleValues(int row, int col) {
	// if (board[row][col] != 0) {
	// int t[] = {};
	// return t;
	// }
	// boolean[] nums = new boolean[16];
	// for (int i = 1; i <= D; i++) {
	// board[row][col] = i;
	// nums[i - 1] = validRowCol(row, col) && validBlock(row, col);
	// }
	// board[row][col] = 0;

	// int[] l = new int[16];
	// int c = 0;
	// for (int i = 0; i < D; i++)
	// if (nums[i]) {
	// l[c] = i + 1;
	// c++;
	// }

	// return Arrays.copyOf(l, c);
	// }

	// public static int[][] getMinCell() {
	// int[][] temp = new int[2][2];
	// int[][] fail = new int[1][1];
	// int min = 17;

	// for (int i = 0; i < board.length; i++)
	// for (int j = 0; j < board.length; j++)
	// if (board[i][j] == 0) {
	// int[] vals = getPossibleValues(i, j);
	// int num = vals.length;
	// if (num == 0)
	// return fail;
	// if (num == 1) {
	// temp[0][0] = i;
	// temp[0][1] = j;
	// temp[1] = vals;
	// return temp;
	// }
	// if (num < min) {
	// temp[0][0] = i;
	// temp[0][1] = j;
	// temp[1] = vals;
	// min = num;
	// }
	// }
	// return temp;
	// }

}
