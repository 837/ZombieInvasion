package ch.zombieInvasion.ComponentSystems;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import ch.zombieInvasion.Components.AppearanceComponent;
import ch.zombieInvasion.Components.ComponentType;
import ch.zombieInvasion.Components.PositionComponent;
import ch.zombieInvasion.Objekte.Entity;
import ch.zombieInvasion.util.Images;

public class RenderSystem extends BaseSystem {
  private ArrayList<Entity> entities;
  private ArrayList<ComponentType> essentialComponents = new ArrayList<>();
  private Graphics g;

  public RenderSystem(ArrayList<Entity> entities, Graphics g) {
    this.entities = entities;
    essentialComponents.add(ComponentType.Appearance);
    essentialComponents.add(ComponentType.Position);
    this.g = g;
  }

  private ArrayList<Entity> entitiesToConsider() {
    ArrayList<Entity> toConsider = new ArrayList<>();
    entities.stream().forEach(e -> {
      if (e.hasComponents(essentialComponents)) {
        toConsider.add(e);
      }
    });
    return toConsider;
  }

  @Override
  public void Update() {
    entitiesToConsider().forEach(e -> {
      AppearanceComponent appC = ((AppearanceComponent) e.getComponent(ComponentType.Appearance));
      PositionComponent posC = ((PositionComponent) e.getComponent(ComponentType.Position));

      if (appC.isEnabled() && posC.isEnabled()) {
        g.drawImage(Images.getImage(appC.getImageType()).getImg(), (float) posC.getPosition().x, (float) posC.getPosition().y);
      }
    });
  }

  @Override
  public void Reset() {
    // TODO Auto-generated method stub
  }

}
