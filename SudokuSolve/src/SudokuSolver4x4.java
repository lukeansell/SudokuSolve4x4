import java.util.Arrays;

public class SudokuSolver4x4 {

    private static final int X = 4;
    private static final int Y = 4;
    private static final int D = X * Y;
    private int[][] board = new int[D][D];
    private boolean[][] empty = new boolean[D][D];
    private String LINE = "";
    private boolean[][][] ops = new boolean[D][D][D];

    // private int cellsFilled = 0;
    // private int cellsStart = 0;
    // private boolean complete = false;
    private boolean filled = false;
    // private final double SIZE = 180.0;
    // private double[][][] boardPos = new double[D][D][2];
    // private static final int DISPLAYDELAY = 0;
    // private static final int GREENWAIT = 0;

    // private Stopwatch sw = new Stopwatch();
    // public double tFill = 0.0;
    // public double tMinCell = 0.0;
    // public double tComplete = 0.0;
    // public double tL1 = 0.0;
    // public double tL2 = 0.0;
    // public double tL3 = 0.0;
    // public double lTime = 0.0;

    public int tries = 0;

    public SudokuSolver4x4(String filename) {
        populateLINE();
        populateBoard(filename);

    }

    private void populateLINE() {
        for (int i = 0; i < (D + Y - 1); i++)
            LINE += "---";
    }

    private void populateBoard(String filename) {
        In in = new In(filename);
        for (int i = 0; i < D; i++)
            for (int j = 0; j < D; j++) {
                board[i][j] = in.readInt();
                empty[i][j] = board[i][j] == 0;
                // if (empty[i][j])
                // cellsStart++;
            }
    }

