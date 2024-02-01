package doom_proyect;

import java.applet.Applet;
import java.awt.BorderLayout;

public class doomApplet extends Applet{
	private static final long serialVesionUID = 1L;
	
	private Display display = new Display();
	
	public void init() {				//
		setLayout(new BorderLayout());
		add(display);
	}
	
	public void start() {
		display.start();
	}
	
	public void stop() {
		display.stop();
	}
}
