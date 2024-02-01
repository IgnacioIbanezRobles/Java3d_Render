package doom_proyect.input;

import doom_proyect.Display;

public class Controller {
	public double x, y, z, rotation, xa, za, rotationa = 0, rotationY = 0;
	public static boolean turnLeft = false;
	public static boolean turnRight = false;
	public static boolean turnUp = false;
	public static boolean turnDown = false;
	public static boolean walk = false;
	public static boolean crouchWalk = false;
	public static boolean sprintWalk = false;
	
	public void tick(boolean forward,boolean back,boolean left,boolean right, boolean jump, boolean crouch, boolean sprint) {
		double rotationSpeedX = 0.002 * Display.mouseSpeed;
		double rotationSpeedY = 0.025;
		double walkSpeed = 0.5;
		double jumpHeight = 0.5;
		double crouchHeight = 0.4;
		double xMove = 0;
		double zMove = 0;
		
		
		if (forward) {
			zMove++;
			walk = true;
		}
		if (back) {
			zMove--;
			walk = true;
		}
		if (left) {
			xMove--;
			walk = true;
		}
		if (right) {
			xMove++;
			walk = true;
		}
		if (turnLeft) {
			if (InputHandler.MouseButton == 3) {
				
			}else {
				rotationa -= rotationSpeedX;
			}
			
		}
		if (turnRight) {
			if (InputHandler.MouseButton == 3) {
				
			}else {
				rotationa += rotationSpeedX;
			}
			
		}
		if (turnUp) {
			rotationY -= rotationSpeedY * Display.mouseSpeedY;
			
		}
		if (turnDown) {
			rotationY += rotationSpeedY * Display.mouseSpeedY;
			
		}
		
		if (jump) {
			y += jumpHeight;
			sprint = false;
		}
		
		if (crouch) {
			y -= crouchHeight;
			sprint = false;
			walkSpeed *= 0.5; 
			crouchWalk = true;
		}
		
		if (sprint) {
			walkSpeed *= 1.5;
			sprintWalk = true;
		}
		
		if (!forward && !back && !left && !right ) {
			walk = false;
		}
		
		if (!crouch) {
			crouchWalk = false;
		}
		
		if (!sprint) {
			sprintWalk = false;
		}
		
		
		
		xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed;
		za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * walkSpeed;
		
		x += xa;
		y *= 0.9;
		z += za;
		xa *= 0.1;
		za *= 0.1;
		
		
		
		rotation += rotationa;
		rotationa *= 0.5;
		
		
		
	}
}
