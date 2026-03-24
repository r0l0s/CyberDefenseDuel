package Application;
	

import Game.Bullet;
import Game.Controller;
import Game.Enemy;
import Game.Player;
import estruc_datos.LinkedList;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
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
	//Esto iría en el mannager
	LinkedList<Enemy> enemyList;

	@Override
	public void start(Stage primaryStage) {
		try {
			//Instance of game objects
			Player player = Player.get_instance();
			player.get_colider().setFocusTraversable(true);
			
			Bullet balaMala = new Bullet(100.0f,-300.0f,1);
			balaMala.get_colider().setFill(Color.GREEN);
			balaMala.setType(2);

			BorderPane root = new BorderPane();
			root.getChildren().add(player.get_colider());
			
			//Enemy test
			enemyList = new LinkedList<Enemy>(new Enemy(200, 40));
			root.getChildren().add(enemyList.get(0).get_colider());
			enemyList.get(0).get_colider().setFill(Color.GREEN);
			for(int i=1;i<5;i++){
				int lastx = (int) enemyList.get(i-1).get_colider().getX();
				Enemy enemy = new Enemy(lastx+100, 40);
				enemyList.insert(enemy);
				enemy.get_colider().setFill(Color.GREEN);
				root.getChildren().add(enemy.get_colider());
			}

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
					
					//Enemy test: Izq: -300 Der:50
					for(int i=0;i<enemyList.getSize();i++){
						Enemy enemy = enemyList.get(i);
						enemy.move();
						double ULTIMATEX = enemy.get_colider().getTranslateX();
						if(ULTIMATEX < -300 || ULTIMATEX > 50){
							enemy.changeDir();
						}
					}

			    	if(inputs.get_shoot()) {
			    		System.out.println("Jugador disparó");
						player.set_type(inputs.get_type());
						player.shoot();
			    	}

					if (root.getChildren().contains(balaMala.get_colider())){
						if (balaMala.get_colider().getBoundsInParent().intersects(player.get_colider().getBoundsInParent())) {
							//root.getChildren().remove(balaMala.get_colider());
							balaMala.get_colider().setLayoutY(-300.0f);
							player.damage(10);
						}
					}

					//Verificar las balas que están en juego
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
								if(bullet.getType()==balaMala.getType()){
									root.getChildren().remove(balaMala.get_colider());
								}

								root.getChildren().remove(bullet.get_colider());
								player.free_bullets.push(bullet);
								player.get_usedBullets().delete(i);
							}

							if (bullet.get_colider().getTranslateY() < -350) {
								System.out.println(bullet.getType());
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
			Scene scene = new Scene(root,1000,1000);
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
