package Game;

import javafx.scene.paint.Color;

public class Enemy extends GameObject{

    private double vel;
    private int dir;
    private int attack_type;

    public Enemy(float x, float y, int type) {
        super(75.0f,75.0f);
        this.colider.setX(x);
		this.colider.setY(y);
		this.vel =  2;
        this.dir = -1;
        this.attack_type = type;    
        //Preventivo
        switch (type) {
            case 0:
                this.colider.setFill(Color.RED);
                break;
            case 1:
                this.colider.setFill(Color.BLUE);
                break;
            case 2:
                this.colider.setFill(Color.YELLOW);
                break;
            default:
                this.colider.setFill(Color.GREEN);
                break;
        }
    }

    //Getters and Setters section:
    public int getAttackType(){
        return this.attack_type;
    }

    //Change the position of the bullet on the X axis.
	public void move() {
		double newX = this.colider.getTranslateX() + (this.dir * this.vel);
		this.colider.setTranslateX(newX);
	}

    public void changeDir(){
        dir *= -1;
    }
    
}
