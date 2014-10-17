package ch.zombieInvasion.Components;

import ch.zombieInvasion.util.Vector2D;

public class TargetComponent implements BaseComponent {
  private Vector2D position;
  private int minAttackDistance = 0;
  private int maxAttackDistance = 0;


  private boolean enabled = true;

  public TargetComponent(Vector2D position) {
    this.position = position;
  }

  public TargetComponent(Vector2D position, int minAttackDistance, int maxAttackDistance) {
    this.minAttackDistance = minAttackDistance;
    this.maxAttackDistance = maxAttackDistance;
    this.position = position;
  }

  public void setPosition(Vector2D position) {
    this.position = position;
  }

  public Vector2D getPosition() {
    return position;
  }

  public int getMaxAttackDistance() {
    return maxAttackDistance;
  }

  public void setMaxAttackDistance(int maxAttackDistance) {
    this.maxAttackDistance = maxAttackDistance;
  }

  public int getMinAttackDistance() {
    return minAttackDistance;
  }

  public void setMinAttackDistance(int minAttackDistance) {
    this.minAttackDistance = minAttackDistance;
  }

  @Override
  public ComponentType getType() {
    return ComponentType.Target;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public void setEnabled(boolean b) {
    enabled = b;
  }



}
