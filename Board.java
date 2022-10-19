import java.io.IOException;

public class Board {

    private int length, p, c, cont, space;
    private int[][] bPlaces;
    private boolean intercal;

    public Board(int length, int pigs, int chickens) {
        this.length = length; // tamanho do tabuleiro
        this.p = pigs; // quantidade de porquinhos
        this.c = chickens; // quantidade de galinhas
        cont = 0; // quantidade de maneiras únicas possíveis de dispor no tabuleiro
        bPlaces = new int[length][length]; // matrjz de posições que ja não podem ser acessadas pelo animal oposto
        intercal = true; // se faz intercalado (um porquino e depois uma galinha) ou se bota todos
        space = length * length;
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

            int isA = (this.p < this.c) ? 1 : 2;
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

        if (isPig) {
            if (space < this.c) {
                return cont > 0;
            }
        } else {
            if (space < this.p) {
                return cont > 0;
            }
        }

        if (isPig && this.p == 0) {
            if (space == this.c) {
                cont++;
                return cont > 0;
            }
        }
        if (!isPig && this.c == 0) {
            if (space == this.p) {
                cont++;
                return cont > 0;
            }
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
                        isLocked(i, j, a, true, space);
                        if (isPig && this.p > 0) {
                            xp = i;
                            yp = j;
                            this.p--;
                            isLocked(i, j, a, false, space);
                            boolean pigs = (this.p > 0) ? picnic(b, 1, xp, yp, xc, yc)
                                    : picnic(b, 2, xp, yp, xc, yc);
                            this.p++;
                        } else {
                            xc = i;
                            yc = j;
                            this.c--;
                            isLocked(i, j, a, false, space);
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

        if (board[row][col] != 0 || board[row][col] == a) {
            return false;
        }

        for (i = 0; i < length; i++) {
            if (board[row][i] != 0 && board[row][i] != a || board[i][col] != 0 && board[i][col] != a) {
                return false;
            }
        }

        for (i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] != 0 && board[i][j] != a)
                return false;

        for (i = row, j = col; j >= 0 && i < length; i++, j--)
            if (board[i][j] != 0 && board[i][j] != a)
                return false;

        for (i = row, j = col; j < length && i >= 0; i--, j++)
            if (board[i][j] != 0 && board[i][j] != a)
                return false;

        for (i = row, j = col; i < length && j < length; i++, j++)
            if (board[i][j] != 0 && board[i][j] != a)
                return false;

        // System.out.println();
        // placesPrinter(bPlaces);
        // System.out.println();

        return true;
    }

    public boolean isLocked(int row, int col, int a, boolean isFree, int space) throws IOException {
        int i, j;
        boolean isPig = (a == 1) ? true : false;

        for (i = 0; i < length; i++) {
            if (isFree && bPlaces[row][i] == 0)
                space--;
            if (isFree && bPlaces[i][col] == 0)
                space--;
            if (isPig && isFree) {
                bPlaces[row][i] = bPlaces[row][i] + 1;
                bPlaces[i][col] = bPlaces[i][col] + 1;
            } else if (isPig && !isFree) {
                bPlaces[row][i] = bPlaces[row][i] - 1;
                bPlaces[i][col] = bPlaces[i][col] - 1;
            }
            if (!isFree && bPlaces[row][i] == 0)
                space++;
            if (!isFree && bPlaces[i][col] == 0)
                space++;
        }

        for (i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (isFree && bPlaces[i][j] == 0)
                space--;
            int p = (isPig && isFree) ? (bPlaces[i][j] = bPlaces[i][j] + 1)
                    : (isPig && !isFree) ? (bPlaces[i][j] = bPlaces[i][j] - 1) : bPlaces[i][j];
            if (!isFree && bPlaces[i][j] == 0)
                space++;

        }

        for (i = row, j = col; j >= 0 && i < length; i++, j--) {
            if (isFree && bPlaces[i][j] == 0)
                space--;
            int p = (isPig && isFree) ? (bPlaces[i][j] = bPlaces[i][j] + 1)
                    : (isPig && !isFree) ? (bPlaces[i][j] = bPlaces[i][j] - 1) : bPlaces[i][j];
            if (!isFree && bPlaces[i][j] == 0)
                space++;

        }

        for (i = row, j = col; j < length && i >= 0; i--, j++) {
            if (isFree && bPlaces[i][j] == 0)
                space--;
            int p = (isPig && isFree) ? (bPlaces[i][j] = bPlaces[i][j] + 1)
                    : (isPig && !isFree) ? (bPlaces[i][j] = bPlaces[i][j] - 1) : bPlaces[i][j];
            if (!isFree && bPlaces[i][j] == 0)
                space++;

        }

        for (i = row, j = col; i < length && j < length; i++, j++) {
            if (isFree && bPlaces[i][j] == 0)
                space--;
            int p = (isPig && isFree) ? (bPlaces[i][j] = bPlaces[i][j] + 1)
                    : (isPig && !isFree) ? (bPlaces[i][j] = bPlaces[i][j] - 1) : bPlaces[i][j];
            if (!isFree && bPlaces[i][j] == 0)
                space++;

        }

        if (space > (length * length))
            space = length * length;

        if (this.c > space)
            return false;

        // System.out.println("espacos livres: " + space);
        // System.out.println();
        // placesPrinter(bPlaces);
        // System.out.println();

        return true;
    }

    // metodo resposavel apenas por printar de maneira "bonitinha" as matrizes com
    // porquinhos e galinhas
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

    public static void placesPrinter(int[][] board) throws IOException {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + "  ");
            }
            System.out.println("  ");
        }
    }

}
