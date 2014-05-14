package ch.zombieInvasion.Components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import ch.zombieInvasion.Eventhandling.EventDispatcher;
import ch.zombieInvasion.Eventhandling.EventType;
import ch.zombieInvasion.util.Vector2D;

public class MovementInputComponent {
	private int up = Input.KEY_W;
	private int left = Input.KEY_A;
	private int down = Input.KEY_S;
	private int right = Input.KEY_D;

	public MovementInputComponent() {
	}

	public MovementInputComponent(int up, int left, int down, int right) {
		try {
			this.up = up;
			this.left = left;
			this.down = down;
			this.right = right;
		} catch (Exception e) {
			System.out.println("Wrong input key int " + e);
		}
	}

	public Vector2D movedirection(GameContainer container) {
		Input input = container.getInput();
		double x = 0;
		double y = 0;

		if (input.isKeyDown(up)) {
			y -= 1;
		}

		if (input.isKeyDown(left)) {
			x -= 1;
		}

		if (input.isKeyDown(down)) {
			y += 1;
		}

		if (input.isKeyDown(right)) {
			x += 1;
		}

		return new Vector2D(x, y).normalize();
	}
}
