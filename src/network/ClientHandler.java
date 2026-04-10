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
            while (true) {
                try {
                    line = in.readUTF();
                    if (line.equals("End")) break;

                    System.out.println("Received: " + line);
                    JSONObject request = new JSONObject(line);
                    String action = request.optString("action");

                    switch (action) {
                        case "login":
                            handleLogin(request);
                            break;
                        case "register":
                            handleRegister(request);
                            break;
                        case "get_config":
                            handleInitialConfig();
                            break;
                        default:
                            sendError("Unknown action");
                            break;
                    }
                } catch (Exception e ) {
                    System.out.println("Error processing request: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }   finally {
            closeConnection();
        }
    }

    // --- HELPER METHODS ---
    private void handleLogin(JSONObject request) throws IOException {
        String user = request.getString("userName");
        String pass = request.getString("password");

        boolean success = dbManager.loginUser(user, pass);
        JSONObject response = new JSONObject();
        response.put("action", "register");
        response.put("status", success ? "success" : "fail");
        response.put("message", success ? "Login Successful" : "Invalid Credentials");

        if (success) {
            loggedInUser = user;
        }
        out.writeUTF(response.toString());
    }

    private void handleRegister(JSONObject request) throws IOException {
        String user = request.getString("userName");
        String pass = request.getString("password");

        boolean success = dbManager.registerUser(user, pass);
        JSONObject response = new JSONObject();
        response.put("action", "register");
        response.put("status", success ? "success" : "fail");
        response.put("message", success ? "Registered" : "Username Taken");

        if (success) {
            loggedInUser = user;
        }
        out.writeUTF(response.toString());
    }

    private void handleInitialConfig() throws IOException {
        System.out.println("Sending initial configuration to " + loggedInUser);

        out.writeUTF(dbManager.fetchConfigurationFile().toString());
    }

    private void sendError(String message) throws IOException {
        JSONObject response = new JSONObject();
        response.put("status", "error");
        response.put("message", message);
        out.writeUTF(response.toString());
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
