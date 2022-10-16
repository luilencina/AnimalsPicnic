package src;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int board = Integer.parseInt(args[0]);
        int pigs = Integer.parseInt(args[1]);
        int chickes = Integer.parseInt(args[2]);
        System.out.println("porquinhos" + " " + pigs + " " + "galinhas" + " " + chickes);
        Picnic l = new Picnic(board, pigs, chickes);
        l.start();
    }
}