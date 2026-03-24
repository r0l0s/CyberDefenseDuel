package Game;

import javafx.scene.shape.Rectangle;


public class GameObject {
	//For collisions and movement
	protected Rectangle colider;
	//Add Sprite and animation parts
	
	//Constructor
	public GameObject(float w,float h) {
		colider = new Rectangle();
		this.colider.setWidth(w);
		this.colider.setHeight(h);
	}
	
	//Setters and Getters section:
	public Rectangle get_colider() {
		return this.colider;
	}
	

}
