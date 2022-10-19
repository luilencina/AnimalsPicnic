import java.io.IOException;

public class Board {

    private int length, p, c, cont, space;
    private int[][] bPlaces;
    private boolean intercal;

    public Board(int length, int pigs, int chickens) {
        this.length = length;
        this.p = pigs;
        this.c = chickens;
        cont = 0;
        bPlaces = new int[length][length];
        space = 0;
        intercal = true;
    }

    public void start() throws IOException {
        int[][] board = new int[this.length][this.length];
        int x = (length * length);

        if (this.c > x || this.p > x || (this.c + this.p) > x) {
            System.out.println(" ");
            System.out.println("Não é possivel uma solução para este caso!");
            System.out.println(" ");
        } else {
            intercal = (this.p * 2) <= this.c || (this.c * 2) <= this.p ? false : true;

            int isA = intercal ? (this.p < this.c) ? 1 : 2 : (this.p > this.c) ? 1 : 2;
            picnic(board, isA, 0, 0, 0, 0);
        }

        System.out.println("Tamanho do tabuleiro: " + length + "x" + length);
        System.out.println("Quantidade de porquinhos: " + p);
        System.out.println("Quantidade de galinhas: " + c);
        System.out.println(" ");
        System.out.println("Quantidade de soluções possíveis: " + cont);
        System.out.println(" ");
    }

    public boolean picnic(int[][] b, int a, int xp, int yp, int xc, int yc) throws IOException {
        boolean isPig = (a == 1) ? true : false;

        int x = isPig ? xp : xc;
        int y = isPig ? yp : yc;

        if (this.p <= 0 && this.c <= 0) {
            // boardPrinter(b);
            // System.out.println(" ");
            cont++;
            return true;
        }

        for (int i = x; i < b.length; i++) {
            for (int j = y; j < b[i].length; j++) {
                if (isSafe(b, i, j, a)) {
                    b[i][j] = a;
                    if (intercal) {
                        if (isPig && this.c > 0) {
                            xp = i;
                            yp = j;
                            this.p--;
                            picnic(b, 2, xp, yp, xc, yc);
                            this.p++;
                        } else if (!isPig && this.p > 0) {
                            xc = i;
                            yc = j;
                            this.c--;
                            picnic(b, 1, xp, yp, xc, yc);
                            this.c++;
                        } else if (this.p > 0 && this.c == 0) {
                            this.p--;
                            xp = i;
                            yp = j;
                            picnic(b, 1, xp, yp, xc, yc);
                            this.p++;
                        } else {
                            xc = i;
                            yc = j;
                            this.c--;
                            picnic(b, 2, xp, yp, xc, yc);
                            this.c++;
                        }
                    } else {
                        if (isPig && this.p > 0) {
                            xp = i;
                            yp = j;
                            this.p--;
                            if (this.p > 0)
                                picnic(b, 1, xp, yp, xc, yc);
                            if (this.p <= 0)
                                picnic(b, 2, xp, yp, xc, yc);
                            this.p++;
                        } else {
                            xc = i;
                            yc = j;
                            this.c--;
                            picnic(b, 2, xp, yp, xc, yc);
                            this.c++;
                        }
                    }

                    b[i][j] = 0; // backtrack
                }
            }
            y = 0;
        }
        return false;

    }

    boolean isSafe(int board[][], int row, int col, int a) throws IOException {
        int i, j;

        if (board[row][col] != 0 || board[row][col] == a)
            return false;

        for (i = 0; i < length; i++) {
            if (board[row][i] != 0 && board[row][i] != a || board[i][col] != 0 && board[i][col] != a)
                return false;
            bPlaces[row][i] = a;
            bPlaces[i][col] = a;
        }

        for (i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] != 0 && board[i][j] != a)
                return false;

            bPlaces[i][j] = a;
        }

        for (i = row, j = col; j >= 0 && i < length; i++, j--) {
            if (board[i][j] != 0 && board[i][j] != a)
                return false;
            bPlaces[i][j] = a;
        }

        for (i = row, j = col; j < length && i >= 0; i--, j++) {
            if (board[i][j] != 0 && board[i][j] != a)
                return false;
            bPlaces[i][j] = a;
        }

        for (i = row, j = col; i < length && j < length; i++, j++) {
            if (board[i][j] != 0 && board[i][j] != a)
                return false;
            bPlaces[i][j] = a;
        }

        // System.out.println(" ");
        // boardPrinter(bPlaces);
        // System.out.println(" ");

        return true;
    }

    boolean isSpace(int a) throws IOException {
        int animal = (a == 1) ? this.p : this.c;

        for (int i = 0; i < bPlaces.length; i++) {
            for (int j = 0; j < bPlaces[i].length; j++) {
                if (bPlaces[i][j] == 0) {
                    space++;
                }
                bPlaces[i][j] = 0; // backtrack
            }
        }

        if (animal > space)
            return false;

        space = 0;
        return true;
    }

    public static void boardPrinter(int[][] board) throws IOException {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 1)
                    System.out.print("P" + "  ");
                if (board[i][j] == 2)
                    System.out.print("G" + "  ");
                if (board[i][j] == 0)
                    System.out.print("." + "  ");
            }
            System.out.println("  ");
        }
    }

}
