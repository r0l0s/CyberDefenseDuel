package network;

import java.io.*;
import java.net.*;
import org.json.*;

public class Client {
    
    // We store the socket and output stream as fields so other methods 
    // can use them later to send messages to the server.
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    public void Login(String UserName, String Password){
        SendAuthenticationRequest("Login", UserName, Password);
    }

    public void Register(String UserName, String Password){
        SendAuthenticationRequest("Register", UserName, Password);
    }

    private void SendAuthenticationRequest(String Action, String UserName, String Password){
        // Creating the JSON payload
        JSONObject object = new JSONObject();
        object.put("Action", Action);
        object.put("UserName", UserName);
        object.put("Password", Password);
        String Payload = object.toString();

        System.out.println("Attempting to " + Action + "with" + UserName);

        // Starting the thread
        Thread ClientThread = new Thread(() -> {
            try{
                if(socket == null || socket.isClosed()){
                    System.out.println("Connecting to server...");
                    socket = new Socket("127.0.0.1", 4000);
                    out = new DataOutputStream(socket.getOutputStream());
                    in = new DataInputStream(socket.getInputStream());
                    System.out.println("Connected to server!");
                }
                // Sending JSON packet to the server
                out.writeUTF(Payload);

                // Waiting for the server's reply
                String ResponseLine = in.readUTF();
                JSONObject Response = new JSONObject(ResponseLine);

                System.out.println("Server replied: " + Response.getString("message"));

                //Notification for the mediator
                if (Response.getString("Status").equals("Success")){
                    // Logic for a successful login/register
                }
                else{
                    // Logic for an unsuccessful login/register
                }
            } catch (IOException | JSONException e){
                System.out.println("Communication error: " + e.getMessage());
                CloseConnection();
            }
        });
        ClientThread.start();

    }

    public void CloseConnection(){
        try{
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
            System.out.println("Connection closed.");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
