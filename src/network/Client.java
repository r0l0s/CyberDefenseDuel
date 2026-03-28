package network;

import java.io.*;
import java.net.*;


// This class
public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("192.168.100.7", 5000);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to Server");
            String line;
            while (!(line = br.readLine()).equals("End")) {
                out.writeUTF(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}