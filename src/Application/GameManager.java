
package Application;

import GameData.GameMediator;
import GameData.PlayerDataManager;
import javafx.application.Application;
import network.Client;
// This class should contain and initialize all components that make the game
public class GameManager {
    private static GameMediator Mediator;

    public static void main(String[] args){
        Mediator = new GameMediator();

        network.Client client = new network.Client();
        Mediator.SetClient(client);

        PlayerDataManager PDM = new PlayerDataManager();
        Mediator.SetPlayerDataManager(PDM);

        Application.launch(GameIU.class, args);
    }

    public static GameMediator getMediator() {
        return Mediator;
    }

}
