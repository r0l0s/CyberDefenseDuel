package Game;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class Enemy extends GameObject{

    private double vel;
    private int dir;
    private int attack_type;

    public Enemy(float x, float y, int type) {
        super(75.0f,75.0f,3);
        this.colider.setX(x);
		this.colider.setY(y);
		this.vel =  2;
        this.dir = -1;
        this.attack_type = type;    

        //Enemy sprites:
        this.sprites[0] = new Image("img/botnet.png");
		this.sprites[1] = new Image("img/malware0.png");
        this.sprites[2] = new Image("img/crendentia0.png");
        //Preventivo
        switch (type) {
            case 0:
                this.colider.setFill(new ImagePattern(sprites[0]));
                break;
            case 1:
                this.colider.setFill(new ImagePattern(sprites[1]));
                break;
            case 2:
                this.colider.setFill(new ImagePattern(sprites[2]));
                break;
            default:
                this.colider.setFill(new ImagePattern(sprites[0]));
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
