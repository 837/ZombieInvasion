package ch.zombieInvasion.ComponentSystems;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import ch.zombieInvasion.Components.AppearanceComponent;
import ch.zombieInvasion.Components.ComponentType;
import ch.zombieInvasion.Components.PositionComponent;
import ch.zombieInvasion.Objekte.Entity;
import ch.zombieInvasion.util.Images;
import ch.zombieInvasion.util.Vector2D;


public class RenderSystem extends BaseSystem {
  private ArrayList<Entity> entities;
  private ArrayList<ComponentType> essentialComponents = new ArrayList<>();
  private Graphics g;
  private double extrapolation;

  public RenderSystem(ArrayList<Entity> entities, Graphics g, double extrapolation) {
    this.entities = entities;
    this.extrapolation = extrapolation;
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
    entitiesToConsider().forEach(
        e -> {
          AppearanceComponent appC =
              ((AppearanceComponent) e.getComponent(ComponentType.Appearance));
          PositionComponent posC = ((PositionComponent) e.getComponent(ComponentType.Position));

          Vector2D extrapolatedPosition =
              posC.getPosition().add(posC.getVelocity().mult(extrapolation));

          if (appC.isEnabled() && posC.isEnabled()) {
            g.drawImage(Images.getImage(appC.getImageType()).getImg(),
                (float) extrapolatedPosition.x, (float) extrapolatedPosition.y);
          }
        });
  }

  @Override
  public void Reset() {
    // TODO Auto-generated method stub
  }

}
