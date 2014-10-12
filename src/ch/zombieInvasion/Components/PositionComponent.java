package ch.zombieInvasion.Components;

import ch.zombieInvasion.util.Vector2D;

public class PositionComponent implements BaseComponent {
  private Vector2D position;
  private Vector2D velocity = new Vector2D();
  private boolean enabled = true;

  public PositionComponent(Vector2D position) {
    this.position = position;
  }

  public void setPosition(Vector2D position) {
    this.position = position;
  }

  public Vector2D getPosition() {
    return position;
  }

  public Vector2D getVelocity() {
    return velocity;
  }

  public void setVelocity(Vector2D velocity) {
    this.velocity = velocity;
  }

  @Override
  public ComponentType getType() {
    return ComponentType.Position;
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
