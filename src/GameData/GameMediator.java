
package GameData;

import java.util.Optional;
import Game.Player;
import network.Client;

// This class is the hub of communication for all the other classes
// that establish the overall player logic system

public class GameMediator {
    private Optional<Player> MaybePlayer = Optional.empty();
    private Optional<PlayerDataManager> MaybePlayerDataManager = Optional.empty();
    private Optional<Client> MaybeClient = Optional.empty();


    // These methods are to set each member for the mediator ---------------------------------
    public void SetPlayer(Player PlayerRef){
        this.MaybePlayer = Optional.ofNullable(PlayerRef);
    }
    public void SetPlayerDataManager(PlayerDataManager PlayerDataManagerRef){
        //this.MaybePlayerDataManager = Optional.ofNullable(PlayerDataManagerRef);
    }
    public void SetClient(Client ClientRef){
        this.MaybeClient = Optional.ofNullable(ClientRef);
    }
    // ---------------------------------------------------------------------------------------


    public void ClientLogin(String UserName, String Password){
        System.out.println("Attempting client login procedure....");
        MaybeClient.ifPresent(Client -> Client.Login(UserName, Password));
    }

    public void ClientRegister(String UserName, String Password){
        System.out.println("Attempting client register procedure....");
        MaybeClient.ifPresent(Client -> Client.Register(UserName, Password));
    }

}
