package network;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatabaseManager {
    private static final String DB_FILE = "database.json";
    private JSONArray usersArray;

    public DatabaseManager() {
        loadDatabase();
    }

    // 1. Reads the file or creates a new one if it doesn't exist
    private void loadDatabase() {
        try {
            File file = new File(DB_FILE);
            if (file.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(DB_FILE)));
                usersArray = new JSONArray(content);
            } else {
                usersArray = new JSONArray(); // Empty array if file doesn't exist
                saveDatabase();
            }
        } catch (Exception e) {
            System.out.println("Error loading database: " + e.getMessage());
            usersArray = new JSONArray();
        }
    }

    // 2. Saves the current JSONArray back to the file
    // Synchronized to prevent two threads from writing at the exact same time
    private synchronized void saveDatabase() {
        try (FileWriter file = new FileWriter(DB_FILE)) {
            file.write(usersArray.toString(4)); // Indent with 4 spaces for readability
            file.flush();
        } catch (IOException e) {
            System.out.println("Error saving database: " + e.getMessage());
        }
    }

    // 3. Authenticate User
    public synchronized boolean loginUser(String username, String password) {
        for (int i = 0; i < usersArray.length(); i++) {
            JSONObject user = usersArray.getJSONObject(i);
            if (user.getString("UserName").equals(username) && 
                user.getString("Password").equals(password)) {
                return true; // Credentials match
            }
        }
        return false; // User not found or wrong password
    }

    // 4. Register User
    public synchronized boolean registerUser(String username, String password) {
        // First check if user already exists
        for (int i = 0; i < usersArray.length(); i++) {
            if (usersArray.getJSONObject(i).getString("UserName").equals(username)) {
                return false; // User already exists!
            }
        }

        // Create new user object with default stats
        JSONObject newUser = new JSONObject();
        newUser.put("UserName", username);
        newUser.put("Password", password); // PDF Recommends hashing this later!
        newUser.put("Score", 0);
        newUser.put("GamesPlayed", 0);
        
        usersArray.put(newUser);
        saveDatabase(); // Persist changes to disk
        return true;
    }
}
