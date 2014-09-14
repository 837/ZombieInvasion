package ch.zombieInvasion.States.Bullet;

import java.util.ArrayDeque;
import org.newdawn.slick.Graphics;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.Camera.Camera;
import ch.zombieInvasion.Eventhandling.EventDispatcher;
import ch.zombieInvasion.Eventhandling.EventType;
import ch.zombieInvasion.Pathfinding.Node;
import ch.zombieInvasion.States.BaseState;
import ch.zombieInvasion.Weapons.Bullet;
import ch.zombieInvasion.Zombies.Zombie;
import ch.zombieInvasion.util.Timer;
import ch.zombieInvasion.util.Vector2D;

public class NormalBulletState implements BaseState<Bullet> {
	Vector2D pos = new Vector2D();
	Timer t = new Timer();
	Node nodePos = null;

	@Override
	public void Enter(Bullet owner) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Update(Bullet owner, Game game) {

		owner.getMovingComponent().seek(owner.getMovingComponent().getLocation().add(owner.getDirection()), 100);
		owner.getMovingComponent().update();
		//owner.getRender().updateShapePositions(owner.getMovingComponent().getLocation());
		
		//distanzbegrenzig
		if (t.getSeconds() >= 1.2) {
			owner.getLife().addDamage(9999);
		}
		if (owner.getLife().isDead()) {
			EventDispatcher.createEvent(-1, EventType.DELETE_ME, owner);
		}

		int nodeX = (int) owner.getMovingComponent().getLocation().x / 32;
		int nodeY = (int) owner.getMovingComponent().getLocation().y / 32;

		if (nodeX >= 0 && nodeX < game.world.map.getWidth() && nodeY >= 0 && nodeY < game.world.map.getHeight()) {
			nodePos = game.world.all_nodes.get(nodeX * game.world.map.getHeight() + nodeY);
		}

		if (nodePos != null) {
			getNeighbors(nodePos, game).stream().filter(e -> owner.getRender().getShape(0).intersects(e.getRender().getShape(0))).findFirst()
					.ifPresent(e -> {
						//EventDispatcher.createEvent(-1, EventType.DELETE_ME, owner);
						owner.getLife().addDamage(1);
						e.getLife().addDamage(1);
						System.out.println("Shot hit target");
					});
			nodePos = null;
		}
	}

	private ArrayDeque<Zombie> getNeighbors(Node e, Game game) {
		ArrayDeque<Zombie> result = new ArrayDeque<>();
		result.addAll(e.getZombies());
		Node neighbor;
		int[][] dirs = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 }, { -1, -1 }, { 1, 1 }, { -1, 1 }, { 1, -1 } }; // plus
		// diagonal
		// int[][] dirs = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

		for (int i = 0; i < dirs.length; i++) {
			neighbor = new Node((e.getX() + dirs[i][0]) + "" + (e.getY() + dirs[i][1]), e.getX() + dirs[i][0], e.getY() + dirs[i][1]);
			for (int j = 0; j < game.world.all_nodes.size(); j++) {
				Node currentNode = game.world.all_nodes.get(j);
				if (currentNode.getX() == neighbor.getX() && currentNode.getY() == neighbor.getY()) {
					result.addAll(currentNode.getZombies());
					break;
				}
			}
		}
		return result;
	}

	@Override
	public void Render(Bullet owner, Graphics g, double extrapolation, Camera camera) {
		Vector2D locationE = owner.getMovingComponent().extrapolatedLocation(extrapolation);
		Vector2D location = owner.getMovingComponent().getLocation();
		owner.getRender().rotateAndRender(locationE, g, 0, locationE.add(owner.getDirection()), camera);
		owner.getRender().renderCollisionShape(location, g, camera);
	}

	@Override
	public void Exit(Bullet owner) {
		// TODO Auto-generated method stub
	}

}
