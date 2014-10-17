package ch.zombieInvasion.ComponentSystems.EntityMovement;

import ch.zombieInvasion.Components.MovementComponent;
import ch.zombieInvasion.Components.PositionComponent;
import ch.zombieInvasion.util.Util;
import ch.zombieInvasion.util.Vector2D;

public class MovementHelper {
  // steeringbehaviors
  public static void seek(Vector2D targetLocation, PositionComponent posC, MovementComponent movC) {
    Vector2D desired = targetLocation.sub(posC.getPosition());
    desired = desired.normalize();
    desired = desired.mult(movC.getMaxSpeed());

    Vector2D steer = desired.sub(posC.getVelocity());
    steer = steer.limit(movC.getMaxForce());
    applyForce(steer, movC);
    addToVelocity(movC, posC);
    limitVelocity(movC, posC);
    posC.setPosition(posC.getPosition().add(posC.getVelocity()));
  }

  public static void arrive(Vector2D targetLocation, PositionComponent posC, MovementComponent movC) {
    Vector2D desired = targetLocation.sub(posC.getPosition());

    double d = desired.magSq();
    desired = desired.normalize();

    if (d < (20 * 20)) {
      float m = (float) (movC.getMaxSpeed() / 4 * Math.log10(d + 1));// LOG
      // float m = Util.map((float) d, 0, (20 * 20), 0, (float) movC.getMaxSpeed());// LIN
      desired = desired.mult(m);
    } else {
      desired = desired.mult(movC.getMaxSpeed());
    }

    Vector2D steer = desired.sub(posC.getVelocity());
    steer = steer.limit(movC.getMaxForce());
    applyForce(steer, movC);
    addToVelocity(movC, posC);
    limitVelocity(movC, posC);
    posC.setPosition(posC.getPosition().add(posC.getVelocity()));
  }

  public static void applyForce(Vector2D force, MovementComponent movC) {
    movC.setAcceleration(movC.getAcceleration().add(force.div(movC.getMass())));
  }

  private static void addToVelocity(MovementComponent movC, PositionComponent posC) {
    posC.setVelocity(posC.getVelocity().add(movC.getAcceleration()));
  }

  private static void limitVelocity(MovementComponent movC, PositionComponent posC) {
    posC.setVelocity(posC.getVelocity().limit(movC.getMaxSpeed()));
  }

  /**
   * needs to be called, to reset the acceleration to 0
   * 
   * @param movC
   */
  public static void movementUpdateFinished(MovementComponent movC, PositionComponent posC) {
    movC.setAcceleration(movC.getAcceleration().mult(0));
  }
}
