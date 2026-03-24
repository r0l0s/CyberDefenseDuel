package Game;

public class Enemy extends GameObject{

    private int vel;
    private int dir;

    public Enemy(float x, float y) {
        super(75.0f,75.0f);
        this.colider.setX(x);
		this.colider.setY(y);
		this.vel =  2;
        this.dir = -1;
    }

    //Getters and Setters section:

    //Change the position of the bullet on the X axis.
	public void move() {
		double newX = this.colider.getTranslateX() + (this.dir * this.vel);
		this.colider.setTranslateX(newX);
	}

    public void changeDir(){
        dir *= -1;
    }
    
}
