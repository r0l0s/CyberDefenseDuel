package Application;
	
import Game.Bullet;
import Game.Controller;
import Game.Player;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.util.Duration;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//Instance of game objects
			Player player = Player.get_instance();
			player.get_colider().setFocusTraversable(true);
			
			//Bullet bullet = new Bullet(100.0f,300.0f,-1);
			
			BorderPane root = new BorderPane();
			root.getChildren().add(player.get_colider());
			
			//Controller
			Controller inputs  = new Controller(root);
			//root.getChildren().add(bullet.get_colider());
			
			//Animator for Game loop  -> Cambiar esto que está hecho con ChatGPT esto lo podría tener el mannager Btw
			AnimationTimer gameLoop = new AnimationTimer() {
				private long lastTime = 0;
				private double timerAcumulado = 0;
				
				@Override
			    public void handle(long now) {
					//Time configuration
					if (lastTime == 0) {
						lastTime = now;
						return;
					}
					double deltaTime = (now - lastTime) / 1_000_000_000.0;
					lastTime = now;
					timerAcumulado += deltaTime;

			        // 1. Capturar Input (Teclas, ratón)
			        // 2. Actualizar lógica (Movimiento, Colisiones)
			        // 3. Renderizar (JavaFX lo hace automático al mover nodos)
					
			    	player.move(inputs.dir);
					if (root.getChildren().contains(player.free_bullets.top().get_colider())){
						player.free_bullets.top().move();
					}

			    	if(inputs.get_shoot()) {
			    		System.out.println("Jugador disparó");
						root.getChildren().add(player.free_bullets.top().get_colider());
			    	}
					
					if(timerAcumulado<5.0){
						if (inputs.get_shoot()){
							inputs.set_shoot(false);
						}
						timerAcumulado = 0;
					}

			    
			}};
			
			//Creating the game scenario:
			Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			gameLoop.start();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
