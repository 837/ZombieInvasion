package ch.zombieInvasion;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import ch.zombieInvasion.Camera.Camera;
import ch.zombieInvasion.States.StateMachine;
import ch.zombieInvasion.States.Game.GameStateRunning;
import ch.zombieInvasion.util.LOGGER;

public class Game extends BasicGame {
	public StateMachine<Game> stateMachine;
	public GameContainer container;
	public World world;
	public Camera camera;

	public Game() {
		super("Zombie Invasion Alpha 3.0");
	}

	public static void main(String[] args) throws SlickException {
		// Game container kreieren
		AppGameContainer container = new AppGameContainer(new Game());
		// grösse bildschirm variabel
		// container.setDisplayMode(container.getScreenWidth(),
		// container.getScreenHeight(), true);

		container.setDisplayMode(1280, 768, false);
		container.setVSync(false);
		container.setUpdateOnlyWhenVisible(false);
		container.setClearEachFrame(true);

		LOGGER.LOG("Container start");
		container.start();
		LOGGER.LOG("Container exit");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		stateMachine = new StateMachine<Game>(this);
		stateMachine.SetCurrentState(new GameStateRunning());
		this.container = container;
		world = new World();
		camera = new Camera(container.getWidth(), container.getHeight());
		camera.setMapData(world.map.getWidth()*32, world.map.getHeight()*32);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		// g.drawImage(Images.background.get(), 0, 0);
		stateMachine.Render(g, 0, null);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		stateMachine.Update(this);
	}

}
