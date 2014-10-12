package ch.zombieInvasion.ComponentSystems.EntityMovement;

import java.util.ArrayList;

import ch.zombieInvasion.ComponentSystems.BaseSystem;
import ch.zombieInvasion.Components.ComponentType;
import ch.zombieInvasion.Components.MovementComponent;
import ch.zombieInvasion.Components.PositionComponent;
import ch.zombieInvasion.Objekte.Entity;
import ch.zombieInvasion.util.Vector2D;

public class EntityMovementSystem extends BaseSystem {
  private ArrayList<Entity> entities;
  private ArrayList<ComponentType> essentialComponents = new ArrayList<>();

  public EntityMovementSystem(ArrayList<Entity> entities) {
    this.entities = entities;
    essentialComponents.add(ComponentType.Movement);
    essentialComponents.add(ComponentType.Position);
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
      MovementComponent movC = ((MovementComponent) e.getComponent(ComponentType.Movement));
      PositionComponent posC = ((PositionComponent) e.getComponent(ComponentType.Position));

      if (movC.isEnabled() && posC.isEnabled()) {
        MovementHelper.arrive(new Vector2D(555, 555), posC, movC);
        MovementHelper.movementUpdateFinished(movC);
      }
    });
  }

  @Override
  public void Reset() {
    // TODO Auto-generated method stub

  }

}
