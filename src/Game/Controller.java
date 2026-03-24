package Game;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class Controller {
	public int dir;
	public boolean shoot;

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
					 //defence_type  = 0;					 
					 System.out.println("Firewall "+0); 
					 shoot = true;

				 }/*
				 if (event.getCode() == KeyCode.W) {
					 defence_type  = 1;					 
					 System.out.println("Antivirus"+defence_type);

				 }
				 if (event.getCode() == KeyCode.E) {
					 defence_type  = 2;					 
					 System.out.println("Crypto shield"+defence_type); 

				 }*/
			}
			
		}));
		
		root.setOnKeyReleased((new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) { 
				dir = 0;
				shoot = false;
			}
		}));
	}

}
