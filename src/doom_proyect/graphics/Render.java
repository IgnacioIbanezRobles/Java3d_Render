package doom_proyect.graphics;

import doom_proyect.Display;

public class Render {
	public final int width;
	public final int height;
	public final int[] pixels;
	
	//private Display display;
	
	public Render(int width, int height) {		//Constructor width, height
		this.width = width;
		this.height = height;
		pixels = new int[width * height];		//Array pixels
		
	}
	
	public void draw(Render render, int xOffset, int yOffset) { 	//Valores del metodo render en clase Screen
		for (int y = 0; y < render.height; y++) {
			int yPix = y + yOffset;
			if (yPix < 0 || yPix >= Display.HEIGHT ) {
				continue;
			}
			
			for (int x = 0; x < render.width; x++) {
				int xPix = x + xOffset;
				if (xPix < 0 || xPix >= Display.WIDTH ) {
					continue;
				}
				
				int alpha = render.pixels[x + y * render.width];
				if (alpha > 0) {		//Si no hay nada que renderizar no se hace
					pixels[xPix + yPix * width] = alpha;
				}
				
				//System.out.println("x: " + x + "y: "+ y);
			}
		}
	}
}
