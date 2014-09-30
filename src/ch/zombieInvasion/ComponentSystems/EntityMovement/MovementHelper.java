package ch.zombieInvasion.ComponentSystems.EntityMovement;

import ch.zombieInvasion.util.Vector2D;

public class MovementHelper {
  // steeringbehaviors
  public Vector2D seek(Vector2D targetLocation, Vector2D location, Vector2D velocity, Vector2D acceleration, double maxSpeed, double maxForce, double mass) {
    return applyForce(targetLocation.sub(location).normalize().mult(maxSpeed).sub(velocity), acceleration, maxForce, mass);
  }


  public Vector2D applyForce(Vector2D force, Vector2D acceleration, double maxForce, double mass) {
    return acceleration.add(force.limit(maxForce).div(mass));
  }
}
