package ch.zombieInvasion.States.Zombie;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import org.newdawn.slick.Graphics;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.Camera.Camera;
import ch.zombieInvasion.Eventhandling.EventDispatcher;
import ch.zombieInvasion.Eventhandling.EventType;
import ch.zombieInvasion.Objekte.Player;
import ch.zombieInvasion.Pathfinding.Node;
import ch.zombieInvasion.States.BaseState;
import ch.zombieInvasion.Zombies.Zombie;
import ch.zombieInvasion.util.Timer;
import ch.zombieInvasion.util.Vector2D;

public class NormalZombieState implements BaseState<Zombie> {
	Timer t = new Timer();
	Node target = null;
	Random r = new Random();
	Node nodePos = null;

	@Override
	public void Enter(Zombie owner) {
		// TODO Auto-generated method stub
	}

	@Override
	public void Update(Zombie owner, Game game) {

		int nodeX = (int) owner.getMovingComponent().getLocation().x / 32;
		int nodeY = (int) owner.getMovingComponent().getLocation().y / 32;

		if (nodeX >= 0 || nodeX < game.world.map.getWidth() || nodeY >= 0 || nodeY < game.world.map.getHeight()) {
			nodePos = game.world.all_nodes.get(nodeX * game.world.map.getHeight() + nodeY);
		}

		if (nodePos != null) {

			nodePos.addZombie(owner);
			Player player = game.world.eManager.getPlayer().get(0);
			if (owner.getMovingComponent().getLocation().dist(player.getMovingComponent().getLocation()) <= 150) {

				owner.getMovingComponent().pursuit(player.getMovingComponent().getLocation(), player.getMovingComponent().getVelocity(),
						player.getMovingComponent().currentSpeed(), 10);

				if (player.getRender().getShape(0).intersects(owner.getRender().getShape(0))) {
					EventDispatcher.createEvent(0, EventType.DMG_Player, new Integer(1));
					System.out.println("Damaging player");
				}
			} else {
				if (target == null || nodePos.equals(target)) {

					int minimum = nodePos.getNeighbors().stream().map(e -> e.getDistance()).min(Integer::compare).get();

					if (nodePos.getDistance() >= 999) {
						EventDispatcher.createEvent(0, EventType.Destroy_Tower, owner.getMovingComponent().getLocation());
					}

					ArrayList<Node> possibleTargets = (ArrayList<Node>) nodePos.getNeighbors().stream().filter(e -> e.getDistance() == minimum)
							.collect(Collectors.toList());

					int size = possibleTargets.size();
					if (size >= 1) {
						target = possibleTargets.get(r.nextInt(possibleTargets.size()));
					}
					t.restart();
				}
				if (target != null) {
					owner.getMovingComponent().seek(target.getVector(32), 15);

					if (target.getDistance() == 0) {
						owner.getLife().addDamage(999);
						EventDispatcher.createEvent(-1, EventType.ReachedTarget, owner);
					}
				}
			}
			owner.getMovingComponent().update();
			nodePos = null;
		}
		if (owner.getLife().isDead()) {
			EventDispatcher.createEvent(-1, EventType.DELETE_ME, owner);
		}
	}

	@Override
	public void Render(Zombie owner, Graphics g, double extrapolation, Camera camera) {
		Vector2D locationE = owner.getMovingComponent().extrapolatedLocation(extrapolation);
		Vector2D location = owner.getMovingComponent().getLocation();
		owner.getRender().rotateAndRender(locationE, g, 0, locationE.add(owner.getMovingComponent().getHeading()), camera);
		owner.getRender().renderCollisionShape(location, g, camera);
	}

	@Override
	public void Exit(Zombie owner) {
		// TODO Auto-generated method stub
	}

}