    public void boardPrintNice() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < D; i++) {
            if (i % X == 0)
                str.append(LINE + "\n");

            for (int j = 0; j < D; j++) {
                if (j % Y == 0)
                    str.append("| ");
                str.append(String.format("%3d", board[i][j]));
            }

            str.append("|\n");
        }
        str.append(LINE + "\n");
        StdOut.print(str.toString());
    }

    public void solve() {
        if (filled())
            return;
        // lTime = sw.elapsedTime();
        int[][] cell = getMinCell();
        // tMinCell += sw.elapsedTime() - lTime;
        if (cell.length == 1)
            return;

        int values[] = cell[1];
        for (int i = 0; i < values.length; i++) {
            board[cell[0][0]][cell[0][1]] = values[i];

            solve();

            if (filled())
                return;
        }
        board[cell[0][0]][cell[0][1]] = 0;
    }

    public void startSolve() {
        initOps();
        solve2();
    }

    private void initOps() {
        for (int row = 0; row < D; row++) {
            for (int col = 0; col < D; col++) {
                initOps(row, col);
            }
        }
    }

    private void initOps(int row, int col) {
        for (int i = 0; i < D; i++)
            ops[row][col][i] = board[row][col] == 0 && checkAll(row, col, i + 1);
    }

    // private void initOpsN(int num, int row, int col) {
    // // for (int i = 0; i < D; i++)
    // ops[row][col][num - 1] = board[row][col] == 0 && checkAll(row, col, num);
    // }

    // private void initOpsN(int num) {
    // for (int row = 0; row < D; row++) {
    // for (int col = 0; col < D; col++) {
    // ops[row][col][num] = (board[row][col] == 0 && checkAll(row, col, num + 1));
    // }
    // }
    // }

    // private boolean initOpsL() {
    // boolean bool = false;
    // for (int row = 0; row < D; row++)
    // for (int col = 0; col < D; col++)
    // bool = initOpsL(row, col) || bool;

    // return bool;
    // }

    // private boolean initOpsL(int row, int col) {
    // boolean bool = false;
    // for (int i = 0; i < D; i++) {
    // if (ops[row][col][i] != (board[row][col] == 0 && checkAll(row, col, i + 1)))
    // {
    // ops[row][col][i] = !ops[row][col][i];
    // System.out.println("r: " + row + " c: " + col + " n: " + (i + 1) + " was: " +
    // !ops[row][col][i]);
    // bool = true;
    // }
    // // ops[row][col][i] = board[row][col] == 0 && checkAll(row, col, i + 1);
    // }
    // return bool;

    // }

    // private void initOpsH(int row, int col) {
    // int r = row / X * X;
    // int c = col / Y * Y;
    // for (int i = r; i < r + X; i++)
    // for (int j = c; j < c + Y; j++)
    // initOps(i, j);
    // }

    private void initOpsHN(int num, int row, int col) {
        int r = row / X * X;
        int c = col / Y * Y;
        for (int i = r; i < r + X; i++)
            for (int j = c; j < c + Y; j++)
                ops[i][j][num - 1] = board[i][j] == 0 && checkAll(i, j, num);
        // initOpsN(num, i, j);
    }

    private boolean checkAll(int row, int col, int num) {
        return checkRC(row, col, num) && checkHouse(row, col, num);
    }
    // private boolean checkAll(int row, int col, int num) {
    // return checkRow(row, num) && checkCol(col, num)
    // && checkHouse(row, col, num);
    // }

    private boolean checkRow(int row, int num) {
        for (int i = 0; i < D; i++)
            if (board[row][i] == num)
                return false;
        return true;
    }

    private boolean checkCol(int col, int num) {
        for (int i = 0; i < D; i++)
            if (board[i][col] == num)
                return false;
        return true;
    }

    private boolean checkRC(int row, int col, int num) {
        for (int i = 0; i < D; i++)
            if (board[i][col] == num || board[row][i] == num)
                return false;

        return true;
    }

    private boolean checkHouse(int row, int col, int num) {
        int r = row / X * X;
        int c = col / Y * Y;
        for (int i = r; i < r + X; i++)
            for (int j = c; j < c + Y; j++)
                if (board[i][j] == num)
                    return false;

        return true;
    }

    private void solve2() {
        tries++;
        // lTime = sw.elapsedTime();
        int[] place = findLowestOps();
        // tMinCell += sw.elapsedTime() - lTime;
        if (place[0] == D)
            return;
        boolean[] sOps = ops[place[0]][place[1]];
        // if (initOpsL()) {
        // System.out.println();
        // }

        // boolean[][][] oldOps = ops.clone();
        // Arrays.copyOfRange(original, from, to)
        // Arrays.copyOfRange(ops, from, to)

        for (int i = 0; i < D; i++) {
            if (sOps[i]) {
                // lTime = sw.elapsedTime();
                board[place[0]][place[1]] = i + 1;
                // System.out.println("r: " + place[0] + " c: " + place[1] + " n: " + (i + 1) +
                // " placed");
                updateOpsRemove(place[0], place[1], i + 1);
                // tL1 += sw.elapsedTime() - lTime;
                if (hasOps())
                    solve2();
                if (complete2())
                    return;
                // lTime = sw.elapsedTime();
                board[place[0]][place[1]] = 0;

                // System.out.println("r: " + place[0] + " c: " + place[1] + " n: " + (i + 1) +
                // " removed");

                updateOpsAdd(place[0], place[1], i + 1);
                // tL3 += sw.elapsedTime() - lTime;
            }
        }
    }

    // public void checkOps

    private boolean hasOps() {
        for (int i = 0; i < X; i++)
            for (int j = 0; j < Y; j++)
                if (board[i][j] == 0 && numOps(i, j) == 0)
                    return false;

        return true;
    }

    private void updateOpsAdd(int row, int col, int num) {
        // lTime = sw.elapsedTime();
        initOps(row, col);
        initOpsHN(num, row, col);
        // tL2 += sw.elapsedTime() - lTime;
        // lTime = sw.elapsedTime();
        for (int a = 0; a < D; a += 4) {
            boolean hr = checkHouse(row, a, num);
            boolean hc = checkHouse(a, col, num);

            for (int i = a; i < a + 4; i++) {
                ops[row][i][num - 1] = hr && board[row][i] == 0 && checkCol(i, num);
                ops[i][col][num - 1] = hc && board[i][col] == 0 && checkRow(i, num);
            }
        }
        // hr = checkHouse(row, 4, num);
        // hc = checkHouse(4, col, num);

        // for (int i = 4; i < 8; i++) {
        // ops[row][i][num - 1] = hr && board[row][i] == 0 && checkRC(row, i, num);
        // ops[i][col][num - 1] = hc && board[i][col] == 0 && checkAll(i, col, num);
        // }
        // hr = checkHouse(row, 8, num);
        // hc = checkHouse(8, col, num);

        // for (int i = 8; i < 12; i++) {
        // ops[row][i][num - 1] = hr && board[row][i] == 0 && checkRC(row, i, num);
        // ops[i][col][num - 1] = hc && board[i][col] == 0 && checkAll(i, col, num);
        // }
        // hr = checkHouse(row, 12, num);
        // hc = checkHouse(12, col, num);

        // for (int i = 12; i < 16; i++) {
        // ops[row][i][num - 1] = hr && board[row][i] == 0 && checkRC(row, i, num);
        // ops[i][col][num - 1] = hc && board[i][col] == 0 && checkAll(i, col, num);
        // }
        // tL3 += sw.elapsedTime() - lTime;
    }
    // private void updateOpsAdd(int row, int col, int num) {
    // lTime = sw.elapsedTime();
    // initOps(row, col);
    // initOpsHN(num, row, col);
    // tL2 += sw.elapsedTime() - lTime;
    // lTime = sw.elapsedTime();
    // for (int i = 0; i < D; i++) {

    // // ops[row][i][num - 1] = board[row][i] == 0 && checkCol(i, num) &&
    // checkHouse(row, i, num);
    // ops[row][i][num - 1] = board[row][i] == 0 && checkAll(row, i, num);
    // ops[i][col][num - 1] = board[i][col] == 0 && checkAll(i, col, num);

    // // initOps( row, i);
    // // initOps( i, col);
    // }
    // tL3 += sw.elapsedTime() - lTime;
    // }

    // private void upRowAdd(int row, int num) {

    // }

    private boolean complete2() {
        for (int i = 0; i < D; i++)
            for (int j = 0; j < D; j++)
                if (board[i][j] == 0)
                    return false;
        return true;
    }

    private void updateOpsRemove(int row, int col, int num) {
        updateCell(row, col);
        updateRowRemove(row, num);
        updateColRemove(col, num);
        updateHouseRemove(row, col, num);
    }

    private void updateCell(int row, int col) {
        for (int i = 0; i < D; i++) {
            ops[row][col][i] = false;
        }
    }

    private void updateRowRemove(int row, int num) {
        for (int i = 0; i < D; i++)
            ops[row][i][num - 1] = false;
    }

    private void updateColRemove(int col, int num) {
        for (int i = 0; i < D; i++)
            ops[i][col][num - 1] = false;
    }

    private void updateHouseRemove(int row, int col, int num) {
        int r = row / X * X;
        int c = col / Y * Y;
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                ops[r + i][c + j][num - 1] = false;
            }
        }
    }

    private int[] findLowestOps() {
        int[] place = { D, D };
        int min = D;
        for (int i = 0; i < D; i++)
            for (int j = 0; j < D; j++)
                if (board[i][j] == 0 && min > numOps(i, j)) {
                    min = numOps(i, j);
                    place[0] = i;
                    place[1] = j;
                    if (min == 1)
                        return place;

                }

        return place;
    }

    private int numOps(int row, int col) {
        int count = 0;
        for (int i = 0; i < D; i++)
            count += ops[row][col][i] ? 1 : 0;
        return count;
    }

    private boolean filled() {
        // lTime = sw.elapsedTime();
        if (filled)
            return true;
        for (int i = 0; i < D; i++)
            for (int j = 0; j < D; j++)
                if (board[i][j] == 0) {
                    // tFill += (sw.elapsedTime() - lTime);
                    return false;
                }
        filled = true;
        // tFill += (sw.elapsedTime() - lTime);
        return true;
    }

    private int[][] getMinCell() {
        int[][] temp = new int[2][2];
        int[][] fail = new int[1][1];
        int min = 17;

        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++)
                if (board[i][j] == 0) {
                    int[] vals = getPossibleValues(i, j);
                    int num = vals.length;
                    if (num == 0)
                        return fail;
                    if (num == 1) {
                        temp[0][0] = i;
                        temp[0][1] = j;
                        temp[1] = vals;
                        return temp;
                    }
                    if (num < min) {
                        temp[0][0] = i;
                        temp[0][1] = j;
                        temp[1] = vals;
                        min = num;
                    }
                }
        return temp;
    }

    private int[] getPossibleValues(int row, int col) {
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

        int[] l = new int[16];
        int c = 0;
        for (int i = 0; i < D; i++)
            if (nums[i]) {
                l[c] = i + 1;
                c++;
            }

        return Arrays.copyOf(l, c);
    }

    public boolean validBoard() {
        return validRowsCols() && validHouses() && complete2();
    }

    private boolean validHouses() {
        for (int i = 0; i < Y; i++)
            for (int j = 0; j < X; j++)
                if (!validBlock(i * Y, j * X))
                    return false;

        return true;
    }

    private boolean validBlock(int sRow, int sCol) {
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

    private boolean validRowsCols() {
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

    private boolean validRowCol(int row, int col) {
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

    // private boolean validRow(int row) {
    // boolean[] nums = new boolean[D + 1];
    // for (int i = 0; i < D; i++) {
    // int num = board[row][i];
    // if (nums[num])
    // return false;
    // nums[num] = num != 0;
    // }

    // return true;
    // }

    // private boolean validCol(int col) {
    // boolean[] nums = new boolean[D + 1];
    // for (int i = 0; i < D; i++) {
    // int num = board[i][col];
    // if (nums[num])
    // return false;
    // nums[num] = num != 0;
    // }
    // return true;
    // }

}
