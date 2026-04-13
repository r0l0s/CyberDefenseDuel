package network;

import java.io.*;
import java.net.*;
import org.json.*;

import Game.Player;

// This class handles all communication with ONE specific client.
// Because it implements Runnable, it can run in its own background thread.
public class ClientHandler implements Runnable {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String loggedInUser = null;
    private String PlayerState = null;
    private DatabaseManager dbManager;
    private Server server;

    // Constructor gets the socket from the main Server loop and the shared DatabaseManager
    public ClientHandler(Socket socket, DatabaseManager dbManager, Server server) {
        this.socket = socket;
        this.dbManager = dbManager;
        this.server = server;
    }

    public String GetLoggedUser() {
        return loggedInUser;
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

                        case "update":
                            handleUpdate(request);
                            break;

                        case "get_stats":
                            System.out.println("Fetching player stats....");
                            sendUserStats();
                            break;

                        case "set_stats":
                            setUserStats(request);
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
        response.put("action", "login");
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

    private void sendUserStats(){
        JSONObject stats = dbManager.GetPlayerStats(loggedInUser);
        sendData(stats.toString());;
    }


    public int GetFinalScore() {
        JSONObject stats = dbManager.GetPlayerStats(loggedInUser);
        return stats.getInt("Score");
    }

    public String GetPlayerSte() {
        return PlayerState;
    }

    public void sendFinalResult(boolean result) {
        JSONObject response = new JSONObject();
        response.put("action", "finalResult");
        response.put("result", result);
        sendData(response.toString());
    }

    private void setUserStats(JSONObject request) {
        PlayerState = request.getString("PlayerState");
        int FinalPlayerScore = request.getInt("Score");
        int TotalGamesPlayed = request.getInt("GamesPlayed");
        String UserName = loggedInUser;
        dbManager.SetPlayerStats(FinalPlayerScore, TotalGamesPlayed, UserName);
        server.SignalPlayerEnd();
    }

    private void handleInitialConfig() throws IOException {
        System.out.println("Sending initial configuration to " + loggedInUser);
        out.writeUTF(dbManager.fetchConfigurationFile().toString());
    }

    private void handleUpdate(JSONObject request){
        PlayerState = request.getString("PlayerState");
        int score = request.getInt("score");
        int hp = request.getInt("hp");

        JSONObject response = new JSONObject();
        response.put("action", "updateOponent");
        response.put("score", score);
        response.put("hp", hp);
        server.UpdateOponentData(loggedInUser, response);
    }

    public void handleOponentUpdate(JSONObject data) {
        System.out.println("Sending oponent data");
        sendData(data.toString());

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

    private void sendData(String payload) {
        try {
            if (out != null) {
                out.writeUTF(payload);
            }
        } catch (IOException e) {
            System.out.println("Failed to send: " + e.getMessage());
        }
    }
}
