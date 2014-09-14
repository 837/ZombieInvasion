package ch.zombieInvasion.Weapons;

import org.newdawn.slick.Graphics;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.Camera.Camera;
import ch.zombieInvasion.Components.LifeComponent;
import ch.zombieInvasion.Components.MovingComponent;
import ch.zombieInvasion.Components.RenderComponent;
import ch.zombieInvasion.Objekte.Entity;
import ch.zombieInvasion.States.StateMachine;
import ch.zombieInvasion.States.Bullet.NormalBulletState;
import ch.zombieInvasion.util.Images;
import ch.zombieInvasion.util.Vector2D;

public class Bullet implements Entity {
	private RenderComponent render;
	private MovingComponent movement;
	private LifeComponent life;
	private StateMachine<Bullet> stateMachine;
	private Vector2D direction;

	public Bullet(Vector2D startlocation, Vector2D direction, int speed, int mass) {
		stateMachine = new StateMachine<Bullet>(this);
		stateMachine.SetCurrentState(new NormalBulletState());
		movement = new MovingComponent(startlocation, mass, speed);
		render = new RenderComponent(Images.normalBullet);
		life = new LifeComponent(1);
		this.direction = direction;
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

	public Vector2D getDirection() {
		return direction;
	}

}
