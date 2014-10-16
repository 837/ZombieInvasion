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

  private static String GAMENAME = "Zombie Invasion ";
  private static String VERSION = "Alpha 3.0.1";

  public Game() {
    super(GAMENAME + VERSION);
  }

  public static void main(String[] args) throws SlickException {
    AppGameContainer container = new AppGameContainer(new Game());
    container.setDisplayMode(1280, 780, false);
    container.setVSync(false);
    container.setUpdateOnlyWhenVisible(true);
    container.setClearEachFrame(true);
    LOGGER.LOG("Game started!");
    container.start();
  }

  @Override
  public void init(GameContainer container) throws SlickException {
    stateMachine = new StateMachine<Game>(this);
    stateMachine.SetCurrentState(new GameStateRunning());
    this.container = container;
    world = new World();
    camera = new Camera(container.getWidth(), container.getHeight());
    camera.setMapData(world.map.getWidth() * world.map.getTileWidth(), world.map.getHeight() * world.map.getTileHeight());
  }

  @Override
  public void render(GameContainer container, Graphics g) throws SlickException {
    stateMachine.Render(g, 0, null);
  }

  @Override
  public void update(GameContainer container, int delta) throws SlickException {
    stateMachine.Update(this);
  }

  @Override
  public boolean closeRequested() {
    LOGGER.LOG("Game stopped!");
    return super.closeRequested();
  }
}
