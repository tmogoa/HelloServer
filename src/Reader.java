import java.io.DataInputStream;
import java.io.IOException;

public class Reader extends Thread{
    private SimpleServer ss;

    public Reader(SimpleServer ss) {
        this.ss = ss;
    }


    @Override
    public void run() {
        while (true) {
            try {
                sleep(100);
                if (ss.getDataOutputStream().available() > 0){
                    String data = ss.read();
                    System.out.println(data);
                    ss.write(data.toUpperCase());
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
