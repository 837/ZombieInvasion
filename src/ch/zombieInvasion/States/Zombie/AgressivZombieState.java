package ch.zombieInvasion.States.Zombie;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.Components.MovingComponent;
import ch.zombieInvasion.Eventhandling.EventDispatcher;
import ch.zombieInvasion.Eventhandling.EventType;
import ch.zombieInvasion.Objekte.Player;
import ch.zombieInvasion.States.BaseState;
import ch.zombieInvasion.Zombies.Zombie;
import ch.zombieInvasion.util.Timer;
import ch.zombieInvasion.util.Vector2D;

public class AgressivZombieState implements BaseState<Zombie> {
	Timer t = new Timer();

	@Override
	public void Enter(Zombie owner) {
		// TODO Auto-generated method stub
	}

	@Override
	public void Update(Zombie owner, Game game) {
		if (!owner.getLife().isDead()) {
			Player player = game.eManager.getPlayer().get(0);
			MovingComponent m = player.getMovingComponent();

			if (!owner.getMovingComponent().obstacleAvoidanceByCircle(game.eManager.getObstacle(),
					owner.getRender().getShape(0).getBoundingCircleRadius(), 10)) {
				if (owner.getMovingComponent().getLocation().dist(game.eManager.getPlayer().get(0).getMovingComponent().getLocation()) < 200) {
					owner.getMovingComponent().pursuit(game.eManager.getPlayer().get(0).getMovingComponent().getLocation(),
							game.eManager.getPlayer().get(0).getMovingComponent().getVelocity(),
							game.eManager.getPlayer().get(0).getMovingComponent().currentSpeed(), 1);
					owner.getMovingComponent().setMaxSpeed(3.1);
				} else {
					owner.getMovingComponent().setMaxSpeed(0.2);
					owner.getMovingComponent().wander(10, 90, 0.05, 0.3);
				}
			}

			owner.getMovingComponent().update();

		} else {
			ArrayList<Object> addInfo = new ArrayList<>();
			addInfo.add(owner);
			EventDispatcher.createEvent(0, EventType.DELETE_ME, addInfo);
		}
	}

	@Override
	public void Render(Zombie owner, Graphics g, double extrapolation) {
		Vector2D locationE = owner.getMovingComponent().extrapolatedLocation(extrapolation);
		Vector2D location = owner.getMovingComponent().getLocation();
		owner.getRender().rotateAndRender(locationE, g, 0, locationE.add(owner.getMovingComponent().getHeading()));

		owner.getRender().renderCollisionShape(location, g);

	

		g.setColor(Color.black);
		Vector2D avoidForce = location.add(owner.getMovingComponent().avoidForce.mult(10));
		g.drawLine((float) location.x, (float) location.y, (float) avoidForce.x, (float) avoidForce.y);

		g.setColor(Color.blue);
		g.draw(owner.getMovingComponent().aheadCircle);
		g.draw(owner.getMovingComponent().aheadCircle2);
	}

	@Override
	public void Exit(Zombie owner) {
		// TODO Auto-generated method stub

	}

}
