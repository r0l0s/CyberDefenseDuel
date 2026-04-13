
package GameData;

import Application.GameManager;
import Game.Player;

import org.json.JSONObject;

// This class handles all data related to the player
public class PlayerDataManager {
    
    private String UserName;
    private String UserPassword;
    private GameMediator Mediator;

    private int PlayerScore = 0;
    private int GamesPlayed = 0;

    private int CurrentHP = 100;

    public PlayerDataManager(){
        Mediator = GameManager.getMediator();
    }

    public void AcquirePlayerStats(JSONObject userStats) {
        PlayerScore = userStats.getInt("Score");
        GamesPlayed = userStats.getInt("GamesPlayed");

        System.out.println("From PlayerDataManager");
        System.out.println(UserName + " -> Score: " + PlayerScore );
        System.out.println(UserName + " -> Games Played: " + GamesPlayed );
    }

    public void IncreaseGamesPlayedCount() {
        GamesPlayed += 1;
    }

    public void SendEndGameStats() {
        JSONObject finalStats = new JSONObject();
        finalStats.put("Score", PlayerScore);
        finalStats.put("GamesPlayed", GamesPlayed);
        finalStats.put("action", "set_stats");
        System.out.println("Sending End Game stats to the server...");
        Mediator.SendEndGameStats(finalStats);
    }

    public void CreatePlayerProfile(String PlayerName, String Password){
        this.UserName = PlayerName;
        this.UserPassword = Password;
        System.out.println("From (PlayerDataManager) got data");
    }

    public void UpdateData(int newScore, int newHP) {
        PlayerScore = newScore;
        CurrentHP = newHP;
        System.out.println("From(PlayerDataManager: Sending player data)");
        Mediator.SendPlayerData(UserName, UserPassword, PlayerScore, CurrentHP);
    }




}
