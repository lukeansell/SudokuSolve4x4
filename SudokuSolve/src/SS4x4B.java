public class SS4x4B {

    private static final int X = 4;
    private static final int Y = 4;
    private static final int D = X * Y;
    private int[][] board = new int[D][D];
    private boolean[][] empty = new boolean[D][D];
    private String LINE = "";

    private boolean[][] rowOps = new boolean[X][D];
    private boolean[][] colOps = new boolean[Y][D];
    private boolean[][] houOps;

    public SS4x4B(String filename) {
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
            }
    }

}
