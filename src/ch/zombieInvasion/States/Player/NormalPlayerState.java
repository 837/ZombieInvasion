package ch.zombieInvasion.States.Player;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.Camera.Camera;
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
		mousePos = new Vector2D(container.getInput().getAbsoluteMouseX() + game.camera.getCamX(), container.getInput().getAbsoluteMouseY()
				+ game.camera.getCamY());

		double force = 2;

		if (owner.getMovingComponent().obstacleCollisionByCircle(game.world.eManager.getObstacle(),
				owner.getRender().getShape(0).getBoundingCircleRadius(), 5)) {
			force = 2;
		}

		owner.getMovingComponent().arrive(mousePos, force);

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
	public void Render(Player owner, Graphics g, double extrapolation, Camera camera) {
		Vector2D locationE = owner.getMovingComponent().extrapolatedLocation(extrapolation);
		Vector2D location = owner.getMovingComponent().getLocation();
		owner.getRender().rotateAndRender(locationE, g, 0, locationE.add(owner.getMovingComponent().getHeading()), camera);

		owner.getRender().renderCollisionShape(location, g, camera);

		g.setColor(Color.cyan);
		Vector2D veloLoc = locationE.add(owner.getMovingComponent().getVelocity().mult(20));
		g.drawLine((float) (locationE.x - camera.getCamX()), (float) (locationE.y - camera.getCamY()), (float) (veloLoc.x - camera.getCamX()),
				(float) (veloLoc.y - camera.getCamY()));

		g.setColor(Color.black);
		Vector2D avoidForce = locationE.add(owner.getMovingComponent().avoidForce.mult(10));
		g.drawLine((float) (locationE.x - camera.getCamX()), (float) (locationE.y - camera.getCamY()), (float) (avoidForce.x - camera.getCamX()),
				(float) (avoidForce.y - camera.getCamY()));

		g.setColor(Color.blue);
		owner.getMovingComponent().aheadCircle2.setCenterX((float) (location.x - camera.getCamX()));
		owner.getMovingComponent().aheadCircle2.setCenterY((float) (location.y - camera.getCamY()));
		g.draw(owner.getMovingComponent().aheadCircle2);
		owner.getMovingComponent().aheadCircle2.setCenterX((float) (location.x + camera.getCamX()));
		owner.getMovingComponent().aheadCircle2.setCenterY((float) (location.y + camera.getCamY()));
		g.setColor(Color.black);
	}

	@Override
	public void Exit(Player owner) {
		// TODO Auto-generated method stub
	}

}
