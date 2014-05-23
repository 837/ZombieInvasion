package ch.zombieInvasion.States.Game;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.Camera.Camera;
import ch.zombieInvasion.Eventhandling.EventDispatcher;
import ch.zombieInvasion.Eventhandling.EventType;
import ch.zombieInvasion.Objekte.Obstacle;
import ch.zombieInvasion.States.BaseState;
import ch.zombieInvasion.Zombies.Zombie;
import ch.zombieInvasion.util.LOGGER;
import ch.zombieInvasion.util.Vector2D;

public class GameStateRunning implements BaseState<Game> {
	private final int TICKS_PER_SECOND = 30;
	private final double SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	private final int MAX_FRAMESKIP = 5;
	private double next_game_tick = System.currentTimeMillis();
	private int loops;
	private double extrapolation;

	int QUADSIZE = 25;
	boolean circle = true;

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
					e -> game.world.eManager.addZombie(new Zombie(new Vector2D(new Random().nextInt(100) + mouseStart.x, new Random().nextInt(100) + mouseStart.y))));
		}
		if (game.container.getInput().isKeyPressed(Input.KEY_G)) {
			EventDispatcher.createEvent(0, EventType.KILL_ALL_ZOMBIES, null);
		}

		if (game.container.getInput().isKeyPressed(Input.KEY_I)) {
			Obstacle ob = new Obstacle(mouseStart, QUADSIZE, QUADSIZE, circle);
			game.world.eManager.addObstacle(ob);
		}

		if (game.container.getInput().isKeyPressed(Input.KEY_U)) {
			circle = !circle;
		}

		if (game.container.getInput().isKeyPressed(Input.KEY_O)) {
			for (int i = 0; i < 500; i += QUADSIZE) {
				Obstacle ob = new Obstacle(mouseStart.add(new Vector2D(i, 0)), QUADSIZE, QUADSIZE, circle);
				game.world.eManager.addObstacle(ob);
			}

		}

		if (game.container.getInput().isKeyPressed(Input.KEY_P)) {
			for (int i = 0; i < 500; i += QUADSIZE) {
				Obstacle ob = new Obstacle(mouseStart.add(new Vector2D(0, i)), QUADSIZE, QUADSIZE, circle);
				game.world.eManager.addObstacle(ob);
			}

		}

		if (game.container.getInput().isKeyPressed(Input.KEY_DELETE)) {
			game.world.eManager.deleteAll();

		}

		if (game.container.getInput().isKeyPressed(Input.KEY_SUBTRACT)) {
			QUADSIZE -= 2;
			if (QUADSIZE <= 2) {
				QUADSIZE = 2;
			}
		}

		if (game.container.getInput().isKeyPressed(Input.KEY_ADD)) {
			QUADSIZE += 2;
			if (QUADSIZE >= 500) {
				QUADSIZE = 500;
			}
		}

		if (game.container.getInput().isKeyDown(Input.KEY_LCONTROL) && game.container.getInput().isKeyDown(Input.KEY_Z)) {
			if (game.world.eManager.getObstacle().size() > 0) {
				ArrayList<Object> addInfo = new ArrayList<>();
				addInfo.add(game.world.eManager.getObstacle().get(game.world.eManager.getObstacle().size() - 1));
				EventDispatcher.createEvent(0, EventType.DELETE_ME, addInfo);
			}
		}

		// System.out.println(game.world.eManager.getZombies().size());
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
