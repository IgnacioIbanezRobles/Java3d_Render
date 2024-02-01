package doom_proyect.graphics;

import java.util.Random;

import doom_proyect.Game;
import doom_proyect.input.Controller;

public class Render3D extends Render{
	
	public double[] zBuffer;							//Depth
	private double renderDistance = 5000;				//Distancia renderizado
	private double forward, right, up,  cosine, sine, walking;
	
	public Render3D(int width, int height) {			//Constructor clase Render3D
		super(width, height);
		zBuffer = new double[width * height];
	}
		
	public void floor(Game game) {						//Generar imagen
		
		double floorPosition = 8;
		double celiningPosition = 8;					//Altura techo
		
		forward = game.controls.z;
		//double forward = game.time % 100 / 20.0;		//Avance automatico de camara
		
		right = game.controls.x;
		up = game.controls.y;
		walking = 0;
		
		
		
		//double rotation = game.time / 100.0;			//Velocidad rotacion
		//double rotation =  Math.sin(game.time / 400. * 10.5);
		double rotation = game.controls.rotation;		//Rotacion camara
		
		cosine = Math.cos(rotation);
		sine = Math.sin(rotation);
		
		for (int y = 0; y < height; y++) {
			double ceiling = (y - height / 2.0) / height;	
					
			double z = (floorPosition + up)/ ceiling;				//Altura floor
			if (Controller.walk) {
				walking = Math.sin(game.time / 6.0) * 0.5;	//Movimiento camara al andar
				z = (floorPosition + up + walking)/ ceiling;		//Movimiento camara al andar
			}
			
			
			if (Controller.crouchWalk && Controller.walk) {
				walking = Math.sin(game.time / 6.0) * 0.2;      //Movimiento camara al andar agachado
			}
			if (Controller.sprintWalk && Controller.walk) {
				walking = Math.sin(game.time / 6.0) * 0.7;      //Movimiento camara al correr
			}
			
			
			if (ceiling < 0) {										//Invertir ceiling
				z = (celiningPosition - up) / -ceiling;						//Altura ceiling
				if (Controller.walk) {
					z = (floorPosition - up - walking)/ -ceiling;
				}
			}
			
			for (int x = 0; x < width; x++) {
				double depth = (x - width / 2.0) / height;
				depth *= z;
				double xx = depth * cosine + z * sine; 
				double yy = z * cosine - depth * sine;
				int xPix = (int) (xx + right);
				int yPix = (int) (yy + forward);
				zBuffer[x + y * width] = z;                       	//Distancia gradiente
				//pixels[x + y * width] = ((xPix & 15) * 16) | ((yPix & 15) * 16) << 8;		//Determinar color pixel
				pixels[x + y * width] = Texture.floor.pixels[(xPix & 7) + (yPix & 7) * 256];	//Asignar textura al pixel
				
				if (z > 500) {				//Limitar distancia de renderizado
					//pixels[x + y * width] = 0;
					pixels[x + y * width] = ((xPix & 15) * 16) | ((yPix & 15) * 16) << 8;
					
				}
				
			}
		}
		
		
	
	}
	
