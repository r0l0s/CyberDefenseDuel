
package GameData;

import java.util.Optional;

import Game.Mannager;
import Game.Player;
import network.Client;
import org.json.JSONObject;

import java.util.function.Consumer;

// This class is the hub of communication for all the other classes
// that establish the overall player logic system

public class GameMediator {
    private Optional<Player> MaybePlayer = Optional.empty();
    private Optional<PlayerDataManager> MaybePlayerDataManager = Optional.empty();
    private Optional<Client> MaybeClient = Optional.empty();
    private Optional<Mannager> MaybeMannager = Optional.empty();


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


    public void ClientLogin(String UserName, String Password, Consumer<Boolean> onResult){
        System.out.println("Attempting client login procedure....");
        MaybeClient.ifPresentOrElse(
                Client -> Client.Login(UserName, Password, onResult),
                () -> onResult.accept(false)); // If the client is null, fail immediately
    }

    public void SetMannager(Mannager MannagerRef) { this.MaybeMannager = Optional.ofNullable(MannagerRef);}

    public void ClientRegister(String UserName, String Password, Consumer<Boolean> onResult){
        System.out.println("Attempting client register procedure....");
        MaybeClient.ifPresentOrElse(
                Client -> Client.Register(UserName, Password, onResult),
                () -> onResult.accept(false)); // Same logic as ClientLogin
    }

    public void getInitialConfiguration(){
        MaybeClient.ifPresent(
                Client -> Client.getConfiguration());
    }

}
