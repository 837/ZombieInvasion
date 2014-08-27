package ch.zombieInvasion.Zombies;

import org.newdawn.slick.Graphics;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.Camera.Camera;
import ch.zombieInvasion.Components.LifeComponent;
import ch.zombieInvasion.Components.MovingComponent;
import ch.zombieInvasion.Components.RenderComponent;
import ch.zombieInvasion.Objekte.Entity;
import ch.zombieInvasion.States.StateMachine;
import ch.zombieInvasion.States.Zombie.NormalZombieState;
import ch.zombieInvasion.States.Zombie.ZombieGlobalState;
import ch.zombieInvasion.util.Images;
import ch.zombieInvasion.util.Vector2D;

public class Zombie implements Entity {
	private RenderComponent render;
	private MovingComponent movement;
	private LifeComponent life;
	private StateMachine<Zombie> stateMachine;

	public Zombie(Vector2D location) {
		stateMachine = new StateMachine<Zombie>(this);
		stateMachine.SetCurrentState(new NormalZombieState());
		stateMachine.SetGlobalState(new ZombieGlobalState());
		render = new RenderComponent(Images.normalZombie);
		movement = new MovingComponent(location, 1, 1);
		life = new LifeComponent(1);
	}

	@Override
	public void render(Graphics g, double extrapolation, Camera camera) {
		stateMachine.Render(g, extrapolation, camera);
	}

	@Override
	public void update(Game game) {
		stateMachine.Update(game);
	}

	public RenderComponent getRender() {
		return render;
	}

	public MovingComponent getMovingComponent() {
		return movement;
	}

	public LifeComponent getLife() {
		return life;
	}

}