	public void renderWall (double xLeft, double xRight, double zDistanceLeft , double zDistanceRight, double yHeight) {
		
		double upCorrect = 0.0625;
		double rightCorrect = 0.0625;
		double forwardCorrect = 0.0625;
		double walkCorrect = -0.0625;
		
		double xcLeft = ((xLeft) - (right * rightCorrect)) *2; //X Left Calculation wall
		double zcLeft = ((zDistanceLeft) - (forward * forwardCorrect)) *2;
		
		double rotLeftSideX = xcLeft * cosine - zcLeft * sine;
		double yCornerTL = ((-yHeight) - (-up *  upCorrect + (walking * walkCorrect))) * 2; 		//Top left corner
		double yCornerBL = ((+0.5 - yHeight)- (-up * upCorrect + (walking * walkCorrect))) * 2;		//Bottom leftsa
		double rotLeftSideZ = zcLeft * cosine + xcLeft * sine;
		
		double xcRight = ((xRight) - (right * rightCorrect)) * 2;
		double zcRight = ((zDistanceRight) - (forward * forwardCorrect)) * 2;
		
		double rotRightSideX = xcRight * cosine - zcRight * sine;
		double yCornerTR = ((-yHeight) - (-up *  upCorrect + (walking * walkCorrect))) * 2;
		double yCornerBR = ((+0.5 - yHeight) - (-up *  upCorrect + (walking * walkCorrect))) *2;
		double rotRightSideZ = zcRight * cosine + xcRight * sine;
		
		double xPixelLeft = (rotLeftSideX / rotLeftSideZ * height + width / 2);
		double xPixelRight = (rotRightSideX / rotRightSideZ * height + width / 2);
		
		double text20 = 0;
		double text30 = 8;
		double clip = 0.5;
		//double clip = 1.0;
		
		if (rotLeftSideZ < clip && rotRightSideZ < clip) {
			return;
		}
		
		if (rotLeftSideZ < clip) {			//Evitar clipping
			double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
			rotLeftSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
			rotLeftSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
			text20 = text20 + (text30 - text20) * clip0;
		}
		
		if (rotRightSideZ < clip) {			//Evitar clipping
			double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
			rotRightSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
			rotRightSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
			text20 = text20 + (text30 - text20) * clip0;
		}
		
		if (xPixelLeft >= xPixelRight) {
			return;
		}
		
		
		
		
		//Convertir pixeles a enteros
		int xPixelLeftInt = (int) (xPixelLeft);
		int xPixelRightInt = (int) (xPixelRight);
		
		//No renderizar si excede la pantalla
		if(xPixelLeftInt < 0) {
			xPixelLeftInt = 0;
		}
		if(xPixelRightInt > width) {
			xPixelRightInt = width;
		}
		
		double yPixelLeftTop = (yCornerTL / rotLeftSideZ * height + height / 2.0);
		
		double yPixelLeftBottom = (yCornerBL / rotLeftSideZ * height + height / 2.0);
		double yPixelRightTop = (yCornerTR / rotRightSideZ * height + height / 2.0);
		double yPixelRightBottom = (yCornerBR / rotRightSideZ * height + height / 2.0);
		
		
		//Texture wall
		//textura
		double text0 = 1 / rotLeftSideZ;
		double text1 = 1 / rotRightSideZ;
		double text2 = text20 / rotLeftSideZ;
		double text3 = text30 / rotRightSideZ - text2;
		
		//Bucle para renderizar
		for (int x = xPixelLeftInt; x < xPixelRightInt; x++) {
			double pixelRotation = (x - xPixelLeft) / (xPixelRight - xPixelLeft);
			
			//Texture Handler
			int xTexture = (int) ((text2 + text3 * pixelRotation) / (text0 + (text1 - text0) * pixelRotation));
			
			double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop) * pixelRotation;
			double yPixelBottom = yPixelLeftBottom + (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;
			
			int yPixelTopInt = (int) (yPixelTop);
			int yPixelBottomInt = (int) (yPixelBottom);
			
			if(yPixelTopInt < 0) {
				yPixelTopInt = 0;
			}
			if(yPixelBottomInt > height) {
				yPixelBottomInt = height;
			}
			
			for (int y = yPixelTopInt; y < yPixelBottomInt; y++) {
				double pixelRotationY = (y - yPixelTop) / (yPixelBottom - yPixelTop);
				int yTexture = (int) (8 * pixelRotationY);
				
				
					//pixels[x+y*width] = 0x1B91E0;		//Color pixel
				//pixels[x+y*width] = xTexture * 100 + yTexture * 100 * 256;
				pixels[x + y * width] = Texture.wall.pixels[(xTexture & 7) + (yTexture & 7) * 128];
				zBuffer[x+y*width] = 1 / (text0 + (text1 - text0) * pixelRotation) * 16;
			}
		}
	}
 	
	public void renderDistanceLimiter() {
		for (int i = 0; i < width * height; i++) {
			int color = pixels[i];
			int brightness = (int) (renderDistance / zBuffer[i]);
			
			if (brightness < 0) {								//Limitar brillo
				brightness = 0;
			}
			if (brightness > 255) {
				brightness = 255;
			}
			
			int r = (color >> 16) &0xff;
			int g = (color >> 8) &0xff;
			int b = (color) &0xff;
			
			r = r * brightness >>> 8;
			g = g * brightness >>> 8;
			b = b * brightness >>> 8;
			
			pixels[i] = r << 16 | g << 8 | b;
		}
	}
}
