package ch.zombieInvasion.Menus;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.Camera.Camera;
import ch.zombieInvasion.Eventhandling.EventDispatcher;
import ch.zombieInvasion.Eventhandling.EventType;
import ch.zombieInvasion.util.Vector2D;

public class Selector {
	int firstClickX = 0;
	int firstClickY = 0;
	int secondClickX = 0;
	int secondClickY = 0;
	boolean firstPos = true;
	ArrayList<Object> selectedThings = new ArrayList<>();

	public boolean somethingSelected() {
		return !getSelectedThings().isEmpty();
	}

	public boolean isSelected(Object entity) {
		return getSelectedThings().contains(entity);
	}

	public ArrayList<Object> getSelectedThings() {
		return selectedThings;
	}

	public void setSelectedThings(ArrayList<Object> selectedThings) {
		this.selectedThings = selectedThings;
	}

	private void setSelection(Vector2D clickPos) {
		if (firstPos) {
			firstPos = false;
			firstClickX = (int) clickPos.x;
			firstClickY = (int) clickPos.y;
			secondClickX = (int) clickPos.x;
			secondClickY = (int) clickPos.y;
		} else {
			secondClickX = (int) clickPos.x;
			secondClickY = (int) clickPos.y;
		}
	}

	public void Update(Game game) {
		EventDispatcher.getEvents().forEach(e -> {
			switch (e.getEvent()) {
				case LeftDown:
					Vector2D clickPos = (Vector2D) e.getAdditionalInfo();
					setSelection(clickPos);
					System.out.println("LeftDown");
					EventDispatcher.removePersistentEvent(e);
				break;
				case LeftUp:
					firstPos = true;
					Vector2D clickPos2 = (Vector2D) e.getAdditionalInfo();
					setSelection(clickPos2);
					firstPos = true;
					System.out.println("LeftUp");
					EventDispatcher.removePersistentEvent(e);
				break;
				case LeftDrag:
					Vector2D clickPos1 = (Vector2D) e.getAdditionalInfo();
					setSelection(clickPos1);
					System.out.println("LeftDrag");
					EventDispatcher.removePersistentEvent(e);
				break;
			}

		});
	}

	public void Render(Graphics g, double extrapolation, Camera camera) {
		drawRect(g, camera);
	}

	private Rectangle makeRectFromCorners(Vector2D first, Vector2D second) {
		return new Rectangle((float) Math.min(first.x, second.x), (float) Math.min(first.y, second.y), (float) Math.abs(first.x - second.x),
				(float) Math.abs(first.y - second.y));
	}

	private void drawRect(Graphics g, Camera camera) {
		Vector2D one = new Vector2D(camera.getWorldPosX(firstClickX), camera.getWorldPosY(firstClickY));
		Vector2D two = new Vector2D(camera.getWorldPosX(secondClickX), camera.getWorldPosY(secondClickY));

		g.fill(makeRectFromCorners(one, two));

	}
}
