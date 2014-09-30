package ch.zombieInvasion.Components;

import ch.zombieInvasion.util.Vector2D;

public class MovementComponent implements BaseComponent {
  private boolean enabled = true;
  private double movementSpeed;
  private double mass;
  private Vector2D acceleration = new Vector2D();
  private Vector2D velocity = new Vector2D();

  public MovementComponent(double movementSpeed, double mass) {
    this.setMovementSpeed(movementSpeed);
    this.setMass(mass);
  }

  @Override
  public ComponentType getType() {
    return ComponentType.Movement;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public void setEnabled(boolean b) {
    enabled = b;
  }

  public double getMovementSpeed() {
    return movementSpeed;
  }

  public void setMovementSpeed(double movementSpeed) {
    this.movementSpeed = movementSpeed;
  }

  public double getMass() {
    return mass;
  }

  public void setMass(double mass) {
    this.mass = mass;
  }

  public Vector2D getAcceleration() {
    return acceleration;
  }

  public void setAcceleration(Vector2D acceleration) {
    this.acceleration = acceleration;
  }

  public Vector2D getVelocity() {
    return velocity;
  }

  public void setVelocity(Vector2D velocity) {
    this.velocity = velocity;
  }

}
