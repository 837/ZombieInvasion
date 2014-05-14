package ch.zombieInvasion;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import ch.zombieInvasion.Objekte.Player;
import ch.zombieInvasion.States.StateMachine;
import ch.zombieInvasion.States.Game.GameStateRunning;
import ch.zombieInvasion.util.Images;
import ch.zombieInvasion.util.LOGGER;
import ch.zombieInvasion.util.Vector2D;

public class Game extends BasicGame {
	public StateMachine<Game> stateMachine;
	public EntityManager eManager;
	public GameContainer container;

	public Game() {
		super("Zombie Invasion Alpha 2.1");
	}

	public static void main(String[] args) throws SlickException {
		// Game container kreieren
		AppGameContainer container = new AppGameContainer(new Game());
		// grösse bildschirm variabel
		// container.setDisplayMode(container.getScreenWidth(),
		// container.getScreenHeight(), true);

		container.setDisplayMode(1280, 768, false);
		container.setVSync(true);

		container.setClearEachFrame(false);

		LOGGER.LOG("Container start");
		container.start();
		LOGGER.LOG("Container exit");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		stateMachine = new StateMachine<Game>(this);
		stateMachine.SetCurrentState(new GameStateRunning());
		eManager = new EntityManager();
		this.container = container;
		eManager.addPlayer(new Player(new Vector2D(500,500)));

	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.drawImage(Images.background.get(), 0, 0);
		stateMachine.Render(g, 0);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		stateMachine.Update(this);
	}

}
