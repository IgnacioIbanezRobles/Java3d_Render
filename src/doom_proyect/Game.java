package doom_proyect;

import java.awt.event.KeyEvent;

import doom_proyect.input.Controller;

public class Game {
	
	public int time;
	public Controller controls;
	
	
	public Game() {
		controls = new Controller();
	}
	
	public void tick(boolean[] key) {
		time++;
		boolean forward = key[KeyEvent.VK_W];
		boolean back = key[KeyEvent.VK_S];
		boolean left = key[KeyEvent.VK_A];
		boolean right = key[KeyEvent.VK_D];
		boolean jump = key[KeyEvent.VK_SPACE];
		boolean crouch = key[KeyEvent.VK_CONTROL];
		boolean sprint = key[KeyEvent.VK_SHIFT];
		
		

		controls.tick(forward, back, left, right, jump, crouch, sprint);
	}
}
