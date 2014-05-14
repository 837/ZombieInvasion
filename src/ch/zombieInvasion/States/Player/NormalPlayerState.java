package ch.zombieInvasion.States.Player;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.Eventhandling.Event;
import ch.zombieInvasion.Eventhandling.EventDispatcher;
import ch.zombieInvasion.Objekte.Player;
import ch.zombieInvasion.States.BaseState;
import ch.zombieInvasion.util.Vector2D;

public class NormalPlayerState implements BaseState<Player> {
	private Vector2D mousePos = new Vector2D();

	@Override
	public void Enter(Player owner) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Update(Player owner, Game game) {
		GameContainer container = game.container;
		mousePos = new Vector2D(container.getInput().getAbsoluteMouseX(), container.getInput().getAbsoluteMouseY());

		if (!owner.getMovingComponent().obstacleAvoidanceByLine(game.eManager.getObstacle(), owner.getRender().getShape(0).getBoundingCircleRadius(),
				0.5)) {
			owner.getMovingComponent().arrive(mousePos, 0.4);
		}
		owner.getMovingComponent().update();
		EventDispatcher.getEvents().forEach(e -> {
			switch (e.getEvent()) {
				case DMG_Player:
					if (owner.getLife().isDead()) {
						EventDispatcher.removePersistentEvent(e);
					}
					System.out.println("DMG");
					owner.getLife().addDamage((int) e.getAdditionalInfos().get(0));
				break;
			}
		});
	}

	@Override
	public void Render(Player owner, Graphics g, double extrapolation) {
		Vector2D locationE = owner.getMovingComponent().extrapolatedLocation(extrapolation);
		Vector2D location = owner.getMovingComponent().getLocation();
		owner.getRender().rotateAndRender(locationE, g, 0, locationE.add(owner.getMovingComponent().getHeading()));

		owner.getRender().renderCollisionShape(location, g);

		g.setColor(Color.cyan);
		Vector2D veloLoc = location.add(owner.getMovingComponent().getVelocity().mult(20));
		g.drawLine((float) location.x, (float) location.y, (float) veloLoc.x, (float) veloLoc.y);

		g.setColor(Color.magenta);
		Vector2D centVector2d = owner.getMovingComponent().obstacle_center;
		g.drawLine((float) location.x, (float) location.y, (float) centVector2d.x, (float) centVector2d.y);

		g.setColor(Color.black);
		Vector2D avoidForce = location.add(owner.getMovingComponent().avoidForce.mult(10));
		g.drawLine((float) location.x, (float) location.y, (float) avoidForce.x, (float) avoidForce.y);

		g.setColor(Color.blue);
		g.draw(owner.getMovingComponent().aheadCircle);
		g.draw(owner.getMovingComponent().aheadCircle2);
		g.setColor(Color.black);
	}

	@Override
	public void Exit(Player owner) {
		// TODO Auto-generated method stub
	}

}
