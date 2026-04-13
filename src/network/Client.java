

package network;

import java.io.*;
import java.net.*;
import org.json.*;
import java.util.function.Consumer;
import javafx.application.Platform;
import GameData.GameMediator;

public class Client {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private Thread listernerThread;

    // Callbacks for the listener thread
    private Consumer<Boolean> loginCallback;
    private Consumer<Boolean> registerCallback;
    private Consumer<JSONObject> configCallback;
    private Consumer<Boolean> OponentEndCallback;

    // Mediator
    private GameMediator mediator;

    public Client(GameMediator mediator){
        this.mediator = mediator;
    }

    // ==========================================================================
    // CONNECTION AND LISTENER STARTUP
    // ==========================================================================
    public void Connect() {
        if (socket != null && !socket.isClosed()) {
            return; // Nothing happens because the socket is already connected
        }
        try {
            System.out.println("Connecting to server...");
            socket = new Socket("127.0.0.1", 4000);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            System.out.println("Connected to server!");

            startListening();
        } catch (IOException e ) {
            System.out.println("Failed to connect: " + e.getMessage());
        }
    }

    // ============================================================================
    // RECEIVER (Background thread)
    // ============================================================================
    private void startListening() {
        listernerThread = new Thread(() -> {
            while (true) {
                try {
                    // Blocking wait: The thread pauses here until the server sends data
                    String responseLine = in.readUTF();
                    JSONObject response = new JSONObject(responseLine);
                    System.out.println("Server sent: " + responseLine);

                    // Using "action" for authentication replies / "type" for game data
                    // The syntax is similar to the optional failure context in Verse
                    String key = response.optString("action", response.optString("type"));

                    // This is the router
                    switch (key) {
                        case "login":
                            handleLoginResponse(response);
                            break;

                        case "register":
                            handleRegisterResponse(response);
                            break;

                        case "CONFIG":
                            System.out.println("Received game configuration!");
                            handleInitialConfigurationResponse(response);
                            break;

                        case "updateOponent":
                            System.out.println("Updating oponent data");
                            handleOponentUpdate(response);
                            break;

                        case "stats":
                            System.out.println("Received User Stats");
                            handlePlayerStats(response);
                            break;

                        case "finalResult":
                            handleFinalResponse(response);
                            break;


                        default:
                            System.out.println("Received unknown message key " + key);
                    }
                } catch (IOException | JSONException e ) {
                    System.out.println("Connection lost to server");
                    closeConnection();
                    break;
                }
            }
        });
        listernerThread.start();
    }

    // --- Response Handlers ---
    private void handleLoginResponse(JSONObject response){
        boolean isSuccess = response.getString("status").equals("success");
        System.out.println(response.getString("message"));

        if (loginCallback != null) {
            Consumer<Boolean> callbackToRun = this.loginCallback;
            this.loginCallback = null;
            Platform.runLater(() -> callbackToRun.accept(isSuccess));
        }
    }

    private void handleFinalResponse(JSONObject response) {
        boolean isWin = response.getBoolean("result");
        System.out.println("Got the end result: " + isWin);
        if (OponentEndCallback != null) {
            Consumer<Boolean> callbackToRun = this.OponentEndCallback;
            this.OponentEndCallback = null;
            Platform.runLater(() -> callbackToRun.accept(isWin));
        }
    }

    private void handleRegisterResponse(JSONObject response) {
        boolean isSuccess = response.getString("status").equals("success");
        System.out.println(response.getString("message"));

        if (registerCallback != null) {
            Consumer<Boolean> callbackToRun = this.registerCallback;
            this.registerCallback = null;
            Platform.runLater(() -> callbackToRun.accept(isSuccess));
        }
    }

    private void handleInitialConfigurationResponse(JSONObject response){
        System.out.println("Received Configuration file");
        if (configCallback != null) {
            Consumer<JSONObject> callbackToRun = this.configCallback;
            this.configCallback = null;
            Platform.runLater(() -> callbackToRun.accept(response));
        }
        //mediator.setConfiguration(response);
    }

    private void handleOponentUpdate(JSONObject response){
        mediator.UpdateOponentData(response);

    }

    // ============================================================================
    // SENDERS (Main UI thread)
    // ============================================================================
    public void Login(String userName, String password, Consumer<Boolean> onResult) {
        Connect();
        this.loginCallback = onResult;

        JSONObject object = new JSONObject();
        object.put("action", "login");
        object.put("userName", userName);
        object.put("password", password);

        sendData(object.toString());
    }

    public void Register(String userName, String password, Consumer<Boolean> onResult) {
        Connect();
        this.registerCallback = onResult;

        JSONObject object = new JSONObject();
        object.put("action", "register");
        object.put("userName", userName);
        object.put("password", password);

        sendData(object.toString());
    }

    public void SignalGameEnd(Consumer<Boolean> onResult) {
        this.OponentEndCallback = onResult;

        JSONObject object = new JSONObject();
        object.put("action", "end");
    }

    public void getConfiguration(Consumer<JSONObject> onResult){
        this.configCallback = onResult;
        JSONObject object = new JSONObject();
        object.put("action", "get_config");
        sendData(object.toString());
    }

    public void SendPlayerData (String UserName, String Password, int CurrentScore, int CurrentHP, String PlayerState) {
        JSONObject object = new JSONObject();
        object.put("action", "update");
        object.put("userName", UserName);
        object.put("password", Password);
        object.put("score", CurrentScore);
        object.put("hp", CurrentHP);
        object.put("PlayerState", PlayerState);
        sendData(object.toString());
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

    public void SendEndGameStats(JSONObject finalStats) {
        sendData(finalStats.toString());
    }

    public void RequestPlayerStats() {
        JSONObject request = new JSONObject();
        request.put("action", "get_stats");
        sendData(request.toString());
    }

    private void handlePlayerStats(JSONObject response) {
        mediator.ProcessPlayerStats(response);
    }

    public void closeConnection() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
            System.out.println("Connection closed");
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }
}