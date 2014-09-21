package ch.zombieInvasion.Components;

import ch.zombieInvasion.util.Vector2D;

public class PositionComponent implements BaseComponent {
  private ComponentType type = ComponentType.Position;
  private Vector2D position;
  private boolean enabled = true;

  public PositionComponent() {

  }

  public PositionComponent(Vector2D position) {
    this.position = position;
  }

  public void setPosition(Vector2D position) {
    this.position = position;
  }

  public Vector2D getPosition() {
    return position;
  }

  public void setType(ComponentType type) {
    this.type = type;
  }

  @Override
  public ComponentType getType() {
    return type;
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
