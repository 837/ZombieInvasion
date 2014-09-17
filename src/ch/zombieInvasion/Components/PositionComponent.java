package ch.zombieInvasion.Components;

import ch.zombieInvasion.util.Vector2D;

import com.google.gson.Gson;


public class PositionComponent implements BaseComponent {
  private String componentName = "PositionComponent";
  private Vector2D position;

  public PositionComponent(Vector2D position) {
    this.position = position;
  }

  public Vector2D getPosition() {
    return position;
  }

  @Override
  public String getName() {
    return componentName;
  }

  @Override
  public String toJSON() {
    return new Gson().toJson(this);
  }
}
