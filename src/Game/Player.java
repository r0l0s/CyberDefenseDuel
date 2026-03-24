package Game;

import estruc_datos.StackList;

public class Player extends GameObject{
	//The instance of object player itself -Singleton
	private static Player self;
	//Player stats: Life, movement
	private int health;
	private int vel;	
	//Stacks for available bullets
	public StackList<Bullet> free_bullets;
	private StackList<Bullet> used_bullets;
	//Type of shot bullet 
	private int defence_type = 0;

	//Constructor
	private Player(float x, float y) {
		super(75.0f,75.0f);
		//Configuring the X and Y position of player and its velocity.
		this.colider.setX(x);
		this.colider.setY(y);
		this.vel =  4;
		//Creating the available bullets.
		this.free_bullets = new StackList<Bullet>(new Bullet(100.0f,300.0f,-1));
		for(int i=0;i<9;i++) {
			this.free_bullets.push(new Bullet(100.0f,300.0f,-1));
		}
		
	}
	
	//Singleton - Returns instance of Player
	public static Player get_instance(){
		if (self  == null) {
			self = new Player(100.0f,250.0f);
		}
		return self;
	}
	
	//Getters and Setters section:
	public int getHealth() {
		return this.health;
	}
	
	public int get_type() {
		return this.defence_type;
	}
	
	//Change the position of the player on the X axis.
	public void move(int dir) {
		double newX = this.colider.getTranslateX() + (dir * this.vel);
		this.colider.setTranslateX(newX);
	}
	
	
}

