package src;

import java.io.IOException;

public class Picnic {

    private int t, p, c;

    public Picnic(int tam, int pigs, int chickens) {
        this.t = tam;
        this.p = pigs;
        this.c = chickens;
    }

    public void start() throws IOException {
        int[][] board = new int[t][t];
        lunchTime(board, 1, 0, 0);
    }

    public boolean lunchTime(int[][] b, int a, int col, int row) throws IOException {

        if (this.p <= 0 && this.c <= 0)
            return true;

        for (int i = row; i < b.length; i++) {
            for (int j = col; j < b[i].length; j++) {
                b[i][j] = a;
                if (isSafe(b, a, i, j)) {
                    if (a == 1 && this.c > 0) {
                        this.p--;
                        if (lunchTime(b, 2, j + 1, i) == true)
                            return true;
                    }
                    if (a == 2 && this.p > 0) {
                        this.c--;
                        if (lunchTime(b, 1, j, i + 1) == true)
                            return true;
                    }
                    b[i][j] = 0; // backtrack
                }
            }
        }

        System.out.println(" ");
        boardPrinter(b);
        System.out.println("porquinhos" + " " + this.p + " " + "galinhas" + " " + this.c);
        return false;
    }

    // metodo procura se esta seguro na linha, coluna ou diagonal para por o porco
    // ou a galinha.
    // manda por parametro se e porco ou galinh, considerando que 1 é porco e 2
    // galinha.
    public boolean isSafe(int board[][], int row, int col, int pc) throws IOException {
        int i, j;

        if (board[row][col] != 0) {
            System.out.println("entrou false aqui! 0");
            return false;
        }

        for (i = 0; i < board.length; i++)
            if (board[row][i] == pc) {
                System.out.println("entrou false aqui! 1");
                return false;
            }

        for (i = 0; i < board.length; i++)
            if (board[i][col] == pc) {
                System.out.println("entrou false aqui! 2");
                return false;
            }

        for (i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == pc) {
                System.out.println("entrou false aqui! 3");
                return false;
            }

        for (i = row, j = col; j >= 0 && i < board.length; i++, j--)
            if (board[i][j] == pc) {
                System.out.println("entrou false aqui! 4");
                return false;
            }

        for (i = row, j = col; i < board.length && j < board.length; i++, j++)
            if (board[i][j] == pc) {
                System.out.println("entrou false aqui! 5");
                return false;
            }

        for (i = row, j = col; j < board.length && i >= 0; i--, j++)
            if (board[i][j] == pc) {
                System.out.println("entrou false aqui! 6");
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