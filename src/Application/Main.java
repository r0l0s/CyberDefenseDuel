package Application;
	

import Game.Bullet;
import Game.Controller;
import Game.Enemy;
import Game.Player;
import estruc_datos.LinkedList;
import estruc_datos.StackList;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
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
	private StackList <Bullet> free_enemyBullets;
	private LinkedList <Bullet> used_enemyBullets;

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
			for(int i=1;i<2;i++){
				int lastx = (int) enemyList.get(i-1).get_colider().getX();
				Enemy enemy = new Enemy(lastx+100, 40);
				enemyList.insert(enemy);
				enemy.get_colider().setFill(Color.GREEN);
				root.getChildren().add(enemy.get_colider());
			}
			//Enemy bullets
			free_enemyBullets = new StackList<Bullet>(new Bullet(100.0f,-300.0f,1));
			for(int i=1;i<19;i++){
				free_enemyBullets.push(new Bullet(100.0f,-300.0f,1));
			}
			

			//Controller
			Controller inputs  = new Controller(root);
			//root.getChildren().add(balaMala.get_colider());

			Rectangle rect = new Rectangle(5,5,Color.RED);
			rect.setX(100.0f);
			rect.setY(150.0f);
			root.getChildren().add(rect);
			
			//Animator for Game loop  -<> Cambiar esto que está hecho con ChatGPT esto lo podría tener el mannager Btw
			AnimationTimer gameLoop = new AnimationTimer() {
				private long lastTime = 0;
				private double timerAcumulado = 0;
				//Temporales  o que hay que revisar
				private double tiempo2=0;
				public boolean enable_move = false;

				//Fue la prueba para colisiones lo voy a dejar así por ahora
				Bounds areaBusqueda = new BoundingBox(100, 0, 5, 5);

				//Prueba2 de bala jugador segunda manera
				int player_bulletIndex = 0;
				
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
					tiempo2+=deltaTime;

			        // 1. Capturar Input (Teclas, ratón)
			        // 2. Actualizar lógica (Movimiento, Colisiones)
			        // 3. Renderizar (JavaFX lo hace automático al mover nodos)
					//Input-Update-Colitions-Cleanup
					
			    	player.move(inputs.dir);
					//balaMala.move();
					
					//System.out.println(get_object(100, 150));
					
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
					
					//region 
					if (root.getChildren().contains(balaMala.get_colider())){

						if (balaMala.get_colider().getBoundsInParent().intersects(player.get_colider().getBoundsInParent())) {
							root.getChildren().remove(balaMala.get_colider());
							enable_move = false;
							player.damage(10);
							//balaMala.get_colider().setLayoutY(-300.0f);
							
						}
					}

					//Verificar las balas que están en juego del jugador
					if (player.get_usedBullets() != null){
						for(int i=0;i<player.get_usedBullets().getSize();i++){
							Bullet bullet = (Bullet) player.get_usedBullets().get(i);
    
							if (bullet == null) continue;
							
							if (!root.getChildren().contains(bullet.get_colider())) {
								root.getChildren().add(bullet.get_colider());
							}
							
							bullet.move();

							//Intento uno de colisiones entre balas
							Bounds pbd = bullet.get_colider().getBoundsInParent();
							Node obj = get_object(pbd.getMaxX(),pbd.getMinY(),bullet.get_colider());

							//if (bullet.get_colider().getBoundsInParent().intersects(balaMala.get_colider().getBoundsInParent())) {
							if (obj != null){
								Bullet enemyBullet = (Bullet) obj.getUserData();
								if(bullet.getType()==enemyBullet.getType()){
									root.getChildren().remove(enemyBullet.get_colider());
									free_enemyBullets.push(enemyBullet);
									used_enemyBullets.delete(used_enemyBullets.get(enemyBullet));
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

					//Verificar las balas enemigas que están en juego
					if (used_enemyBullets != null){
						//Comprobar como están y moverlas
						for(int i=0;i<used_enemyBullets.getSize();i++){
							Bullet bullet = (Bullet) used_enemyBullets.get(i);
    
							if (bullet == null) continue;
							
							if (!root.getChildren().contains(bullet.get_colider())) {
								root.getChildren().add(bullet.get_colider());
							}

							
							bullet.move();

							//Intento uno de colisiones entre balas
							if (bullet.get_colider().getBoundsInParent().intersects(player.get_colider().getBoundsInParent())) {
									System.out.println("CHOCÓ PLAYER");
									player.damage(10);
									root.getChildren().remove(bullet.get_colider());
									free_enemyBullets.push(bullet);
									used_enemyBullets.delete(i);
							}
						}
					}

					//Cambiar nombre de variable
					if(tiempo2>3.0){
						//Comprobaciones de tiempo
						//System.out.println("Pasaron 3 segundos"+balaMala.getType());
						tiempo2 =0;
						//Sacar del stack a la lista
						if(free_enemyBullets.getSize()!=0){
							Bullet bullet = free_enemyBullets.top();
							//Rectangle colider =  get_instance().get_colider();
							free_enemyBullets.pop();
							if (used_enemyBullets == null){
								used_enemyBullets = new LinkedList<Bullet>(bullet);
							}else{
								used_enemyBullets.insert(bullet);
							}
							//bullet.setPosition(colider.getLayoutX() + colider.getTranslateX(), this.get_colider().getLayoutY());
							bullet.setPosition(100.0f,-300.0f);
							bullet.setType(2);
							
						}
					}
					//endregion

					if(timerAcumulado<5.0){
						if (inputs.get_shoot()){
							inputs.set_shoot(false);
						}
						timerAcumulado = 0;
					}
			}
			private Node get_object(double x, double y,Node self){
				Node obj = root.lookupAll("*").stream()
					.filter(node -> node != root)
					.filter(node -> node != self)
					.filter(n -> n.getUserData() instanceof Bullet)
					.filter(node -> node.getBoundsInParent().contains(x, y))
					.findFirst()
					.orElse(null);
				return obj;
			}
		};
			

			//Creating the game scenario:
			Scene scene = new Scene(root,1200, 760);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			//primaryStage.setMaximized(true);
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
