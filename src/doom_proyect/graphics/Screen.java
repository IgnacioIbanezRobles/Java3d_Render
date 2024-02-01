package doom_proyect.graphics;

import java.util.Random;

import doom_proyect.Game;

public class Screen extends Render{

	private Render test;
	private Render3D render;
	
	public Screen(int width, int height) {
		super(width, height);
		Random random = new Random();
		render = new Render3D(width, height);
		test = new Render(256, 256);
		for (int i = 0; i < 256*256 ; i++) {
			test.pixels[i] = random.nextInt() * (random.nextInt(5) / 4);
		}
	}
	
	public void render(Game game) {
		for (int i = 0; i < width * height; i++) {		
			pixels[i]=0;							//Quitar barrido de animación
		}
		
//		for (int i = 0; i< 50; i++) {
//			//Animacion Pixeles
//			//int anim = (int) (Math.sin((System.currentTimeMillis() + i * 8) %2000.0/2000*Math.PI*2)*200);
//			//int anim2 = (int) (Math.cos((System.currentTimeMillis() +i * 8) %2000.0/2000*Math.PI*2)*200);
//			int anim = (int) (Math.sin ((game.time +i *2) % 1000.0 / 100) * 100);
//			int anim2 = (int) (Math.cos ((game.time +i *2) % 1000.0 / 100) * 100);
//		//draw(test, (width-256) / 2 + anim, (height-256)/2 + anim2); //Posición y animacion de los Pixeles
//		}
		
		render.floor(game);
		//(largo izquierdo, largo a la derecho,lado izquierdo, lado derecho, altura )
		render.renderWall(0, 0.5, 1.5, 1.5, 0);
		render.renderWall(0, 0, 1, 1.5, 0);
		render.renderWall(0, 0.5, 1, 1, 0);
		render.renderWall(0.5, 0.5, 1, 1.5, 0);	
		
		render.renderWall(0, 0.5, 1.5, 1.5, 0.5);
		render.renderWall(0, 0, 1, 1.5, 0.5);
		render.renderWall(0, 0.5, 1, 1, 0.5);
		render.renderWall(0.5, 0.5, 1, 1.5, 0.5);



		render.renderDistanceLimiter();
		
		draw(render, 0 , 0);
	}

}
