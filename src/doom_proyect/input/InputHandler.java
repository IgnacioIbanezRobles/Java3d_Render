package doom_proyect.input;

import java.awt.RenderingHints.Key;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.w3c.dom.events.MouseEvent;

public class InputHandler implements KeyListener, FocusListener, MouseListener, MouseMotionListener {

	public boolean[] key = new boolean[68836];
	public static int MouseX;
	public static int MouseY;
	public static int MouseButton;
	
	@Override
	public void mouseDragged(java.awt.event.MouseEvent e) {

		
	}

	@Override
	public void mouseMoved(java.awt.event.MouseEvent e) {
		MouseX = e.getX();
		MouseY = e.getY();

		
	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		MouseButton = e.getButton();
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
	
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		for (int i = 0; i < key.length; i++) {
			key[i] = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {					//teclado
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length) {
			key[keyCode] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length) {
			key[keyCode] = false;
		}
	}
	
}
