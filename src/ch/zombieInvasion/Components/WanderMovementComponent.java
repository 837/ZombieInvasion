package ch.zombieInvasion.Components;

import ch.zombieInvasion.Components.base.BaseComponent;
import ch.zombieInvasion.Components.base.ComponentType;

public class WanderMovementComponent extends BaseComponent {
  private double circleDistance = 20;
  private double circleRadius = 10;
  private double wanderAngle;
  private double wanderMovementMaxSpeed;
  private double angleChange = 1;

  /**
   * Default params are:
   * 
   * circleDistance=6; circleRadius=8; angleChange=1
   * 
   * @param wanderMovementMaxSpeed
   */
  public WanderMovementComponent(double wanderMovementMaxSpeed) {
    this.wanderMovementMaxSpeed = wanderMovementMaxSpeed;
  }

  public WanderMovementComponent(double wanderMovementMaxSpeed, double circleDistance,
      double circleRadius, double angleChange) {
    this.wanderMovementMaxSpeed = wanderMovementMaxSpeed;
    this.angleChange = angleChange;
    this.circleDistance = circleDistance;
    this.circleRadius = circleRadius;
  }

  public double getCircleDistance() {
    return circleDistance;
  }

  public void setCircleDistance(double circleDistance) {
    this.circleDistance = circleDistance;
  }

  public double getCircleRadius() {
    return circleRadius;
  }

  public void setCircleRadius(double circleRadius) {
    this.circleRadius = circleRadius;
  }

  public double getWanderAngle() {
    return wanderAngle;
  }

  public void setWanderAngle(double wanderAngle) {
    this.wanderAngle = wanderAngle;
  }

  public double getWanderMovementSpeed() {
    return wanderMovementMaxSpeed;
  }

  public void setWanderMovementSpeed(double wanderMovementSpeed) {
    this.wanderMovementMaxSpeed = wanderMovementSpeed;
  }

  public double getAngleChange() {
    return angleChange;
  }

  public void setAngleChange(double angleChange) {
    this.angleChange = angleChange;
  }

  @Override
  public ComponentType getType() {
    return ComponentType.WanderMovement;
  }

}
