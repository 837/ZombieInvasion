package ch.zombieInvasion.ComponentSystems.EntityMovement;

import java.util.ArrayList;
import ch.zombieInvasion.ComponentSystems.BaseSystem;
import ch.zombieInvasion.Components.MovementComponent;
import ch.zombieInvasion.Components.PositionComponent;
import ch.zombieInvasion.Components.TargetComponent;
import ch.zombieInvasion.Components.WanderMovementComponent;
import ch.zombieInvasion.Components.base.ComponentType;
import ch.zombieInvasion.Objekte.Entity;


public class EntityMovementSystem extends BaseSystem {
  private ArrayList<Entity> entities;
  private ArrayList<ComponentType> essentialComponents = new ArrayList<>();

  public EntityMovementSystem(ArrayList<Entity> entities) {
    this.entities = entities;
    essentialComponents.add(ComponentType.Movement);
    essentialComponents.add(ComponentType.Position);
    essentialComponents.add(ComponentType.Target);
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
          MovementComponent movC = ((MovementComponent) e.getComponent(ComponentType.Movement));
          PositionComponent posC = ((PositionComponent) e.getComponent(ComponentType.Position));
          TargetComponent tarC = ((TargetComponent) e.getComponent(ComponentType.Target));
          WanderMovementComponent wamC =
              ((WanderMovementComponent) e.getComponent(ComponentType.WanderMovement));

          if (movC.isEnabled() && posC.isEnabled() & tarC.isEnabled()) {
            if (tarC.getPosition().dist(posC.getPosition()) <= tarC.getMaxAttackDistance()) {
              MovementHelper.arrive(tarC.getPosition(), posC, movC);
              MovementHelper.movementUpdateFinished(movC, posC);
            } else {
              if (wamC != null && wamC.isEnabled()) {
                MovementHelper.wander(posC, movC, wamC);
                MovementHelper.movementUpdateFinished(movC, posC);
              } else {
                MovementHelper.arrive(posC.getPosition(), posC, movC);
                MovementHelper.movementUpdateFinished(movC, posC);
              }
            }
          }

        });
  }

  @Override
  public void Reset() {
    // TODO Auto-generated method stub

  }

}
