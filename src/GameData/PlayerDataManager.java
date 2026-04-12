
package GameData;

import Application.GameManager;

// This class handles all data related to the player
public class PlayerDataManager {
    
    private String UserName;
    private String UserPassword;
    private GameMediator Mediator;

    private int CurrentScore = 0;
    private int CurrentHP = 100;

    public PlayerDataManager(){
        Mediator = GameManager.getMediator();
    }

    public void CreatePlayerProfile(String PlayerName, String Password){
        this.UserName = PlayerName;
        this.UserPassword = Password;
        System.out.println("From (PlayerDataManager) got data");
    }

    public void UpdateData(int newScore, int newHP) {
        CurrentScore = newScore;
        CurrentHP = newHP;
        System.out.println("From(PlayerDataManager: Sending player data)");
        Mediator.SendPlayerData(UserName, UserPassword, CurrentScore, CurrentHP);
    }




}
