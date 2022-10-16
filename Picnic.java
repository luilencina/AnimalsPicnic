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

        if (this.p == 0 && this.c == 0) {
            System.out.println("Nenhum animal inserido");
            return;
        }

        // if (this.c > 0) {
        // if (!solve(board, 0, 0, 0, 0, this.p, this.c, 2)) {
        // System.out.print("nenhuma solução encontrada");
        // return;
        // }
        // } else {
        // if (!solve(board, 0, 0, 0, 0, this.p, this.c, 1)) {
        // System.out.print("nenhuma solução encontrada");
        // return;
        // }
        // }

        solve(board, 0, 0, 0, 0, this.p, this.c, 1);
        boardPrinter(board);
        // for (int i = 0; i < solutions.size(); i++) {
        // System.out.println("solução: " + i + "\n");
        // System.out.println(solutions.get(i));
        // System.out.println();
        // }
    }

    public void teste1(int[][] b, int a, int col, int row) throws IOException {

        if (this.p <= 0 && this.c <= 0)
            System.out.println("chegou no final!");

        for (int i = col; i < b.length; i++) {
            for (int j = row; j < b[i].length; j++) {
                if (isSafe(b, i, col, a)) {
                    b[i][j] = a;
                    if (a == 1) {
                        this.p--;
                        teste1(b, 2, j + 1, i);

                    }
                    if (a == 2) {
                        this.c--;
                        teste1(b, 1, j, i);
                    }
                }
            }
        }

        System.out.println("porquinhos: " + this.p + " " + "galinhas: " + this.c);
    }

    public boolean lunchTime(int[][] b, int row, int col, int a) throws IOException {

        int pigs, chicken;
        pigs = this.p;
        chicken = this.c;

        if (this.p <= 0 && this.c <= 0)
            return true;

        for (int i = col; i < b.length; i++) {
            for (int j = row; j < b[i].length; j++) {
                if (this.p == pigs) {
                    this.p--;
                    b[i][j] = a;
                    lunchTime(b, row, col, 2);
                }

                if (a == 2 && this.p > 0) {
                    this.c--;
                    lunchTime(b, row, col, 1);
                }
            }
        }

        System.out.println("porquinhos: " + this.p + " " + "galinhas: " + this.c);
        return false;
    }

    private boolean solve(int[][] b, int linhaPorquinho, int colunaPorquinho, int linhaGalinha,
            int colunaGalinha,
            int numeroPorquinhos, int numeroGalinhas,
            int a) throws IOException {
        if (numeroGalinhas == 0 && numeroPorquinhos == 0) {
            // solutions.add(boardPrinter(b));
            return true;
        }
        int linha = 0;
        int coluna = 0;
        int numeroChecador = 0;
        if (a == 1 && numeroPorquinhos > 0) {
            coluna = colunaPorquinho;
            linha = linhaPorquinho;
        } else if (a == 2 && numeroGalinhas > 0) {
            linha = linhaGalinha;
            coluna = colunaGalinha;
        }

        if (a == 1) {
            for (int i = linha; i < b.length; i++) {
                for (int j = coluna; j < b.length; j++) {
                    if (isSafe(b, i, j, a)) {
                        b[i][j] = 2;
                        if (numeroGalinhas > 0) {
                            solve(b, i, j, linhaGalinha, colunaGalinha, numeroPorquinhos - 1, numeroGalinhas, 1);
                        } else {
                            solve(b, i, j, linhaGalinha, colunaGalinha, numeroPorquinhos - 1, numeroGalinhas, 2);
                        }
                        b[i][j] = 0;
                    }
                }
            }
        } else {
            for (int i = linha; i < b.length; i++) {
                for (int j = coluna; j < b.length; j++) {
                    if (isSafe(b, i, j, a)) {
                        b[i][j] = 1;
                        if (numeroGalinhas > 0) {
                            solve(b, linhaPorquinho, colunaPorquinho, i, j, numeroPorquinhos,
                                    numeroGalinhas - 1, 1);
                        } else {
                            solve(b, linhaPorquinho, colunaPorquinho, i, j, numeroPorquinhos,
                                    numeroGalinhas - 1, 2);
                        }
                        b[i][j] = 0;
                    }
                }
            }
        }
        return false;
    }

    public boolean isSafe(int board[][], int row, int col, int pc) throws IOException {
        int i, j;

        if (board[row][col] != 0) {
            return false;
        }

        for (i = 0; i < board.length; i++)
            if (board[row][i] == pc) {
                return false;
            }

        for (i = 0; i < board.length; i++)
            if (board[i][col] == pc) {
                return false;
            }

        for (i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == pc) {
                return false;
            }

        for (i = row, j = col; j >= 0 && i < board.length; i++, j--)
            if (board[i][j] == pc) {
                return false;
            }

        for (i = row, j = col; i < board.length && j < board.length; i++, j++)
            if (board[i][j] == pc) {
                return false;
            }

        for (i = row, j = col; j < board.length && i >= 0; i--, j++)
            if (board[i][j] == pc) {
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