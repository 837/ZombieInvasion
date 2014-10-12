package ch.zombieInvasion.Components;

import ch.zombieInvasion.util.Vector2D;

public class MovementComponent implements BaseComponent {
  private boolean enabled = true;
  private double maxSpeed;
  private double mass;
  private double maxForce;
  private Vector2D acceleration = new Vector2D();
  

  public MovementComponent(double maxSpeed, double mass, double maxForce) {
    this.setMaxSpeed(maxSpeed);
    this.setMass(mass);
    this.setMaxForce(maxForce);
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

  public double getMaxSpeed() {
    return maxSpeed;
  }

  public void setMaxSpeed(double maxSpeed) {
    this.maxSpeed = maxSpeed;
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
  
  public double getMaxForce() {
    return maxForce;
  }

  public void setMaxForce(double maxForce) {
    this.maxForce = maxForce;
  }

}
