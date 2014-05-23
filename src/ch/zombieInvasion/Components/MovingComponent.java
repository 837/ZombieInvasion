package ch.zombieInvasion.Components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.Point;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

import ch.zombieInvasion.Camera.Camera;
import ch.zombieInvasion.Objekte.Entity;
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

	public Vector2D ahead = new Vector2D();
	public Vector2D ahead2 = new Vector2D();
	public Circle aheadCircle = new Circle(0, 0, 0);
	public Circle aheadCircle2 = new Circle(0, 0, 0);
	public Vector2D obstacle_center = new Vector2D();

	public Vector2D obstacle_center2 = new Vector2D();
	public Vector2D avoidForce = new Vector2D();
	public Vector2D avoidForce2 = new Vector2D();
	final int MAX_SEE_AHEAD = 10;
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

	public void applyForce(Vector2D force, double maxForce) {
		acceleration = acceleration.add(force.limit(maxForce).div(mass));
	}

	// steeringbehaviors
	public void seek(Vector2D targetLocation, double maxForce) {
		applyForce(targetLocation.sub(location).normalize().mult(maxSpeed).sub(velocity), maxForce);
	}

	public void flee(Vector2D targetLocation, double maxForce) {
		applyForce(location.sub(targetLocation).normalize().mult(maxSpeed).sub(velocity), maxForce);
	}

	public void arrive(Vector2D targetLocation, double maxForce) {
		Vector2D desired = targetLocation.sub(location);

		double d = desired.magSq();
		desired = desired.normalize();

		if (d < (100 * 100)) {
			// float m = (float) (maxSpeed / 4 * Math.log10(d + 1));// LOG
			float m = Util.map((float) d, 0, (100 * 100), 0, (float) maxSpeed);// LIN
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

	public void offsetSeek(Vector2D targetLocation, float targetRadius, double maxForce) {
		// TO-DO
	}

	public boolean obstacleAvoidanceByPoint(List<Obstacle> list, float radius, double maxForce) {
		double dynamic_length = velocity.mag();
		ahead = location.add(velocity.normalize().mult((MAX_SEE_AHEAD)).mult(dynamic_length));
		ahead2 = location.add(velocity.normalize().mult((MAX_SEE_AHEAD) * (new Random().nextDouble() / 2)).mult(dynamic_length));

		mostThreatening = null;

		list.forEach(e -> {
			Vector2D obstacle_center = new Vector2D(e.get().getCenterX(), e.get().getCenterY());
			if (location.dist(obstacle_center) < e.get().getBoundingCircleRadius() + radius - 1
					|| ahead.dist(obstacle_center) < e.get().getBoundingCircleRadius() + radius - 1
					|| ahead2.dist(obstacle_center) < e.get().getBoundingCircleRadius() + radius - 1) {
				if (mostThreatening == null || location.dist(obstacle_center) < location.dist(mostThreatening.get().getCenter())) {
					mostThreatening = e;
				}
			}
		});

		if (mostThreatening != null) {
			obstacle_center = new Vector2D(mostThreatening.get().getCenterX(), mostThreatening.get().getCenterY());

			avoidForce = location.sub(obstacle_center).normalize().mult(maxSpeed);
			applyForce(avoidForce, maxForce);
			return true;
		} else {
			avoidForce = avoidForce.mult(0);
		}
		return false;
	}

	public boolean obstacleCollisionByCircle(List<Obstacle> list, float radius, double maxForce) {
		// double dynamic_length = velocity.mag();
		// aheadCircle = new Circle((float)
		// location.add(velocity.normalize().mult((MAX_SEE_AHEAD)).mult(dynamic_length)).x,
		// (float)
		// location.add(velocity.normalize().mult((MAX_SEE_AHEAD)).mult(dynamic_length)).y,
		// radius, 8);

		aheadCircle2 = new Circle((float) location.x, (float) location.y, radius, 9);

		mostThreatening = null;
		mostThreatening2 = null;

		list.forEach(e -> {
			Vector2D obstacle_center = new Vector2D(e.get().getCenterX(), e.get().getCenterY());

			if (aheadCircle.intersects(e.get()) || aheadCircle2.intersects(e.get())) {
				if (mostThreatening == null || location.dist(obstacle_center) < location.dist(mostThreatening.get().getCenter())) {
					if (mostThreatening != null && location.dist(obstacle_center) <= location.dist(mostThreatening.get().getCenter())) {
						mostThreatening2 = e;
					}
					mostThreatening = e;
				}
			}
		});

		if (mostThreatening != null) {
			obstacle_center = new Vector2D(mostThreatening.get().getCenterX(), mostThreatening.get().getCenterY());

			double p = location.dist(obstacle_center) - (radius + mostThreatening.get().getBoundingCircleRadius());

			avoidForce = location.sub(obstacle_center).normalize().mult((p * -1));

			// avoidForce =
			// location.sub(obstacle_center).normalize().mult(maxSpeed);
			// avoidForce =
			// tangentVectorToCircle(mostThreatening.get()).mult(maxSpeed);
			// arrive(location.add(avoidForce.mult(5)), maxForce);
			// applyForce(avoidForce, maxForce);
			// return true;
			// arrive(avoidForce.add(avoidForce2), maxForce);
			applyForce(avoidForce, maxForce);
		}
		if (mostThreatening2 != null) {
			obstacle_center2 = new Vector2D(mostThreatening2.get().getCenterX(), mostThreatening2.get().getCenterY());

			double p = location.dist(obstacle_center2) - (radius + mostThreatening2.get().getBoundingCircleRadius());

			avoidForce2 = location.sub(obstacle_center2).normalize().mult((p * -1));

			// avoidForce =
			// location.sub(obstacle_center).normalize().mult(maxSpeed);
			// avoidForce =
			// tangentVectorToCircle(mostThreatening.get()).mult(maxSpeed);
			// arrive(location.add(avoidForce.mult(5)), maxForce);
			// applyForce(avoidForce, maxForce);

			// arrive(avoidForce.add(avoidForce2), maxForce);
			applyForce(avoidForce2, maxForce);
			// return true;
		} else {
			avoidForce = avoidForce.mult(0);
			avoidForce2 = avoidForce.mult(0);
		}
		return false;
	}

	
	private Vector2D tangentVectorToCircle(Shape s) {
		double circleX = s.getCenterX();
		double circleY = s.getCenterY();

		Vector2D sVec = new Vector2D(circleX, circleY);
		Vector2D playerToObstacle = sVec.sub(location);

		double d = location.dist(sVec);

		if (d <= 0.0) {
			d = 0.1;
		}

		double radius = s.getBoundingCircleRadius();
		double rd = (radius / d);

		if (rd >= 1) {
			rd = 0.1;
		}
		double angle = Math.acos(rd);

		return playerToObstacle.rotate(angle).normalize();
	}

	int count = 0;

	public void separate(ArrayList<Vector2D> entitiesLocation, double desiredSeperation, double maxForce) {
		Vector2D sum = new Vector2D();
		count = 0;
		entitiesLocation.forEach(e -> {
			double d = location.dist(e);
			if ((d > 0) && (d < desiredSeperation)) {
				sum.add(location.sub(e).normalize().div(d));
				count++;
			}
		});
		if (count > 0) {
			applyForce(sum.div(count).normalize().mult(maxSpeed), maxForce);
		}
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
