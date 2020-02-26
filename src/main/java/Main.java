import thread.FileSearch;
import thread.SYNReceiver;
import thread.TCPConnect;

public class Main {
    public static void main(String[] args) {
        new Thread(new SYNReceiver()).start();
        new Thread(new TCPConnect()).start();
        new Thread(new FileSearch()).start();
    }
}
