package ch.zombieInvasion.Zombies;

import org.newdawn.slick.Graphics;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.Components.LifeComponent;
import ch.zombieInvasion.Components.MovingComponent;
import ch.zombieInvasion.Components.RenderComponent;
import ch.zombieInvasion.Objekte.Entity;
import ch.zombieInvasion.States.StateMachine;
import ch.zombieInvasion.States.Zombie.AgressivZombieState;
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
		stateMachine.SetCurrentState(new AgressivZombieState());
		stateMachine.SetGlobalState(new ZombieGlobalState());
		render = new RenderComponent(Images.normalZombie);
		movement = new MovingComponent(location, 10, 0.8);
		life = new LifeComponent(10);
	}

	@Override
	public void render(Graphics g, double extrapolation) {
		stateMachine.Render(g, extrapolation);
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
