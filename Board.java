import java.io.IOException;

public class Board {

    private int t, p, c;

    public Board(int tam, int pigs, int chickens) {
        this.t = tam;
        this.p = pigs;
        this.c = chickens;
    }

    public void start() throws IOException {
        int[][] board = new int[this.t][this.t];
        picnic(board, 1, 0, 0);
        boardPrinter(board);
    }

    public boolean picnic(int[][] b, int a, int r, int c) throws IOException {

        if (this.p <= 0 && this.c <= 0)
            return true;

        for (int i = r; i < b.length; i++) {
            for (int j = c; j < b[i].length; j++) {
                if (isSafe(b, i, j, a)) {
                    b[i][j] = a;

                    if (a == 1 && this.c > 0) {
                        this.p--;
                        picnic(b, 2, i + 1, j);
                    } else if (a == 2 && this.p > 0) {
                        this.c--;
                        picnic(b, 1, r - 1, j);
                    } else if (this.p > 0 && this.c == 0) {
                        this.p--;
                        picnic(b, 1, i, j);
                    } else {
                        this.c--;
                        picnic(b, 2, i, j);
                    }

                }
            }
        }

        return false;

    }

    boolean isSafe(int board[][], int row, int col, int a) {
        int i, j;

        /* Check this row on left side */
        for (i = 0; i < col; i++)
            if (board[row][i] == a)
                return false;

        /* Check upper diagonal on left side */
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == a)
                return false;

        /* Check lower diagonal on left side */
        for (i = row, j = col; j >= 0 && i < board.length; i++, j--)
            if (board[i][j] == a)
                return false;

        return true;
    }

    public static void boardPrinter(int[][] board) throws IOException {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + "  ");
            }
            System.out.println("  ");
        }
    }

}
