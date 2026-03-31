package Game;

import javafx.scene.shape.Rectangle;
import javafx.scene.image.*;


public class GameObject {
	//For collisions and movement
	protected Rectangle colider;
	//Add Sprite and animation parts
	Image[] sprites;
	
	//Constructor
	public GameObject(float w,float h,int spr_cant) {
		colider = new Rectangle();
		this.colider.setWidth(w);
		this.colider.setHeight(h);
		this.sprites = new Image[spr_cant];
	}
	
	//Setters and Getters section:
	public Rectangle get_colider() {
		return this.colider;
	}
	

}
