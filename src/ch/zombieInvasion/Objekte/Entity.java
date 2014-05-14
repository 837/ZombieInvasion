package ch.zombieInvasion.Objekte;

import org.newdawn.slick.Graphics;

import ch.zombieInvasion.Game;

public interface Entity {
	boolean disappeard = false;

	abstract void render(Graphics g, double extrapolation);

	abstract void update(Game game);

}
