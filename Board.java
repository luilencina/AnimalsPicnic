import java.io.IOException;

public class Board {

    private int length, p, c, cont, space;
    private int[][] bPlaces;
    private boolean intercal;

    public Board(int length, int pigs, int chickens) {
        this.length = length; // tamanho do bPlaces
        this.p = pigs; // quantidade de porquinhos
        this.c = chickens; // quantidade de galinhas
        cont = 0; // quantidade de maneiras únicas possíveis de dispor no bPlaces
        bPlaces = new int[length][length]; // matrjz de posições que ja não podem ser acessadas pelo animal oposto
        intercal = true; // se faz intercalado (um porquino e depois uma galinha) ou se bota todos
        space = length * length; // espaços livres no bPlaces
    }

    public void start() throws IOException {
        int[][] board = new int[this.length][this.length];
        int x = (length * length);

        if (this.c > x || this.p > x || (this.c + this.p) > x || this.c == 0 && this.p == 0) {
            System.out.println(" ");
            System.out.println("Não é possivel uma solução para este caso!");
            System.out.println(" ");
        } else {
            // intercal = (this.p * 2) <= this.c || (this.c * 2) <= this.p ? false : true;
            intercal = false;
            int isA = (this.p < this.c) ? 1 : 2;
            picnic(board, isA, 0, 0, 0, 0);
        }

        System.out.println("Tamanho do bPlaces: " + length + "x" + length);
        System.out.println("Quantidade de porquinhos: " + p);
        System.out.println("Quantidade de galinhas: " + c);
        System.out.println(" ");
        System.out.println("Quantidade de soluções possíveis: " + cont);
        System.out.println(" ");
    }

    boolean picnic(int[][] b, int a, int xp, int yp, int xc, int yc) throws IOException {

        boolean isPig = (a == 1) ? true : false;

        int x = isPig ? xp : xc;
        int y = isPig ? yp : yc;

        if (this.p <= 0 && this.c <= 0) {
            cont++;
            System.out.println("quantidade p e c == 0 " + cont);
            System.out.println("porquinhos " + this.p + " galinhas " + this.c);
            return true;
        }

        // if (isPig && space < this.c) {
        // return false;
        // }

        if (!isPig && this.p == 0) {
            if (space == this.c) {
                cont++;
                return true;
            }
            if (space < this.c)
                return false;
        }

        for (int i = x; i < b.length; i++) {
            for (int j = y; j < b[i].length; j++) {
                if (isSafe(b, i, j, a)) {
                    desocupaEspacosBloqueados(i, j, a, true);
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
                            // if (j < length) {
                            if (this.p < 0 && j < length) {
                                picnic(b, 1, xp, yp, xc + 1, yc);
                            } else if (j > length) {
                                picnic(b, 2, xp, yp, xc, yc + 1);
                            } else if (this.p > 0) {
                                picnic(b, 1, xp, yp, xc, yc);
                            } else {
                                picnic(b, 2, xp, yp, xc, yc + 1);
                            }
                            this.p++;
                        } else if (!isPig && this.c > 0) {
                            xc = i;
                            yc = j;
                            this.c--;
                            picnic(b, 2, xp, yp, xc, yc);
                            this.c++;
                        }
                    }
                    // picnic(b, 2, xp, yp, xc, yc + 1);
                    // } else {
                    // picnic(b, 1, xp, yp, xc + 1, yc);
                    // }

                    desocupaEspacosBloqueados(i, j, a, false);
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

        return true;
    }

    public boolean isLocked(int row, int col, int a, boolean isFree) throws IOException {
        int i, j;
        boolean isPig = (a == 1) ? true : false;

        if (!isPig)
            return false;

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

        return true;
    }

    private boolean desocupaEspacosBloqueados(int row, int col, int a, boolean isFree) {
        boolean isPig = (a == 1) ? true : false;

        if (!isPig)
            return false;

        for (int i = 0; i < length; i++) {
            if (isFree && bPlaces[row][i] == 0)
                space--;
            bPlaces[row][i] = bPlaces[row][i] - 1;
            if (bPlaces[row][i] == 0) {
                space++;
            }
            if (isPig && isFree) {
                bPlaces[row][i] = bPlaces[row][i] + 1;
                bPlaces[i][col] = bPlaces[i][col] + 1;
            } else if (isPig && !isFree) {
                bPlaces[row][i] = bPlaces[row][i] - 1;
                bPlaces[i][col] = bPlaces[i][col] - 1;
            }
        }

        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (isFree && bPlaces[i][j] == 0)
                space--;
            int p = (isPig && isFree) ? (bPlaces[i][j] = bPlaces[i][j] + 1)
                    : (isPig && !isFree) ? (bPlaces[i][j] = bPlaces[i][j] - 1) : bPlaces[i][j];
            if (!isFree && bPlaces[i][j] == 0)
                space++;
        }

        for (int i = row, j = col; j >= 0 && i < length; i++, j--) {
            if (isFree && bPlaces[i][j] == 0)
                space--;
            int p = (isPig && isFree) ? (bPlaces[i][j] = bPlaces[i][j] + 1)
                    : (isPig && !isFree) ? (bPlaces[i][j] = bPlaces[i][j] - 1) : bPlaces[i][j];
            if (!isFree && bPlaces[i][j] == 0)
                space++;
        }

        for (int i = row, j = col; i < length && j < length; i++, j++) {
            if (isFree && bPlaces[i][j] == 0)
                space--;
            int p = (isPig && isFree) ? (bPlaces[i][j] = bPlaces[i][j] + 1)
                    : (isPig && !isFree) ? (bPlaces[i][j] = bPlaces[i][j] - 1) : bPlaces[i][j];
            if (!isFree && bPlaces[i][j] == 0)
                space++;
        }

        for (int i = row, j = col; j < length && i >= 0; i--, j++) {
            if (isFree && bPlaces[i][j] == 0)
                space--;
            int p = (isPig && isFree) ? (bPlaces[i][j] = bPlaces[i][j] + 1)
                    : (isPig && !isFree) ? (bPlaces[i][j] = bPlaces[i][j] - 1) : bPlaces[i][j];
            if (!isFree && bPlaces[i][j] == 0)
                space++;
        }

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

    public static void placesPrinter(int[][] board) throws IOException {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + "  ");
            }
            System.out.println("  ");
        }
    }

}
