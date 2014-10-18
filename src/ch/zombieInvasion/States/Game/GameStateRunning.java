package ch.zombieInvasion.States.Game;

import java.util.Random;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.Camera.Camera;
import ch.zombieInvasion.ComponentSystems.RenderSystem;
import ch.zombieInvasion.Components.AppearanceComponent;
import ch.zombieInvasion.Components.ComponentType;
import ch.zombieInvasion.Components.MovementComponent;
import ch.zombieInvasion.Components.PositionComponent;
import ch.zombieInvasion.Components.TargetComponent;
import ch.zombieInvasion.Objekte.Entity;
import ch.zombieInvasion.States.BaseState;
import ch.zombieInvasion.util.ImageTypes;
import ch.zombieInvasion.util.Vector2D;

public class GameStateRunning implements BaseState<Game> {
  private final int TICKS_PER_SECOND = 30;
  private final double timePerTick = 1000 / TICKS_PER_SECOND;
  private final int MAX_FRAMESKIP = 5;
  private double next_game_tick = System.currentTimeMillis();
  private int loops;
  private double extrapolation;

  @Override
  public void Enter(Game owner) {}

  @Override
  public void Update(Game owner, Game game) {
    loops = 0;
    while (System.currentTimeMillis() > next_game_tick && loops < MAX_FRAMESKIP) {
      owner.getWorld().Update(game);
      game.getEventDispatcher().Update();

      Input input = game.getContainer().getInput();

      Vector2D mousePos =
          game.getCamera().getPositionInWorld(
              new Vector2D(input.getAbsoluteMouseX(), input.getAbsoluteMouseY()));

      if (game.getContainer().getInput().isKeyPressed(Input.KEY_DELETE)) {
        game.getWorld().eManager.deleteAll();
      }



      if (game.getContainer().getInput().isKeyPressed(Input.KEY_Z)) {
        for (int i = 0; i < 100; i++) {
          Entity e = new Entity();
          e.addComponent(new PositionComponent(new Vector2D(new Random().nextInt(800), new Random()
              .nextInt(800))));
          e.addComponent(new AppearanceComponent(ImageTypes.hardZombie));
          e.addComponent(new MovementComponent(3, 1, 0.5));
          e.addComponent(new TargetComponent(mousePos, 0, 300));
          game.getWorld().eManager.addEntity(e);
        }
      }
      if (game.getContainer().getInput().isKeyPressed(Input.KEY_H)) {
        Entity e = new Entity();
        e.addComponent(new PositionComponent(mousePos));
        e.addComponent(new AppearanceComponent(ImageTypes.normalZombie));
        e.addComponent(new MovementComponent(5, 2, 1));
        e.addComponent(new TargetComponent(mousePos, 0, 300));
        game.getWorld().eManager.addEntity(e);
      }

      game.getEventDispatcher().getEvents().forEach(e -> {
        switch (e.getEvent()) {
          case Reset_Game:
            game.getWorld().eManager.deleteAll();
            game.getCamera().setPosition(new Vector2D());
            break;
        }
      });

      owner.getWorld().eManager.getEntities().forEach(e -> {
        if (e.hasComponent(ComponentType.Target)) {
          TargetComponent tarC = (TargetComponent) e.getComponent(ComponentType.Target);
          tarC.setPosition(mousePos);
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
    owner.getWorld().Render(g, extrapolation, owner.getCamera());

    new RenderSystem(owner.getWorld().eManager.getEntities(), owner.getContainer().getGraphics(),
        extrapolation, owner.getCamera()).Update();

  }

  @Override
  public void Exit(Game owner) {}

}
