package Game;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class Controller {
	public int dir;
	private boolean shoot = false;
	private int globalType = 0;

	public Controller(BorderPane root) {
		root.setOnKeyPressed((new EventHandler<KeyEvent>() { 
			public void handle(KeyEvent event) { 
				//Movement controls
				if (event.getCode() == KeyCode.A) {
					System.out.println("Move izq");
					dir = -1;
				 }
				if (event.getCode() == KeyCode.D) {
					 System.out.println("Move der"); 
					 dir = 1;
				 }
				 
				 //Attack controls
				 if (event.getCode() == KeyCode.Q) {
					globalType = 0;
					System.out.println("Firewall "+globalType); 	 
					shoot();
				 }
				 if (event.getCode() == KeyCode.W) {		
					globalType = 1;		 
					System.out.println("Antivirus"+globalType);
					shoot();
				 }

				 if (event.getCode() == KeyCode.E) {	
					globalType = 2;	 
					System.out.println("Crypto shield"+globalType); 
					shoot();

				 }
			}
			
		}));
		
		root.setOnKeyReleased((new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) { 
				dir = 0;
				shoot = false;
			}
		}));
	}

	private void shoot(){
		if(shoot==false){
			shoot = true;
		}
	}

	public boolean get_shoot(){
		return this.shoot;
	}
	
	public void set_shoot(boolean op){
		this.shoot = op;
	}

	public int get_type(){
		return this.globalType;
	}

}
