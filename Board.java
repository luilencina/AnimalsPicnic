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
        space = 0; // quantidade de espaços livres ainda
        intercal = true; // se faz intercalado (um porquino e depois uma galinha) ou se bota todos
                         // primeiro e depois encaixa os outros
    }

    // esse metodo apenas cria o tabuleito e faz uma mini-verificação de casos em
    // que o resultado da 0 de cara
    // caso contrário ele chama o pinic
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

    // metodo responsavel pela disposicao dos porcos e galinhas no tabuleiro
    public boolean picnic(int[][] b, int a, int xp, int yp, int xc, int yc) throws IOException {

        // verifica se o animal mandado é um porqiunho
        // caso !isPig, é uma galiha // a é o animal, logo se a == 1 é porco
        boolean isPig = (a == 1) ? true : false;
        int animal = isPig ? 2 : 1;

        // caso ele for um porquinho, começa da ultima posicão do porquinho (linha e
        // coluna) - > (x, y)
        // xp = ultima posicão da linha do porquinho // yp = ultima posicao da linha dos
        // porquinhos
        // yp = ultima posicao da coluna do porquinho || yc = ultima posicao da linha
        // das galinhas
        int x = isPig ? xp : xc;
        int y = isPig ? yp : yc;

        // caso acabe os porquinhos e as galinhas (não tem mais o que botar) e encontrou
        // a solução
        // metodo cont++ (mais uma solucao) e retorna true
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
                            if (isLocked(i, j, a, true))
                                return false;
                            boolean pigs = (this.p > 0) ? picnic(b, 1, xp, yp, xc, yc)
                                    : picnic(b, 2, xp, yp, xc, yc);
                            this.p++;
                        } else {
                            xc = i;
                            yc = j;
                            this.c--;
                            picnic(b, 2, xp, yp, xc, yc);
                            this.c++;
                        }
                        isLocked(i, j, a, false);
                    }

                    b[i][j] = 0; // backtrack
                }
            }
            y = 0;
        }
        return false;

    }

    // esse metodo verifica se ta tudo bem botar o porquinho na poxicao [x][y] do
    // tabuleiro
    // ele verifica se não tem nenuhma galinha na mesma coluna, linha e diagonal
    // caso tiver ele não deixa rodar e tenta o proximo
    boolean isSafe(int board[][], int row, int col, int a) throws IOException {
        int i, j;
        space = 0;

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

    public boolean isLocked(int row, int col, int a, boolean isFree) throws IOException {
        int i, j;
        boolean isPig = (a == 1) ? true : false;

        for (i = 0; i < length; i++) {
            if (isPig && isFree) {
                bPlaces[row][i] = bPlaces[row][i] + 1;
                bPlaces[i][col] = bPlaces[i][col] + 1;
            } else if (isPig && !isFree) {
                bPlaces[row][i] = bPlaces[row][i] - 1;
                bPlaces[i][col] = bPlaces[i][col] - 1;
            }
            int sRow = (bPlaces[row][i] == 0) ? space++ : 0;
            int sCol = (bPlaces[i][col] == 0) ? space++ : 0;
        }

        for (i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            int p = (isPig && isFree) ? (bPlaces[i][j] = bPlaces[i][j] + 1)
                    : (isPig && !isFree) ? (bPlaces[i][j] = bPlaces[i][j] - 1) : bPlaces[i][j];
            int sRow = (bPlaces[row][i] == 0) ? space++ : 0;

        }

        for (i = row, j = col; j >= 0 && i < length; i++, j--) {
            int p = (isPig && isFree) ? (bPlaces[i][j] = bPlaces[i][j] + 1)
                    : (isPig && !isFree) ? (bPlaces[i][j] = bPlaces[i][j] - 1) : bPlaces[i][j];
            int sRow = (bPlaces[row][i] == 0) ? space++ : 0;

        }

        for (i = row, j = col; j < length && i >= 0; i--, j++) {
            int p = (isPig && isFree) ? (bPlaces[i][j] = bPlaces[i][j] + 1)
                    : (isPig && !isFree) ? (bPlaces[i][j] = bPlaces[i][j] - 1) : bPlaces[i][j];
            int sRow = (bPlaces[row][i] == 0) ? space++ : 0;

        }

        for (i = row, j = col; i < length && j < length; i++, j++) {
            int p = (isPig && isFree) ? (bPlaces[i][j] = bPlaces[i][j] + 1)
                    : (isPig && !isFree) ? (bPlaces[i][j] = bPlaces[i][j] - 1) : bPlaces[i][j];
            int sRow = (bPlaces[row][i] == 0) ? space++ : 0;

        }

        if (this.c > space)
            return false;

        System.out.println("espacos livres: " + space);
        System.out.println();
        placesPrinter(bPlaces);
        System.out.println();

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
