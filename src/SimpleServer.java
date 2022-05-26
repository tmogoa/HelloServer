// SimpleServer.java: A simple server program.
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class SimpleServer {
    // creating class variables
    private ServerSocket s;
    private Socket sock;
    private OutputStream os;
    private DataOutputStream dos;
    private InputStream is;
    private DataInputStream dis;

    public SimpleServer() {
        try {
            // Register service on port 1254
            s = new ServerSocket(1254);
            System.out.println("Server running");
            // Get a communication stream associated with the socket
            sock = s.accept();

            os = sock.getOutputStream();
            dos = new DataOutputStream (os);

            is = sock.getInputStream();
            dis = new DataInputStream(is);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[]) throws IOException {
        SimpleServer ss = new SimpleServer();
        // start thread to listen for input
        Reader reader = new Reader(ss);
        reader.start();
        // use main thread to listen on console for messages to send
        ss.startWriter();
    }

    // Send a string!
    public void write(String data){
        try {
            dos.writeUTF(data);
        } catch (IOException e) {
            close();
            throw new RuntimeException(e);
        }
    }

    public String read() {
        try {
            String st = new String (dis.readUTF());
            return st;
        } catch (IOException e) {
            close();
            throw new RuntimeException(e);
        }
    }

    public  void startWriter(){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            if(line.equals("quit") || line.equals("q")){
                close();
                System.exit(0);
            }
            write(line);
        }
    }

    public void close(){
        try {
            dis.close();
            dos.close();
            sock.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DataInputStream getDataOutputStream() {
        return dis;
    }
}
