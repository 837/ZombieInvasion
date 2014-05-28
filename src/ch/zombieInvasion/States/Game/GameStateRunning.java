package ch.zombieInvasion.States.Game;

import java.util.Random;
import java.util.stream.IntStream;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import ch.zombieInvasion.Game;
import ch.zombieInvasion.Camera.Camera;
import ch.zombieInvasion.Eventhandling.EventDispatcher;
import ch.zombieInvasion.Eventhandling.EventType;
import ch.zombieInvasion.States.BaseState;
import ch.zombieInvasion.Zombies.Zombie;
import ch.zombieInvasion.util.LOGGER;
import ch.zombieInvasion.util.Timer;
import ch.zombieInvasion.util.Vector2D;

public class GameStateRunning implements BaseState<Game> {
	private final int TICKS_PER_SECOND = 30;
	private final double SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	private final int MAX_FRAMESKIP = 5;
	private double next_game_tick = System.currentTimeMillis();
	private int loops;
	private double extrapolation;

	boolean spawning = false;
	Timer t = new Timer();

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
			next_game_tick += SKIP_TICKS;
			loops++;
		}
		if (next_game_tick > System.currentTimeMillis() + SKIP_TICKS) {
			next_game_tick = System.currentTimeMillis() + SKIP_TICKS;
		}
		Vector2D mouseStart = new Vector2D(game.container.getInput().getAbsoluteMouseX() + game.camera.getCamX(), game.container.getInput()
				.getAbsoluteMouseY() + game.camera.getCamY());

		if (game.container.getInput().isKeyPressed(Input.KEY_H)) {
			IntStream.range(0, 10).forEach(
					e -> game.world.eManager.addZombie(new Zombie(new Vector2D(new Random().nextInt(100) + mouseStart.x, new Random().nextInt(100)
							+ mouseStart.y))));
		}
		if (game.container.getInput().isKeyPressed(Input.KEY_G)) {
			EventDispatcher.createEvent(0, EventType.KILL_ALL_ZOMBIES, null);
		}

		if (game.container.getInput().isKeyPressed(Input.KEY_DELETE)) {
			game.world.eManager.deleteAll();
		}

		if (game.container.getInput().isMousePressed(1)) {
			Vector2D additonalInfo = new Vector2D(game.container.getInput().getAbsoluteMouseX() + game.camera.getCamX(), game.container.getInput()
					.getAbsoluteMouseY() + game.camera.getCamY());

			if (game.container.getInput().isKeyDown(Input.KEY_LSHIFT)) {
				EventDispatcher.createEvent(0, EventType.MoveToPosQueued, additonalInfo);
			} else {
				EventDispatcher.createEvent(0, EventType.MoveToPos, additonalInfo);
			}
		}

		if (game.container.getInput().isKeyPressed(Input.KEY_S)) {
			spawning = !spawning;
		}
		if (spawning && t.getSeconds() >= 0.1) {
			for (int x = 0; x < game.world.map.getWidth(); x++) {
				for (int y = 0; y < game.world.map.getHeight(); y++) {
					if (game.world.map.getTileId(x, y, 1) == 110) {
						game.world.eManager.addZombie(new Zombie(new Vector2D(x * 32 + 16, y * 32 + 16)));
					}
				}
			}
			t.restart();
		}

		if (game.container.getInput().isKeyDown(Input.KEY_LCONTROL) && game.container.getInput().isKeyDown(Input.KEY_Z)) {
			if (game.world.eManager.getObstacle().size() > 0) {
				EventDispatcher.createEvent(0, EventType.DELETE_ME,
						game.world.eManager.getObstacle().get(game.world.eManager.getObstacle().size() - 1));
			}
		}

		EventDispatcher.getEvents().forEach(e -> {
			switch (e.getEvent()) {
				case Destroy_Tower:

					System.out.println("destroy tower");

				break;
			}
		});

		extrapolation = (System.currentTimeMillis() + SKIP_TICKS - next_game_tick) / SKIP_TICKS;
		if (extrapolation > 1) {
			extrapolation = 1;
		}
	}

	@Override
	public void Render(Game owner, Graphics g, double extrapolationHereUnused, Camera cameraHereUnused) {
		owner.world.Render(g, extrapolation, owner.camera);
	}

	@Override
	public void Exit(Game owner) {
		LOGGER.LOG("Exiting StateRunning");
	}

}
