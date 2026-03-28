package network;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(5000);
             Socket socket = server.accept();
             DataInputStream in = new DataInputStream(socket.getInputStream())) {

            System.out.println("Server started. Waiting for client...");
            String line;
            while (!(line = in.readUTF()).equals("End")) {
                System.out.println("Client: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}