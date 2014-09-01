package ch.zombieInvasion.Objekte;

import org.newdawn.slick.Graphics;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.Camera.Camera;

public interface Entity {
	boolean disappeard = false;
	
	abstract void render(Graphics g, double extrapolation, Camera camera);

	abstract void update(Game game);

}
