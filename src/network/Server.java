package network;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        // Initialize our DatabaseManager once to be shared among all clients
        DatabaseManager dbManager = new DatabaseManager(); 

        try (ServerSocket server = new ServerSocket(4000)) {
            System.out.println("Server started on port 4000. Waiting for clients...");

            // The main thread loops forever, just accepting new connections
            while (true) {
                // This blocks until a client connects
                Socket clientSocket = server.accept();
                System.out.println("New client connected from: " + clientSocket.getInetAddress());

                // Create a new handler for this specific client and start it in a new Thread
                ClientHandler handler = new ClientHandler(clientSocket, dbManager);
                Thread clientThread = new Thread(handler);
                clientThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
