package ch.zombieInvasion;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import ch.zombieInvasion.Camera.Camera;
import ch.zombieInvasion.Eventhandling.EventDispatcher;
import ch.zombieInvasion.States.StateMachine;
import ch.zombieInvasion.States.Game.GameStateRunning;
import ch.zombieInvasion.util.LOGGER;

public class Game extends BasicGame {
  private StateMachine<Game> stateMachine;
  private GameContainer container;
  private World world;
  private Camera camera;
  private EventDispatcher eventDispatcher;

  private static String GAMENAME = "Zombie Invasion ";
  private static String VERSION = "Alpha 3.0.2";

  public Game() {
    super(GAMENAME + VERSION);
  }

  public static void main(String[] args) throws SlickException {
    AppGameContainer container = new AppGameContainer(new Game());
    container.setDisplayMode(1280, 780, false);
    container.setVSync(true);
  //  container.setTargetFrameRate(60);
    container.setUpdateOnlyWhenVisible(true);
    container.setClearEachFrame(true);
    container.start();
  }

  @Override
  public void init(GameContainer container) throws SlickException {
    // Statemachine
    setStateMachine(new StateMachine<Game>(this));
    getStateMachine().SetCurrentState(new GameStateRunning());

    this.setContainer(container);
    // world
    setWorld(new World());

    // camera
    setCamera(new Camera(container.getWidth(), container.getHeight()));
    getCamera().setMapData(getWorld().map.getWidth() * getWorld().map.getTileWidth(),
        getWorld().map.getHeight() * getWorld().map.getTileHeight());

    // eventdispatcher
    setEventDispatcher(new EventDispatcher());

    LOGGER.LOG("Game started!");
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

  public GameContainer getContainer() {
    return container;
  }

  public void setContainer(GameContainer container) {
    this.container = container;
  }

  public EventDispatcher getEventDispatcher() {
    return eventDispatcher;
  }

  public void setEventDispatcher(EventDispatcher eventDispatcher) {
    this.eventDispatcher = eventDispatcher;
  }

  public Camera getCamera() {
    return camera;
  }

  public void setCamera(Camera camera) {
    this.camera = camera;
  }

  public World getWorld() {
    return world;
  }

  public void setWorld(World world) {
    this.world = world;
  }

  public StateMachine<Game> getStateMachine() {
    return stateMachine;
  }

  public void setStateMachine(StateMachine<Game> stateMachine) {
    this.stateMachine = stateMachine;
  }
}
