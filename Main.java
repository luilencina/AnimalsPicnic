import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("porquinhos" + " " + args[1] + " " + "galinhas" + " " + args[2]);
        Picnic l = new Picnic(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        l.start();
    }
}