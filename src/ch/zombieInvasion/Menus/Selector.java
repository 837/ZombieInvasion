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

	private void drawRect(Graphics g, Camera camera) {
		int tx1 = (int) (firstClickX - camera.getCamX());
		int ty1 = (int) (firstClickY - camera.getCamY());
		int tx2 = (int) (secondClickX - camera.getCamX());
		int ty2 = (int) (secondClickY - camera.getCamY());

		// Use Math. something abs()/min()
		if (firstClickX > secondClickX) {
			tx1 = (int) (secondClickX - camera.getCamX());
			tx2 = (int) (firstClickX - camera.getCamX());
		}
		if (firstClickY > secondClickY) {
			ty1 = (int) (secondClickY - camera.getCamY());
			ty2 = (int) (firstClickY - camera.getCamY());
		}
		// Rectangle r = new Rectangle(tx1, ty1, tx2 - tx1, ty2 - ty1);

		g.fillRect(tx1, ty1, tx2 - tx1, ty2 - ty1);

	}
}
