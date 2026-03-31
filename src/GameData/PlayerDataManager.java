
package GameData;

import Application.GameManager;

// This class handles all data related to the player
public class PlayerDataManager {
    private String UserName;
    private String UserPassword;
    private GameMediator Mediator;

    public PlayerDataManager(){
        Mediator = GameManager.getMediator();
    }


    public void CreatePlayerProfile(String PlayerName, String Password){
        this.UserName = PlayerName;
        this.UserPassword = Password;
    }
    // This method gets called when the user presses the confirmation button
    // After entering the Username and Password
    private void EstablishServerConnection() {

    }
}
