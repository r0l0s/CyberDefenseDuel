package Game;

import javafx.scene.paint.Color;

public class Bullet extends GameObject{
	//Bullet movement params
	private int type;
	private int vel = 4;
	private int dir;
	private Color[] bullet_color = {Color.RED,Color.BLUE,Color.YELLOW};; //Provisional
	private int[] damage = new int[3];

	//Constructor
	public Bullet(float x, float y, int dir) {
		super(50.0f,50.0f);
		this.colider.setX(x);
		this.colider.setY(y);
		this.colider.setFill(Color.YELLOW);
		this.dir = dir;
		this.colider.setUserData(this);
	}
	
	public void set_damage(int[]values){
		this.damage = values;
	}

	public int get_damage(){
		return this.damage[this.type];
	}

	public void setPosition(double x,double y){
		this.colider.setTranslateX(x);
		this.colider.setTranslateY(y);
	}

	public int getType(){
		return this.type;
	}

	public void setType(int type){
		this.type = type;
		get_colider().setFill(bullet_color[type]);
	}


	//Change the position of the bullet on the Y axis.
	public void move() {
		double newX = this.colider.getTranslateY() + (this.dir * this.vel);
		this.colider.setTranslateY(newX);
	}

	public void colition(){

	}
}
