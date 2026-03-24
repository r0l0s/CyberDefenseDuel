package Game;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class Controller {
	public int dir;
	private boolean shoot = false;

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
					System.out.println("Antivirus"+1);		 
					shoot();
				 }
				 if (event.getCode() == KeyCode.W) {				 
					System.out.println("Antivirus"+2);
					shoot();
				 }

				 if (event.getCode() == KeyCode.E) {		 
					System.out.println("Crypto shield"+3); 
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
		System.out.println("Firewall "+0); 
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

}
