package Application;
	
import Game.Bullet;
import Game.Controller;
import Game.Player;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
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
			
			Bullet balaMala = new Bullet(100.0f,-300.0f,1);
			balaMala.get_colider().setFill(Color.GREEN);

			BorderPane root = new BorderPane();
			root.getChildren().add(player.get_colider());
			
			//Controller
			Controller inputs  = new Controller(root);
			root.getChildren().add(balaMala.get_colider());

			Rectangle rect = new Rectangle(5,5,Color.RED);
			rect.setX(100);
			rect.setY(0);
			root.getChildren().add(rect);
			
			//Animator for Game loop  -<> Cambiar esto que está hecho con ChatGPT esto lo podría tener el mannager Btw
			AnimationTimer gameLoop = new AnimationTimer() {
				private long lastTime = 0;
				private double timerAcumulado = 0;

				//Fue la prueba para colisiones lo voy a dejar así por ahora
				Bounds areaBusqueda = new BoundingBox(100, 0, 5, 5);
				
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
					balaMala.move();
					
					//System.out.println(root.contains);


			    	if(inputs.get_shoot()) {
			    		System.out.println("Jugador disparó");
						player.shoot();
			    	}

					//System.out.println(player.get_usedBullets());
					if (player.get_usedBullets() != null){
						for(int i=0;i<player.get_usedBullets().getSize();i++){
							Bullet bullet = (Bullet) player.get_usedBullets().get(i);
    
							if (bullet == null) continue;
							
							if (!root.getChildren().contains(bullet.get_colider())) {
								root.getChildren().add(bullet.get_colider());
							}
							
							bullet.move();

							//Intento uno de colisiones entre balas
							if (bullet.get_colider().getBoundsInParent().intersects(balaMala.get_colider().getBoundsInParent())) {
								System.out.println("CHOCÓ");
								root.getChildren().remove(bullet.get_colider());
								root.getChildren().remove(balaMala.get_colider());
								player.free_bullets.push(bullet);
								player.get_usedBullets().delete(i);
							}else{
								System.out.println("Nahhh");
							}

							if (bullet.get_colider().getTranslateY() < -350) {
								root.getChildren().remove(bullet.get_colider());
								player.free_bullets.push(bullet);
								player.get_usedBullets().delete(i);
        					}
						}
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
