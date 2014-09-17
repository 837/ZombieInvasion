package ch.zombieInvasion.States.Game;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.Camera.Camera;
import ch.zombieInvasion.ComponentSystems.RenderSystem;
import ch.zombieInvasion.Components.AppearanceComponent;
import ch.zombieInvasion.Components.PositionComponent;
import ch.zombieInvasion.Eventhandling.EventDispatcher;
import ch.zombieInvasion.Eventhandling.EventType;
import ch.zombieInvasion.Objekte.Entity;
import ch.zombieInvasion.States.BaseState;
import ch.zombieInvasion.util.ImageM;
import ch.zombieInvasion.util.Images;
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
      Input input = game.container.getInput();

      Vector2D mousePos = game.camera.getPositionInWorld(new Vector2D(input.getAbsoluteMouseX(), input.getAbsoluteMouseY()));


      if (game.container.getInput().isKeyPressed(Input.KEY_DELETE)) {
        game.world.eManager.deleteAll();
      }

      if (game.container.getInput().isKeyPressed(Input.KEY_J)) {
        for (int i = 0; i < 10; i++) {
          Entity e = new Entity();
          e.addComponent(new PositionComponent(new Vector2D(new Random().nextInt(800), new Random().nextInt(800))));

          game.world.eManager.addEntity(e);
        }
      }
      if (game.container.getInput().isKeyPressed(Input.KEY_K)) {
        ArrayList<String> names = new ArrayList<>();
        names.add("AppearanceComponent");
        game.world.eManager.getEntities().stream().filter(e -> e.containsComponents(names)).findAny().get().addComponent(new AppearanceComponent(Images.hardZombie));
      }
      EventDispatcher.getEvents().forEach(e -> {
        switch (e.getEvent()) {
          case Reset_Game:
            game.world.eManager.deleteAll();
            game.camera.setPosition(new Vector2D());
            break;
        }
      });

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
    RenderSystem rs = new RenderSystem(owner.world.eManager.getEntities(), owner.container.getGraphics());
    rs.Update();
  }

  @Override
  public void Exit(Game owner) {
    LOGGER.LOG("Exiting StateRunning");
  }

}
