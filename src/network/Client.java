package network;

import java.io.*;
import java.net.*;

public class Client {
    
    // We store the socket and output stream as fields so other methods 
    // can use them later to send messages to the server.
    private Socket socket;
    private DataOutputStream out;
    
    // We use a Thread to prevent blocking the JavaFX UI
    public void Initialize() {
        
        // 1. Create a new background Thread
        Thread clientThread = new Thread(() -> {
            try {
                // 2. Connect to the server on localhost (127.0.0.1) at port 4000
                System.out.println("Connecting to server...");
                socket = new Socket("127.0.0.1", 4000);
                out = new DataOutputStream(socket.getOutputStream());
                
                System.out.println("Connected to Server!");
                
                // 3. Keep listening for console input (Temporary for testing)
                // Note: In the final game, GameIU will send data, not the console.
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String line;
                
                // This loop blocks, but because it's in a separate thread, 
                // the JavaFX window remains responsive!
                while (!(line = br.readLine()).equals("End")) {
                    out.writeUTF(line);
                }
                
                // Close resources when "End" is typed
                closeConnection();
                
            } catch (IOException e) {
                System.out.println("Failed to connect to server: " + e.getMessage());
                // e.printStackTrace(); // Uncomment for full error trace
            }
        });
        
        // Start the thread running in the background
        clientThread.start();
    }
    
    // A helper method to cleanly close the connection later
    public void closeConnection() {
        try {
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
