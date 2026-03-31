package Application;
	
import Game.Mannager;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {	
			BorderPane root = new BorderPane();
			Mannager manneger = new Mannager(root);

			//Creating the game scenario:
			Scene scene = new Scene(root,1200, 760);
			primaryStage.setScene(scene);
			primaryStage.show();
			manneger.startLoop();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
