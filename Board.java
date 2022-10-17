import java.io.IOException;

public class Board {

    private int t, p, c, cont;

    public Board(int tam, int pigs, int chickens) {
        this.t = tam;
        this.p = pigs;
        this.c = chickens;
        cont = 0;
    }

    public void start() throws IOException {
        int[][] board = new int[this.t][this.t];
        picnic(board, 1, 0, 0);
        System.out.println("Tamanho do tabuleiro: " + this.t + "x" + this.t);
        System.out.println("Quantidade de porquinhos: " + p);
        System.out.println("Quantidade de porquinhos: " + c);
        System.out.println(" ");
        System.out.println("Quantidade de soluções possíveis: " + cont);
        // boardPrinter(board);
    }

    public boolean picnic(int[][] b, int a, int r, int c) throws IOException {

        if (this.p <= 0 && this.c <= 0) {
            cont++;
            boardPrinter(b);
            System.out.println(" ");
            return true;
        }

        for (int i = r; i < b.length; i++) {
            for (int j = c; j < b[i].length; j++) {
                if (isSafe(b, i, j, a)) {
                    b[i][j] = a;

                    if (a == 1 && this.c > 0) {
                        this.p--;
                        picnic(b, 2, i, j);
                        this.p++;
                    } else if (a == 2 && this.p > 0) {
                        this.c--;
                        picnic(b, 1, 0, 0);
                        this.c++;
                    } else if (this.p > 0 && this.c == 0) {
                        this.p--;
                        picnic(b, 1, 0, 0);
                        this.p++;
                    } else {
                        this.c--;
                        picnic(b, 2, i, j);
                        this.c++;
                    }

                    b[i][j] = 0;
                }
            }
        }

        return false;

    }

    boolean isSafe(int board[][], int row, int col, int a) {
        int i, j;

        if (board[row][col] != 0) {
            return false;
        }

        for (i = 0; i < board.length; i++)
            if (board[row][i] != 0 && board[row][i] != a) {
                return false;
            }

        for (i = 0; i < board.length; i++)
            if (board[i][col] != 0 && board[i][col] != a) {
                return false;
            }

        for (i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] != 0 && board[i][j] != a) {
                return false;
            }

        for (i = row, j = col; j >= 0 && i < board.length; i++, j--)
            if (board[i][j] != 0 && board[i][j] != a) {
                return false;
            }

        for (i = row, j = col; i < board.length && j < board.length; i++, j++)
            if (board[i][j] != 0 && board[i][j] != a) {
                return false;
            }

        for (i = row, j = col; j < board.length && i >= 0; i--, j++)
            if (board[i][j] != 0 && board[i][j] != a) {
                return false;
            }

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
