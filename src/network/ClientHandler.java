package network;

import java.io.*;
import java.net.*;
import org.json.*;

// This class handles all communication with ONE specific client.
// Because it implements Runnable, it can run in its own background thread.
public class ClientHandler implements Runnable {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String loggedInUser = null;
    private DatabaseManager dbManager;

    // Constructor gets the socket from the main Server loop and the shared DatabaseManager
    public ClientHandler(Socket socket, DatabaseManager dbManager) {
        this.socket = socket;
        this.dbManager = dbManager;
    }

    @Override
    public void run() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            String line;
            // This loop now runs in a separate thread, so it doesn't block other clients!
            while (true) {
                try {
                    line = in.readUTF();
                    if (line.equals("End")) {
                        break;
                    }
                    
                    System.out.println("Received: " + line);
                    
                    try {
                        JSONObject request = new JSONObject(line);
                        
                        // Using optString so it doesn't crash if "action" is missing
                        String action = request.optString("Action");
                        
                        if (action != null && !action.isEmpty()) {
                            String user = request.getString("UserName");
                            String pass = request.getString("Password");
                            
                            JSONObject response = new JSONObject();
                            
                            if (action.equals("Login")) {
                                boolean success = dbManager.loginUser(user, pass);
                                response.put("status", success ? "success" : "fail");
                                response.put("message", success ? "Login successful" : "Invalid credentials");
                                if (success) loggedInUser = user;
                                
                            } else if (action.equals("Register")) {
                                boolean success = dbManager.registerUser(user, pass);
                                response.put("status", success ? "success" : "fail");
                                response.put("message", success ? "Registered!" : "Username taken");
                                if (success) loggedInUser = user;
                                
                            } else {
                                response.put("status", "error");
                                response.put("message", "Unknown action");
                            }
                            
                            // Send the response back to the client
                            out.writeUTF(response.toString());
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid JSON format received or missing keys.");
                    }

                } catch (EOFException e) {
                    // This happens if the client disconnects abruptly
                    System.out.println("Client disconnected unexpectedly.");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    private void closeConnection() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
            System.out.println("Client handler closed for: " + (loggedInUser != null ? loggedInUser : "Unknown"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
