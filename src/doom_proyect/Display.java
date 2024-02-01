package doom_proyect;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import doom_proyect.graphics.Render;
import doom_proyect.graphics.Screen;
import doom_proyect.input.Controller;
import doom_proyect.input.InputHandler;

public class Display extends Canvas implements Runnable{
	public static final int WIDTH = 1000;			//Pixeles X pantalla
	public static final  int HEIGHT = 800;			//Pixeles Y pantalla
	public static final String TITLE = "Java_DOOM";
	
	private Thread thread;
	private Screen screen;
	private Game game;
	private BufferedImage img;
	private boolean running = false;
	//private Render render;
	private int[] pixels;
	
	private InputHandler input;
	private int newXMouse = 0;
	private int oldXMouse = 0;
	private int newYMouse = 0;
	private int oldYMouse = 0;
	private int fps;
	
	public static double mouseSpeed;
	public static double mouseSpeedY;
	
	
	public Display() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		
		//render = new Render(WIDTH, HEIGHT);
		screen = new Screen(WIDTH, HEIGHT);
		game = new Game();
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
		
		input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
	}
	
	public synchronized void start() {	//Start
		if (running) 
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
		
	}
	
	public synchronized void stop() {	//Metodo stop
		if(!running) 
			return;
		running = false;
		try {
			thread.join();		
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void run() {
		int frames = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;
		
		while (running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0;
			requestFocus();				//Click is not necessary
			
			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount ++;
				if (tickCount % 60 == 0) {
					System.out.println(frames + "fps");
					//fps = String.valueOf(frames);
					fps = frames;
					previousTime += 1000;
					frames = 0;
					
				}
			}
			if (ticked) {
				render();
				frames++;
			}
			render();
			frames ++;
			
			//System.out.println("x: " +InputHandler.MouseX + "; Y: " +InputHandler.MouseY );
		  //Mouse X
			newXMouse = InputHandler.MouseX;

			if (newXMouse > oldXMouse) {
				//System.out.println("Right");
				Controller.turnRight = true;
			}
			if (newXMouse < oldXMouse) {
				//System.out.println("Left");
				Controller.turnLeft = true;
			}
			if (newXMouse == oldXMouse) {
				//System.out.println("Still");
				Controller.turnLeft = false;
				Controller.turnRight = false;
			}
			
			mouseSpeed = Math.abs(newXMouse - oldXMouse); 	//Calcular velocidad raton
			
			oldXMouse = newXMouse;
			
		//Mouse Y	
			newYMouse = InputHandler.MouseY;
			if (newYMouse < oldYMouse) {
				System.out.println("UP");
				Controller.turnUp = true;
			}
			if (newYMouse > oldYMouse) {
				System.out.println("Down");
				Controller.turnDown = true;
			}
			if (newYMouse == oldYMouse) {
				//System.out.println("Still");
				Controller.turnUp = false;
				Controller.turnDown = false;
			}
			
			mouseSpeedY = Math.abs(newYMouse - oldYMouse); 	//Calcular velocidad raton
			oldYMouse = newYMouse;
			
			
			
		}
	}
	
	private void tick() {
		game.tick(input.key);
	}
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.render(game);		//----RENDER---------
		
		for (int i = 0; i< WIDTH*HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH , HEIGHT , null);
		  //FPS pantalla
			g.setFont(new Font("Verdana", 0, 40));
			g.setColor(Color.yellow);
			g.drawString(fps +" FPS" , 20, 40);
		g.dispose();
		bs.show();
		
	}
	
	public static void main(String[] args) {
		//Cursor nulo
		BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);		
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0,0), "blank");
		
		Display game = new Display();
		JFrame frame = new JFrame();
		frame.add(game);
		frame.pack();
		frame.getContentPane().setCursor(blank);			//Cursor invisible
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);			//Redimensionar ventana
		frame.setVisible(true);
		
		System.out.println("Runing...");
		
		game.start();
	}
}
