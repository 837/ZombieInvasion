package ch.zombieInvasion.Components;

import java.util.Random;

import org.newdawn.slick.geom.Circle;
import ch.zombieInvasion.Objekte.Obstacle;
import ch.zombieInvasion.util.Util;
import ch.zombieInvasion.util.Vector2D;

public class MovingComponent {
	private Vector2D location = new Vector2D();
	private Vector2D acceleration = new Vector2D();
	private Vector2D velocity = new Vector2D();
	// a normalized vector heading in the direction the entity is heading^^
	private Vector2D heading = new Vector2D();
	// a vector perpendicular to the heading vector +
	private Vector2D side = new Vector2D();

	public Circle aheadCircle = new Circle(0, 0, 0);
	public Circle aheadCircle2 = new Circle(0, 0, 0);
	public Vector2D obstacle_center = new Vector2D();

	public Vector2D obstacle_center2 = new Vector2D();
	public Vector2D avoidForce = new Vector2D();
	public Vector2D avoidForce2 = new Vector2D();
	Obstacle mostThreatening = null;
	Obstacle mostThreatening2 = null;

	private double maxSpeed = 0;
	private double mass = 0;

	public MovingComponent(Vector2D location, double mass, double maxSpeed) {
		this.location = location;
		this.mass = mass;
		this.maxSpeed = maxSpeed;
	}

	public void update() {
		velocity = velocity.add(acceleration).limit(maxSpeed);
		location = location.add(velocity);
		acceleration = acceleration.mult(0);
		if (velocity.magSq() > 0.01) {
			heading = velocity.normalize();
			side = heading.perpCCW();

		}
	}

	public void headTo(Vector2D v) {
		heading = v.sub(location).normalize();
		side = heading.perpCCW();
	}

	public void applyForce(Vector2D force, double maxForce) {
		acceleration = acceleration.add(force.limit(maxForce).div(mass));
	}

	// steeringbehaviors
	public void seek(Vector2D targetLocation, double maxForce) {
		if (targetLocation != null) {
			applyForce(targetLocation.sub(location).normalize().mult(maxSpeed).sub(velocity), maxForce);
		}
	}

	public void flee(Vector2D targetLocation, double maxForce) {
		if (targetLocation != null) {
			applyForce(location.sub(targetLocation).normalize().mult(maxSpeed).sub(velocity), maxForce);
		}
	}

	public void arrive(Vector2D targetLocation, double maxForce) {
		Vector2D desired = targetLocation.sub(location);

		double d = desired.magSq();
		desired = desired.normalize();

		if (d < (20 * 20)) {
			// float m = (float) (maxSpeed / 4 * Math.log10(d + 1));// LOG
			float m = Util.map((float) d, 0, (20 * 20), 0, (float) maxSpeed);// LIN
			desired = desired.mult(m);
		} else {
			desired = desired.mult(maxSpeed);
		}

		applyForce(desired.sub(velocity), maxForce);
	}

	public void pursuit(Vector2D targetLocation, Vector2D targetVelocity, double targetSpeed, double maxForce) {
		Vector2D toTarget = targetLocation.sub(location);
		Vector2D targetHeading = targetVelocity.normalize();
		if ((targetHeading.dotP(heading)) > 0.9876 || (targetHeading.dotP(heading)) < -0.9876) {
			seek(targetLocation, maxForce);
		} else {
			double lookAheadTime = toTarget.mag() / (maxSpeed + targetSpeed);
			seek(targetLocation.add(targetVelocity.mult(lookAheadTime)), maxForce);
		}
	}

	public void evade(Vector2D targetLocation, Vector2D targetVelocity, double targetSpeed, double maxForce) {
		Vector2D toPursuer = targetLocation.sub(location);
		double lookAheadTime = toPursuer.mag() / (maxSpeed + targetSpeed);
		flee(targetLocation.add(targetVelocity.mult(lookAheadTime * 1.2)), maxForce);
	}

	private double wandertheta;

	public void wander(double wanderRadius, double wanderDistance, double change, double maxForce) {
		switch (new Random().nextInt(2)) {
			case 0:
				wandertheta += change;
			break;
			case 1:
				wandertheta -= change;
			break;
		}

		Vector2D circleLocation = velocity.copy();
		circleLocation = circleLocation.normalize();
		circleLocation = circleLocation.mult(wanderDistance);
		circleLocation = circleLocation.add(location);

		float h = velocity.heading2D();
		Vector2D circleOffSet = new Vector2D(wanderRadius * Math.cos(wandertheta + h), wanderRadius * Math.sin(wandertheta + h));

		Vector2D target = circleLocation.add(circleOffSet);
		seek(target, maxForce);
	}

	public Vector2D extrapolatedLocation(double extrapolation) {
		return location.add(velocity.mult(extrapolation));
	}

	public Vector2D getLocation() {
		return location;
	}

	public Vector2D getAcceleration() {
		return acceleration;
	}

	public Vector2D getVelocity() {
		return velocity;
	}

	public Vector2D getHeading() {
		return heading;
	}

	public Vector2D getSide() {
		return side;
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

	public double currentSpeed() {
		return velocity.mag();
	}
}
