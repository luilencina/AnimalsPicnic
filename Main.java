import java.io.IOException;
import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) throws IOException {
        Board x = new Board(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
                Integer.parseInt(args[2]));
        SimpleDateFormat t = new SimpleDateFormat("hh:mm:ss,SSS");
        long start = System.currentTimeMillis();
        // Board x = new Board(8, 2,
        // 24);
        x.start();
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("o metodo executou em " + t.format(elapsed));
    }
}