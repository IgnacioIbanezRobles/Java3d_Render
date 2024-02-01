package doom_proyect.graphics;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

public class Texture {		
	public static Render floor = loadBitmap("res/textures/floor.png");
	public static Render wall = loadBitmap("res/textures/wall.png");
	
	public static Render loadBitmap(String fileName) {		//Renderizar imagen
		try {
			//BufferedImage image = ImageIO.read(Texture.class.getResource(fileName));
			BufferedImage image = ImageIO.read(new FileInputStream(fileName));
			int width = image.getWidth();
			int height = image.getHeight();
			Render result = new Render(width, height);
			image.getRGB(0, 0, width, height, result.pixels, 0, width);
			return result;
		} catch (Exception e) {
			System.out.println("CRASH!");
			throw new RuntimeException(e);
		
		}
	}
}
