package network;

import java.io.*;
import java.net.*;
import org.json.JSONObject;


public class Server {

    private ClientHandler[] Clients = new ClientHandler[2];
    private int CurrentArrayIndex = 0; 
    public void main(String[] args) {
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
                ClientHandler handler = new ClientHandler(clientSocket, dbManager, this);
                addClient(handler);
                System.out.println("Added new client handler");
                Thread clientThread = new Thread(handler);
                clientThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addClient(ClientHandler NewClient) {
        ClientHandler[] NewClientArray = new ClientHandler[2];
        NewClientArray = Clients;
        NewClientArray[CurrentArrayIndex] = NewClient;
        CurrentArrayIndex += 1;

    }

    public void UpdateOponentData(String LoggedUser, JSONObject Data) {
        for (int i = 0; i < 2; i++) {
            if (!LoggedUser.equals(Clients[i].GetLoggedUser())) {
                Clients[i].handleOponentUpdate(Data);
            } 

        }
    }


}
