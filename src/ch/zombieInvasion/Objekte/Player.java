package ch.zombieInvasion.Objekte;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.Camera.Camera;
import ch.zombieInvasion.Components.LifeComponent;
import ch.zombieInvasion.Components.MovementInputComponent;
import ch.zombieInvasion.Components.MovingComponent;
import ch.zombieInvasion.Components.RenderComponent;
import ch.zombieInvasion.Eventhandling.Event;
import ch.zombieInvasion.States.StateMachine;
import ch.zombieInvasion.States.Player.NormalPlayerState;
import ch.zombieInvasion.util.Images;
import ch.zombieInvasion.util.Vector2D;

public class Player implements Entity {
	private MovingComponent movement;
	private RenderComponent render;
	private LifeComponent life;
	private MovementInputComponent movementInputComponent;
	private StateMachine<Player> stateMachine;

	public Player(Vector2D location) {
		stateMachine = new StateMachine<Player>(this);
		stateMachine.SetCurrentState(new NormalPlayerState());

		movement = new MovingComponent(location, 1, 8);
		movementInputComponent = new MovementInputComponent();
		render = new RenderComponent(Images.player);
		life = new LifeComponent(100);
	}

	@Override
	public void render(Graphics g, double extrapolation, Camera camera) {
		stateMachine.Render(g, extrapolation, camera);
	}

	@Override
	public void update(Game game) {
		stateMachine.Update(game);
	}

	public MovingComponent getMovingComponent() {
		return movement;
	}

	public RenderComponent getRender() {
		return render;
	}

	public LifeComponent getLife() {
		return life;
	}

	public MovementInputComponent getMovementInputComponent() {
		return movementInputComponent;
	}


}
