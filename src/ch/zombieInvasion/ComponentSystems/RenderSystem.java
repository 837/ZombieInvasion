package ch.zombieInvasion.ComponentSystems;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import ch.zombieInvasion.Components.AppearanceComponent;
import ch.zombieInvasion.Components.PositionComponent;
import ch.zombieInvasion.Objekte.Entity;

public class RenderSystem extends BaseSystem {
  private ArrayList<Entity> entities;
  private ArrayList<String> components = new ArrayList<>();
  private Graphics g;

  public RenderSystem(ArrayList<Entity> entities, Graphics g) {
    this.entities = entities;
    components.add("AppearanceComponent");
    components.add("PositionComponent");
    this.g = g;
  }

  private ArrayList<Entity> entitiesToRender() {
    ArrayList<Entity> toRender = new ArrayList<>();
    entities.forEach(e -> {
      if (e.containsComponents(components)) {
        toRender.add(e);
      }
    });
    return toRender;
  }

  @Override
  public void Update() {
    entitiesToRender().forEach(e -> {
      AppearanceComponent appC = ((AppearanceComponent) e.getComponent("AppearanceComponent"));
      PositionComponent posC = ((PositionComponent) e.getComponent("PositionComponent"));
      g.drawImage(appC.getImage().get(), (float) posC.getPosition().x, (float) posC.getPosition().y);
    });

  }

  @Override
  public void Reset() {
    // TODO Auto-generated method stub

  }

}
