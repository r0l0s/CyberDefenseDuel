package Game;

import org.json.JSONObject;
import GameData.GameMediator;
import estruc_datos.DoubleEndedList;
import estruc_datos.StackList;
import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class Mannager {

    private GameMediator Mediator;

    //region Configuración de Enemigos
    // Listado de enemigos para el juego
    private DoubleEndedList<Enemy> enemyList;
    // Listado de balas y proyectiles para el juego.
    private StackList<Bullet> free_enemyBullets;
    private DoubleEndedList<Bullet> used_enemyBullets;
    //Parámetros de spawn de balas y velocidad de las mismas
    private double spawnrate;
    private double attackspeed;
    //endregion

    //region Jugadores
    //Player 1
    private Player player = Player.get_instance();
    private int score = 0;
    private int actual_level = 1;

    //Player  2
    private int OponentScore = 0;
    private int OponentHP = 0;
    private Label lbl_enemyLife;
    private Label lbl_enemyScore;
    //endregion

    //Game loop man
    final AnimationTimer gameLoop;

    //Parámetros pasados por el config:
    int initialHP;
    double baseSpawnRate;
    double baseAttackSpeed;
    int scorePerKill;
    int difficultyStepScore;
    double spawnMultiplierPerLevel;
    double speedAddPerLevel;
    int[] damageByType;
    private boolean isGameOver = false;

    public Mannager(BorderPane root, GameMediator Mediator, JSONObject config,int player_index) { //level
        //Configuración del Manager
        this.Mediator = Mediator;
        Mediator.SetMannager(this);
        configureParams(config);

        //Configuración Jugador1 (Objeto dentro del juego)
        player.setHealth(initialHP);
        player.set_avatar(player_index);
        root.getChildren().add(player.get_colider());
        player.get_colider().setFocusTraversable(true);
        Controller inputs = new Controller(root);

        //region Datos del oponente  (Jugador2) ---------------------------------------------------------
        Label lbl_enemy = new Label("ENEMY:");
        lbl_enemy.setTextFill(Color.web("#8c00ff"));
        lbl_enemy.setFont(Font.font("Segoe UI", 20));

        lbl_enemyLife = new Label("HP: " + OponentHP);
        lbl_enemyLife.setTextFill(Color.web("#8c00ff"));
        lbl_enemyLife.setFont(Font.font("Segoe UI", 20));   

        lbl_enemyScore = new Label("Score:" + OponentScore);
        lbl_enemyScore.setTextFill(Color.web("#8c00ff"));
        lbl_enemyScore.setFont(Font.font("Segoe UI", 20));   

        HBox enemyLabels = new HBox(100, lbl_enemy, lbl_enemyLife,lbl_enemyScore);
        enemyLabels.setAlignment(Pos.TOP_LEFT);
        root.setTop(enemyLabels);
        //endregion -------------------------------------------------------------------------------

        //region Datos del jugador1 -------------------------------------------------------------
        Label lbl_playerHP = new Label("HP: "+player.getHealth());
        lbl_playerHP.setTextFill(Color.web("#ff0000"));
        lbl_playerHP.setFont(Font.font("Segoe UI", 20));

        Label lbl_score = new Label("Score: "+score);
        lbl_score.setTextFill(Color.web("#ff0000"));
        lbl_score.setFont(Font.font("Segoe UI", 20));
        
        Label lbl_level = new Label("Level: "+actual_level);
        lbl_level.setTextFill(Color.web("#ff0000"));
        lbl_level.setFont(Font.font("Segoe UI", 20));

        HBox playerLabels = new HBox(450, lbl_playerHP, lbl_score,lbl_level);
        root.setBottom(playerLabels); 
        //endregion -------------------------------------------------------------------------------

        //region Configuración de enemigos
        //Creación de enemigos
        enemyList = new DoubleEndedList<Enemy>(new Enemy(200, 55, 0));
        root.getChildren().add(enemyList.get(0).get_colider());
        for (int i = 1; i < 9; i++) {
            int lastx = (int) enemyList.get(i - 1).get_colider().getX();
            int ranint = (int) (Math.random() * ((2 - 0) + 1)) + 0;
            Enemy enemy = new Enemy(lastx + 100, 55, ranint);
            enemyList.insert(enemy);
            root.getChildren().add(enemy.get_colider());
        }
        //Creación de balas enemigas
        free_enemyBullets = new StackList<Bullet>(new Bullet(100.0f, 150, 1));
        for (int i = 1; i < 19; i++) {
            free_enemyBullets.push(new Bullet(100.0f, 150, 1));
            free_enemyBullets.top().set_damage(damageByType);
        }
        //endregion

        gameLoop = new AnimationTimer() {
            //Manejo del tiempo
            private long lastTime = 0;  //Para el tiempo delta
            private double timer_playerShoot = 0; //Para limitar los inputs de balas
            //Para el spawn de balas
            private double timer_spawn = 0; 
            private int enemy_count = 0;

            private double networkSyncTimer = 0.0; //Configuración con el server

            @Override
            public void handle(long now) {
                // 1. Capturar Input (Teclas, ratón)
                // 2. Actualizar lógica (Movimiento, Colisiones)
                // 3. Renderizar (Auto)

                // region DeltaTime configuracion
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;
                timer_playerShoot += deltaTime;
                timer_spawn += deltaTime;
                
                // Sending updates to the server every 50ms ------------------------------------
                networkSyncTimer += deltaTime;
                if (networkSyncTimer >= 0.5) {
                    networkSyncTimer = 0.0;
                    Mediator.UpdatePlayerData(score, player.getHealth());
                }
                // endregion

                //region Inputs:
                player.move(inputs.dir);

                if (inputs.get_shoot()) {
                    player.set_type(inputs.get_type());
                    player.shoot();
                }

                // Timer de disparos del jugador
                if (timer_playerShoot < 5.0) {
                    if (inputs.get_shoot()) {
                        inputs.set_shoot(false);
                    }
                    timer_playerShoot = 0;
                }
                //endregion

                // region Movimiento Enemigo
                for (int i = 0; i < enemyList.getSize(); i++) {
                    Enemy enemy = enemyList.get(i);
                    enemy.move();

                    double lastX = enemy.get_colider().getTranslateX();
                    if (lastX < -300 || lastX > 50) {
                        enemy.changeDir();
                    }
                }
                // endregion

                // region Lógica Balas del jugador
                // Verificar las balas que están en juego del jugador
                if (player.get_usedBullets() != null) {
                    for (int i = 0; i < player.get_usedBullets().getSize(); i++) {
                        Bullet bullet = (Bullet) player.get_usedBullets().get(i);

                        if (bullet == null)
                            continue;

                        if (!root.getChildren().contains(bullet.get_colider())) {
                            root.getChildren().add(bullet.get_colider());
                        }

                        bullet.move();

                        // Colisiones entre balas
                        Bounds pbd = bullet.get_colider().getBoundsInParent();
                        Node obj = get_object(pbd.getMaxX(), pbd.getMinY(), bullet.get_colider());

                        // PLAYER SCORE TRACKER ------------------------------------------------------------------
                        if (obj != null) {
                            Bullet enemyBullet = (Bullet) obj.getUserData();
                            if (bullet.getType() == enemyBullet.getType()) {
                                root.getChildren().remove(enemyBullet.get_colider());
                                free_enemyBullets.push(enemyBullet);
                                used_enemyBullets.delete(used_enemyBullets.get(enemyBullet));
                                score +=  scorePerKill;
                                lbl_score.setText("Score : "+score);
                            }

                            root.getChildren().remove(bullet.get_colider());
                            player.free_bullets.push(bullet);
                            player.get_usedBullets().delete(i);
                        }
                        // ----------------------------------------------------------------------------------------

                        //Comprobación si bala sigue dentro de los límites
                        if (bullet.get_colider().getTranslateY() < -350) {
                            root.getChildren().remove(bullet.get_colider());
                            player.free_bullets.push(bullet);
                            player.get_usedBullets().delete(i);
                        }
                    }
                }
                // endregion

                // region Lógica Balas del enemigo
                // Verificar las balas enemigas que están en juego
                if (used_enemyBullets != null) {
                    // Comprobar como están y moverlas
                    for (int i = 0; i < used_enemyBullets.getSize(); i++) {
                        Bullet bullet = (Bullet) used_enemyBullets.get(i);

                        if (bullet == null)
                            continue;

                        if (!root.getChildren().contains(bullet.get_colider())) {
                            root.getChildren().add(bullet.get_colider());
                        }

                        bullet.setVelocity(attackspeed);
                        bullet.move();
                        
                        // -- PLAYER HIT DETECTION -------------------------------------------------------------------------
                        if (bullet.get_colider().getBoundsInParent().intersects(player.get_colider().getBoundsInParent())) {
                            player.damage(bullet.get_damage());
                            lbl_playerHP.setText("HP: "+player.getHealth());

                            //Mediator.UpdatePlayerData(score, player.getHealth());
                            System.out.println("Player received damage");

                            root.getChildren().remove(bullet.get_colider());
                            free_enemyBullets.push(bullet);
                            used_enemyBullets.delete(i);
                        }
                        // ---------------------------------------------------------------------------------------------------

                        //Comprobación si bala sigue dentro de los límites
                        if (bullet.get_colider().getTranslateY() > 700) {
                            root.getChildren().remove(bullet.get_colider());
                            free_enemyBullets.push(bullet);
                            used_enemyBullets.delete(i);
                        }
                    }
                }

                //Timer del spawn
                if (timer_spawn > (1/spawnrate)) {
                    timer_spawn = 0;

                    //Animación del enemigo
                    for (int i = 0; i < enemyList.getSize(); i++) {
                        Enemy enemy = enemyList.get(i);
                        enemy.change_sprite();
                    }

                    // Sacar del stack a la lista
                    if (free_enemyBullets.getSize() != 0) {
                        Bullet bullet = free_enemyBullets.top();
                        Rectangle enemy = enemyList.get(enemy_count).get_colider();
                        free_enemyBullets.pop();
                        if (used_enemyBullets == null) {
                            used_enemyBullets = new DoubleEndedList<Bullet>(bullet);
                        } else {
                            used_enemyBullets.insert(bullet);
                        }

                        bullet.setPosition(enemy.getX() + enemy.getTranslateX() - 80, enemy.getY() - 70);
                        bullet.setType(enemyList.get(enemy_count).getAttackType());
                        enemy_count++;
                        if (enemy_count >= enemyList.getSize()) {
                            enemy_count = 0;
                        }
                    }
                }
                // endregion

                // Lugar donde se detecta un game over ------------------------------------------------------
                if (player.getHealth() <= 0 && !isGameOver) {
                    isGameOver = true;
                    this.stop();
                    Mediator.SetGameOver();
                }
                // ------------------------------------------------------------------------------------------

                //Cambio de dificultad y ajuste de parámetros
                if (score > difficultyStepScore){
                    actual_level = score/difficultyStepScore;
                    lbl_level.setText("Level: "+ actual_level);
                    attackspeed =  baseAttackSpeed + speedAddPerLevel*actual_level;
                    spawnrate =  baseSpawnRate * (Math.pow(spawnMultiplierPerLevel,actual_level));
                }
            }

            //Comprobación si un objeto está en cierta posición
            private Node get_object(double x, double y, Node self) {
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

    }

    private void configureParams(JSONObject Configuration){
        
        try{
            initialHP = Configuration.getInt("initialHp");
            baseSpawnRate = Configuration.getDouble("baseSpawnRate");
            baseAttackSpeed = Configuration.getDouble("baseAttackSpeed");
            scorePerKill = Configuration.getInt("scorePerKill");
            difficultyStepScore = Configuration.getInt("difficultyStepScore");
            spawnMultiplierPerLevel = Configuration.getDouble("spawnMultiplierPerLevel");
            speedAddPerLevel = Configuration.getDouble("speedAddPerLevel");
            damageByType = new int[]{ 10, 20, 10 };
            attackspeed = baseAttackSpeed;
            spawnrate = baseSpawnRate;

        } catch(Exception e ){
            System.out.println("Error in configuration file");
            e.printStackTrace();

        }
        
    }

    public void UpdateOponentData(JSONObject data) {
        System.out.println("Updating Oponent data UI ");
        OponentHP  = data.getInt("hp");
        OponentScore = data.getInt("score");

        javafx.application.Platform.runLater(() -> {
            if (lbl_enemyLife != null && lbl_enemyScore != null) {
                lbl_enemyLife.setText("HP: " + OponentHP);
                lbl_enemyScore.setText("Score:" + OponentScore);
            }
        });
    }

    public void startLoop(){
        this.gameLoop.start();
    }

}
