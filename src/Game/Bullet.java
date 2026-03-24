package Game;

import javafx.scene.paint.Color;

public class Bullet extends GameObject{
	//Bullet movement params
	private int type;
	private int vel = 4;
	private int dir;
	//Eliminar
	public String text  = "aAA";

	//Constructor
	public Bullet(float x, float y, int dir) {
		super(50.0f,50.0f);
		this.colider.setX(x);
		this.colider.setY(y);
		this.colider.setFill(Color.YELLOW);
		this.dir = dir;
	}
	
	public void setPosition(double x,double y){
		this.colider.setTranslateX(x);
		this.colider.setTranslateY(y);
	}

	//Change the position of the bullet on the Y axis.
	public void move() {
		//System.out.println(this.get_colider().getTranslateY() );
		double newX = this.colider.getTranslateY() + (this.dir * this.vel);
		this.colider.setTranslateY(newX);

	}

	public void colition(){

	}
}
