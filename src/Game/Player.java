package Game;

import estruc_datos.DoubleEndedList;
import estruc_datos.LinkedList;

import estruc_datos.StackList;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Player extends GameObject{
	//The instance of object player itself -Singleton
	private static Player self;
	//Player important vars
	private float x;
	private float y;
	//Player stats: Life, movement
	private int health;
	private int vel;	
	//Stacks for available bullets
	public StackList<Bullet> free_bullets;
	private DoubleEndedList<Bullet> used_bullets;
	//Type of shot bullet 
	private int defence_type = 0;

	//Constructor
	private Player(float x, float y) {
		super(75.0f,75.0f,2);
		//Configuring the X and Y position of player and its velocity.
		this.colider.setX(x);
		this.x = x;
		this.colider.setY(y);
		this.y = y;
		this.vel =  4;
		//Misc
		this.health = 100;
		//Creating the available bullets.
		this.free_bullets = new StackList<Bullet>(new Bullet(this.x,this.y,-1));
		for(int i=0;i<9;i++) {
			this.free_bullets.push(new Bullet(this.x,this.y,-1));
		}
		//Configuring sprite
		this.sprites[0] = new Image("img/nave0.png");
		this.sprites[1] = new Image("img/nave1.png");
		this.colider.setFill(new ImagePattern(sprites[0]));
	}
	
	//Singleton - Returns instance of Player
	public static Player get_instance(){
		if (self  == null) {
			self = new Player(100.0f,620.0f);
		}
		return self;
	}
	
	//Getters and Setters section:
	public int getHealth() {
		return this.health;
	}
	
	public void setHeath(int hp){
		this.health = hp;
	}

	public int get_type() {
		return this.defence_type;
	}

	public void set_type(int type){
		this.defence_type  = type;
	}

	public DoubleEndedList<Bullet> get_usedBullets(){
		return this.used_bullets;
	}

	public Image get_sprite(int pos){
		return this.sprites[pos];
	}

	//Change the position of the player on the X axis.
	public void move(int dir) {
		double newX = this.colider.getTranslateX() + (dir * this.vel);
		this.colider.setTranslateX(newX);
		
		if (dir == 0){
			this.colider.setFill(new ImagePattern(sprites[0]));	
		}else{
			this.colider.setFill(new ImagePattern(sprites[1]));	
		}
	}

	//Create a object Bullet with the position of the X and Y player axis
	public void shoot(){
		Bullet bullet = this.free_bullets.top();
		Rectangle colider =  get_instance().get_colider();

		this.free_bullets.pop();
		if (this.used_bullets  == null){
			this.used_bullets = new DoubleEndedList<Bullet>(bullet);
		}else{
			this.used_bullets.insert(bullet);
		}

		bullet.setPosition(colider.getLayoutX() + colider.getTranslateX(), this.get_colider().getLayoutY());
		bullet.setType(this.defence_type);
	}
	
	public void damage(int value){
		this.health -= value;
		if (this.health<0){
			this.health = 0;
		}
	}
	
}

