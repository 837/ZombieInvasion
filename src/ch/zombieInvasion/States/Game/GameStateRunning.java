package ch.zombieInvasion.States.Game;

import java.util.Random;
import java.util.stream.IntStream;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.Camera.Camera;
import ch.zombieInvasion.Eventhandling.EventDispatcher;
import ch.zombieInvasion.Eventhandling.EventType;
import ch.zombieInvasion.States.BaseState;
import ch.zombieInvasion.Weapons.Bullet;
import ch.zombieInvasion.Zombies.Zombie;
import ch.zombieInvasion.util.LOGGER;
import ch.zombieInvasion.util.Timer;
import ch.zombieInvasion.util.Vector2D;

public class GameStateRunning implements BaseState<Game> {
	private final int TICKS_PER_SECOND = 30;
	private final double timePerTick = 1000 / TICKS_PER_SECOND;
	private final int MAX_FRAMESKIP = 5;
	private double next_game_tick = System.currentTimeMillis();
	private int loops;
	private double extrapolation;

	boolean spawning = false;
	Timer t = new Timer();
	Timer t2 = new Timer();

	int gamelife = 10;

	@Override
	public void Enter(Game owner) {
		LOGGER.LOG("Entering StateRunning");
	}

	@Override
	public void Update(Game owner, Game game) {
		loops = 0;
		while (System.currentTimeMillis() > next_game_tick && loops < MAX_FRAMESKIP) {
			owner.world.Update(game);
			EventDispatcher.dispatchEvents();

			Vector2D mouseStart = new Vector2D(game.container.getInput().getAbsoluteMouseX() + game.camera.getCamX(), game.container.getInput()
					.getAbsoluteMouseY() + game.camera.getCamY());

			if (game.container.getInput().isKeyPressed(Input.KEY_H)) {
				IntStream.range(0, 10).forEach(
						e -> game.world.eManager.addZombie(new Zombie(new Vector2D(new Random().nextInt(100) + mouseStart.x, new Random()
								.nextInt(100) + mouseStart.y))));
			}
			if (game.container.getInput().isKeyPressed(Input.KEY_G)) {
				EventDispatcher.createEvent(0, EventType.KILL_ALL_ZOMBIES, null);
			}

			if (game.container.getInput().isKeyPressed(Input.KEY_DELETE)) {
				game.world.eManager.deleteAll();
			}

			if (game.container.getInput().isMousePressed(1)) {
				Vector2D additonalInfo = new Vector2D(game.container.getInput().getAbsoluteMouseX() + game.camera.getCamX(), game.container
						.getInput().getAbsoluteMouseY() + game.camera.getCamY());

				if (game.container.getInput().isKeyDown(Input.KEY_LSHIFT)) {
					EventDispatcher.createEvent(0, EventType.MoveToPosQueued, additonalInfo);
				} else {
					EventDispatcher.createEvent(0, EventType.MoveToPos, additonalInfo);
				}
			}

			if (game.container.getInput().isKeyPressed(Input.KEY_Z)) {
				spawning = !spawning;
			}
			if (spawning && t.getSeconds() >= 1.0) {
				for (int x = 0; x < game.world.map.getWidth(); x++) {
					for (int y = 0; y < game.world.map.getHeight(); y++) {
						if (game.world.map.getTileId(x, y, 1) == 110) {
							game.world.eManager.addZombie(new Zombie(new Vector2D(x * 32 + 16, y * 32 + 16)));
						}
					}
				}
				t.restart();
			}

			EventDispatcher.getEvents().forEach(
					e -> {
						switch (e.getEvent()) {
							case Destroy_Tower:
								System.out.println("destroy tower");
								EventDispatcher.removePersistentEvent(e);
							break;

							case FireAt:
								game.world.eManager.addBullet(new Bullet((Vector2D) e.getAdditionalInfos().get(0), (Vector2D) e.getAdditionalInfos()
										.get(1), (int) e.getAdditionalInfos().get(2), (int) e.getAdditionalInfos().get(3)));
								EventDispatcher.removePersistentEvent(e);
								System.out.println("shot fired");
							break;

							case ReachedTarget:
								gamelife--;
								EventDispatcher.removePersistentEvent(e);

								System.out.println("zombie made it to the target");
								if (gamelife <= 0) {
									game.container.exit();
								}
							break;
						}
					});

			owner.container.getInput().addMouseListener(new MouseListener() {

				@Override
				public void setInput(Input input) {
					// TODO Auto-generated method stub

				}

				@Override
				public boolean isAcceptingInput() {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public void inputStarted() {
					// TODO Auto-generated method stub

				}

				@Override
				public void inputEnded() {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseWheelMoved(int change) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseReleased(int button, int x, int y) {
					if (button == 0) {
						Vector2D additonalInfo = new Vector2D(x + game.camera.getCamX(), y + game.camera.getCamY());
						EventDispatcher.createEvent(0, EventType.LeftUp, additonalInfo);
					}

				}

				@Override
				public void mousePressed(int button, int x, int y) {
					if (button == 0) {
						Vector2D additonalInfo = new Vector2D(x + game.camera.getCamX(), y + game.camera.getCamY());
						EventDispatcher.createEvent(0, EventType.LeftDown, additonalInfo);
					}
				}

				@Override
				public void mouseMoved(int oldx, int oldy, int newx, int newy) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseDragged(int oldx, int oldy, int newx, int newy) {
					if (t2.getSeconds() >= 0.1) {

						Vector2D additonalInfo = new Vector2D(newx + game.camera.getCamX(), newy + game.camera.getCamY());
						EventDispatcher.createEvent(0, EventType.LeftDrag, additonalInfo);
						t2.restart();
					}
				}

				@Override
				public void mouseClicked(int button, int x, int y, int clickCount) {
					// TODO Auto-generated method stub

				}
			});
			owner.selector.Update(game);

			next_game_tick += timePerTick;
			loops++;
		}

		if (next_game_tick < System.currentTimeMillis()) {
			next_game_tick = System.currentTimeMillis();
		}

		extrapolation = 1 - (next_game_tick - System.currentTimeMillis()) / timePerTick;
	}

	@Override
	public void Render(Game owner, Graphics g, double extrapolationHereUnused, Camera cameraHereUnused) {
		owner.world.Render(g, extrapolation, owner.camera);
		owner.selector.Render(g, extrapolationHereUnused, owner.camera);
	}

	@Override
	public void Exit(Game owner) {
		LOGGER.LOG("Exiting StateRunning");
	}

}
