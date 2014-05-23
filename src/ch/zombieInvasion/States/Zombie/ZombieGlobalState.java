package ch.zombieInvasion.States.Zombie;

import org.newdawn.slick.Graphics;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.Camera.Camera;
import ch.zombieInvasion.Eventhandling.EventDispatcher;
import ch.zombieInvasion.States.BaseState;
import ch.zombieInvasion.Zombies.Zombie;

public class ZombieGlobalState implements BaseState<Zombie> {

	@Override
	public void Enter(Zombie owner) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Update(Zombie owner, Game game) {
		EventDispatcher.getEvents().forEach(e -> {
			switch (e.getEvent()) {
				case KILL_ALL_ZOMBIES:
					System.out.println("WE ARE DEAD");
					owner.getLife().addDamage(999999);
				break;
			}
		});
	}

	@Override
	public void Render(Zombie owner, Graphics g, double extrapolation, Camera camera) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Exit(Zombie owner) {
		// TODO Auto-generated method stub

	}

}
