package ch.zombieInvasion.ComponentSystems.EntityMovement;

import ch.zombieInvasion.Components.MovementComponent;
import ch.zombieInvasion.Components.PositionComponent;
import ch.zombieInvasion.Components.WanderMovementComponent;
import ch.zombieInvasion.util.Noise;
import ch.zombieInvasion.util.Util;
import ch.zombieInvasion.util.Vector2D;

// TODO non static
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
    setPosition(movC, posC);
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
    setPosition(movC, posC);
  }

  public static void perlinMove(PositionComponent posC, MovementComponent movC,
      WanderMovementComponent wamC) {
    double xN = Noise.noise(wamC.tx);
    double yN = Noise.noise(wamC.ty);

    double x = Util.map(xN, 0, 1, 0, Math.PI);
    double y = Util.map(yN, 0, 1, 0, Math.PI);

    System.out.println(xN + " " + yN);
    wamC.tx += 0.01;
    wamC.ty += 0.01;


    Vector2D steer = new Vector2D(x, y).sub(posC.getVelocity());
    steer = steer.limit(movC.getMaxForce());

    applyForce(steer, movC);
    addToVelocity(movC, posC);
    limitVelocity(movC, posC);
    setPosition(movC, posC);
  }

  public static void wander(PositionComponent posC, MovementComponent movC,
      WanderMovementComponent wamC) {
    double CIRCLE_DISTANCE = wamC.getCircleDistance();
    Vector2D circleCenter = posC.getVelocity();
    circleCenter = circleCenter.normalize();
    circleCenter = circleCenter.mult(CIRCLE_DISTANCE);

    //
    // Calculate the displacement force
    double CIRCLE_RADIUS = wamC.getCircleRadius();
    Vector2D displacement = new Vector2D(0, -1);
    displacement = displacement.mult(CIRCLE_RADIUS);

    //
    // Randomly change the vector direction
    // by making it change its current angle
    double displaceMentLength = displacement.mag();
    double x = Math.cos(wamC.getWanderAngle()) * displaceMentLength;
    double y = Math.sin(wamC.getWanderAngle()) * displaceMentLength;
    displacement = new Vector2D(x, y);

    //
    // Change wanderAngle just a bit, so it
    // won't have the same value in the
    // next game frame.
    double ANGLE_CHANGE = wamC.getAngleChange();
    wamC.setWanderAngle(wamC.getWanderAngle() + Math.random() * ANGLE_CHANGE - ANGLE_CHANGE * .5);

    //
    // Finally calculate and return the wander force
    Vector2D wanderForce = circleCenter.add(displacement);
    
    Vector2D steer = wanderForce.sub(posC.getVelocity());
    steer = steer.limit(movC.getMaxForce());
        
    applyForce(steer, movC);
    addToVelocity(movC, posC);
    posC.setVelocity(posC.getVelocity().limit(wamC.getWanderMovementSpeed()));
    setPosition(movC, posC);
  }

  /*
   * HELPER FUNCTIONS
   */
  public static void applyForce(Vector2D force, MovementComponent movC) {
    movC.setAcceleration(movC.getAcceleration().add(force.div(movC.getMass())));
  }

  private static void addToVelocity(MovementComponent movC, PositionComponent posC) {
    posC.setVelocity(posC.getVelocity().add(movC.getAcceleration()));

  }

  private static void limitVelocity(MovementComponent movC, PositionComponent posC) {
    posC.setVelocity(posC.getVelocity().limit(movC.getMaxSpeed()));

  }

  private static void setPosition(MovementComponent movC, PositionComponent posC) {
    // Vector2D pos = posC.getPosition().add(posC.getVelocity());
    // posC.setPosition(new Vector2D((int) pos.x, (int) pos.y));
    posC.setPosition(posC.getPosition().add(posC.getVelocity()));
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
