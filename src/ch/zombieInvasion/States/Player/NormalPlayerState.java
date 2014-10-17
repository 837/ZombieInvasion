package ch.zombieInvasion.States.Player;

import java.util.ArrayDeque;
import java.util.ArrayList;

import org.lwjgl.Sys;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.Camera.Camera;
import ch.zombieInvasion.Eventhandling.EventDispatcher;
import ch.zombieInvasion.Eventhandling.EventType;
import ch.zombieInvasion.Objekte.Player;
import ch.zombieInvasion.States.BaseState;
import ch.zombieInvasion.util.Timer;
import ch.zombieInvasion.util.Vector2D;

public class NormalPlayerState implements BaseState<Player> {
	private Vector2D mousePos = new Vector2D(100, 100);
	private ArrayDeque<Vector2D> movementQueue = new ArrayDeque<>();

	// schüsse
	private Timer t2 = new Timer();

	@Override
	public void Enter(Player owner) {
		// TODO Auto-generated method stub
	}

	@Override
	public void Update(Player owner, Game game) {
		GameContainer container = game.container;

		if (container.getInput().isMouseButtonDown(0) && container.getInput().isKeyDown(Input.KEY_LSHIFT)) {

			Vector2D mouse = game.camera.getPositionInWorld(new Vector2D(container.getInput().getAbsoluteMouseX(),
					container.getInput().getAbsoluteMouseY()));

			owner.getMovingComponent().headTo(mouse);

			if (game.container.getInput().isMouseButtonDown(0) && t2.getSeconds() >= 0.25) {
				ArrayList<Object> additonalInfos = new ArrayList<>();

				additonalInfos.add(game.world.eManager.getPlayer().get(0).getMovingComponent().getLocation());
				additonalInfos.add(mouse.sub(game.world.eManager.getPlayer().get(0).getMovingComponent().getLocation())
						.normalize());
				// speed
				additonalInfos.add(18);
				// mass
				additonalInfos.add(1);
				EventDispatcher.createEvent(0, EventType.FireAt, additonalInfos);
				t2.restart();
			}

		}
		if (!movementQueue.isEmpty()) {
			if (mousePos.dist(owner.getMovingComponent().getLocation()) <= 10) {
				mousePos = movementQueue.poll();
			}
		}

		owner.getMovingComponent().arrive(mousePos, 10);
		owner.getMovingComponent().update();
		EventDispatcher.getEvents().forEach(e -> {
			switch (e.getEvent()) {
			case DMG_Player:
				if (owner.getLife().isDead()) {
					EventDispatcher.removePersistentEvent(e);
					EventDispatcher.createEvent(0, EventType.Reset_Game, null);
				}
				System.out.println("DMG");
				owner.getLife().addDamage((int) e.getAdditionalInfo());
				break;
			case MoveToPosQueued:
				movementQueue.offer((Vector2D) e.getAdditionalInfo());
				EventDispatcher.removePersistentEvent(e);
				System.out.println("added movepos player");
				break;
			case MoveToPos:
				movementQueue.clear();
				mousePos = (Vector2D) e.getAdditionalInfo();
				EventDispatcher.removePersistentEvent(e);

				break;

			}
		});
	}

	@Override
	public void Render(Player owner, Graphics g, double extrapolation, Camera camera) {
		Vector2D locationE = owner.getMovingComponent().extrapolatedLocation(extrapolation);
		Vector2D location = owner.getMovingComponent().getLocation();
		owner.getRender().rotateAndRender(locationE, g, 0, locationE.add(owner.getMovingComponent().getHeading()),
				camera);
		owner.getRender().renderCollisionShape(location, g, camera);
	}

	@Override
	public void Exit(Player owner) {
		// TODO Auto-generated method stub
	}

}
