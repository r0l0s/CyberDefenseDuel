package Game;

import estruc_datos.DoubleEndedList;
import estruc_datos.LinkedList;
import estruc_datos.StackList;
import javafx.animation.AnimationTimer;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Mannager {
    // Listado de enemigos para el juego
    private DoubleEndedList<Enemy> enemyList;
    // Listado de balas y proyectiles para el juego.
    private StackList<Bullet> free_enemyBullets;
    private DoubleEndedList<Bullet> used_enemyBullets;
    //private int[] damageByType = { 10, 20, 10 };

    // Jugador
    private Player player = Player.get_instance();

    //Game loop man
    final AnimationTimer gameLoop;
    //Parámetros pasados por el config:
    int initialHP;
    int baseSpawnRate;
    int baseAttackSpeed;
    int scorePerKill;
    int difficultyStepScore;
    int spawnMultiplierPerLevel;
    int speedAddPerLevel;
    int[] damageByType;

    public Mannager(BorderPane root) {
        damageByType = new int[]{ 10, 20, 10 };
        initialHP = 100;
        // Agregamos los componentes al juego:
        player.setHeath(initialHP);
        root.getChildren().add(player.get_colider());
        player.get_colider().setFocusTraversable(true);
        Controller inputs = new Controller(root);

        enemyList = new DoubleEndedList<Enemy>(new Enemy(200, 40, 0));
        root.getChildren().add(enemyList.get(0).get_colider());
        for (int i = 1; i < 9; i++) {
            int lastx = (int) enemyList.get(i - 1).get_colider().getX();
            int ranint = (int) (Math.random() * ((2 - 0) + 1)) + 0;
            Enemy enemy = new Enemy(lastx + 100, 40, ranint);
            enemyList.insert(enemy);
            root.getChildren().add(enemy.get_colider());
        }
        // Enemy bullets
        free_enemyBullets = new StackList<Bullet>(new Bullet(100.0f, 150, 1));
        for (int i = 1; i < 19; i++) {
            free_enemyBullets.push(new Bullet(100.0f, 150, 1));
            free_enemyBullets.top().set_damage(damageByType);
        }

        gameLoop = new AnimationTimer() {
            private long lastTime = 0;
            private double timerAcumulado = 0;
            // Temporales o que hay que revisar
            private double tiempo2 = 0;
            int enemy_count = 0;

            @Override
            public void handle(long now) {
                // 1. Capturar Input (Teclas, ratón)
                // 2. Actualizar lógica (Movimiento, Colisiones)
                // 3. Renderizar (JavaFX lo hace automático al mover nodos)
                // Input-Update-Colitions-Cleanup

                // region Time configuration
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;
                timerAcumulado += deltaTime;
                tiempo2 += deltaTime;
                // endregion

                // Inputs:
                player.move(inputs.dir);

                if (inputs.get_shoot()) {
                    System.out.println("Jugador disparó");
                    player.set_type(inputs.get_type());
                    player.shoot();
                }

                // Timers y calculos de tiempo
                if (timerAcumulado < 5.0) {
                    if (inputs.get_shoot()) {
                        inputs.set_shoot(false);
                    }
                    timerAcumulado = 0;
                }

                //Prueba animación sprites
                final ImageView spr_nave = new ImageView(player.get_sprite(0));

                // region Movimiento Enemigo
                // Enemy test: Izq: -300 Der:50
                for (int i = 0; i < enemyList.getSize(); i++) {
                    Enemy enemy = enemyList.get(i);
                    enemy.move();
                    double ULTIMATEX = enemy.get_colider().getTranslateX();
                    if (ULTIMATEX < -300 || ULTIMATEX > 50) {
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

                        // Intento uno de colisiones entre balas
                        Bounds pbd = bullet.get_colider().getBoundsInParent();
                        Node obj = get_object(pbd.getMaxX(), pbd.getMinY(), bullet.get_colider());

                        // if
                        // (bullet.get_colider().getBoundsInParent().intersects(balaMala.get_colider().getBoundsInParent()))
                        // {
                        if (obj != null) {
                            Bullet enemyBullet = (Bullet) obj.getUserData();
                            if (bullet.getType() == enemyBullet.getType()) {
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

                        bullet.move();

                        // Intento uno de colisiones entre balas
                        if (bullet.get_colider().getBoundsInParent().intersects(player.get_colider().getBoundsInParent())) {
                            System.out.println("CHOCÓ PLAYER");
                            player.damage(bullet.get_damage());
                            root.getChildren().remove(bullet.get_colider());
                            free_enemyBullets.push(bullet);
                            used_enemyBullets.delete(i);
                        }

                        if (bullet.get_colider().getTranslateY() > 700) {
                            System.out.println("Salió");
                            root.getChildren().remove(bullet.get_colider());
                            free_enemyBullets.push(bullet);
                            used_enemyBullets.delete(i);
                        }
                    }
                }

                // Cambiar nombre de variable
                if (tiempo2 > 1.5) {
                    // Comprobaciones de tiempo
                    // System.out.println("Pasaron 3 segundos"+balaMala.getType());
                    tiempo2 = 0;
                    // Sacar del stack a la lista
                    if (free_enemyBullets.getSize() != 0) {
                        Bullet bullet = free_enemyBullets.top();
                        Rectangle enemy = enemyList.get(enemy_count).get_colider();
                        // Rectangle colider = get_instance().get_colider();
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

                if (player.getHealth() <= 0) {
                    root.getChildren().remove(player.get_colider());
                }
            }

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

    public void startLoop(){
        this.gameLoop.start();
    }

}
