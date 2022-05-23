// SimpleServer.java: A simple server program.
import java.net.*;
import java.io.*;

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


        // Wait and accept a connection

    }

    public static void main(String args[]) throws IOException {
        SimpleServer ss = new SimpleServer();
        ss.write("Hi there!");
        System.out.println(ss.read());
        ss.write("Please input the string for uppercase conversion...");
        String msg = ss.read();
        ss.write(msg.toUpperCase());
    }

    // Send a string!
    public void write(String data){
        try {
            dos.writeUTF(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String read() {
        try {
            String st = new String (dis.readUTF());
            return st;
        } catch (IOException e) {
            throw new RuntimeException(e);
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
}
