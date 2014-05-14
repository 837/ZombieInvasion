package ch.zombieInvasion.Objekte;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.util.Vector2D;

public class Obstacle implements Entity {
	private Shape shape;
	public Obstacle(Vector2D location, float height, float width, boolean circle) {
		if (circle) {
			this.shape = new Circle((float) location.x, (float) location.y, Math.min(width, height));
		} else {
			this.shape = new Rectangle((float) location.x, (float) location.y, width, height);
		}
	}

	public Shape get() {
		return shape;
	}

	@Override
	public void render(Graphics g, double extrapolation) {
		g.draw(shape);
	}

	@Override
	public void update(Game game) {
		// TODO Auto-generated method stub

	}

}
