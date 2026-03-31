package network;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(4000)) {
            System.out.println("Server started. Waiting for client...");

            try (Socket socket = server.accept();
                 DataInputStream in = new DataInputStream(socket.getInputStream())) {

                String line;
                while (!(line = in.readUTF()).equals("End")) {
                    System.out.println("Client: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}