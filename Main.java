import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Board x = new Board(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
                Integer.parseInt(args[2]));
        x.start();
    }
}