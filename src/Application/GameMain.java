package Application;
	
import Game.Mannager;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class GameMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {	
			//Pane principal donde se almacena todo
			BorderPane root = new BorderPane();
			root.setStyle("-fx-background-color: linear-gradient(to bottom right, #0a1022, #0f1a36, #111f47);");
        	root.setPadding(new Insets(24));
			/*
			Label lbl_enemy = new Label("ENEMY:");
       		lbl_enemy.setTextFill(Color.web("#8c00ff"));
        	lbl_enemy.setFont(Font.font("Segoe UI", 20));

			BorderPane arriba = new BorderPane();
			arriba.setLeft(lbl_enemy);
			arriba.setPadding(new Insets(0, 0, 0, 0));
			root.setTop(arriba);
			*/
			
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
