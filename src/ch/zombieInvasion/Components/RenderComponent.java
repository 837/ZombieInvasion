package ch.zombieInvasion.Components;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import ch.zombieInvasion.util.ImageM;
import ch.zombieInvasion.util.Vector2D;

public class RenderComponent {
	private List<ImageM> images = new ArrayList<>();
	private List<Shape> shapes = new ArrayList<>();

	public RenderComponent(List<ImageM> images) {
		images.addAll(images);
		shapes.add(new Ellipse(0, 0, (images.get(0).getRadiusW() + images.get(0).getRadiusH()) / 2, (images.get(0).getRadiusW() + images.get(0)
				.getRadiusH()) / 2));
	}

	public RenderComponent(ImageM img) {
		images.add(img);

		shapes.add(new Ellipse(0, 0, (images.get(0).getRadiusW() + images.get(0).getRadiusH()) / 2, (images.get(0).getRadiusW() + images.get(0)
				.getRadiusH()) / 2));
	}

	public void renderAt(Vector2D location, Graphics g, int number) {
		ImageM img = images.get(number);
		Vector2D imgDrawPoint = new Vector2D(location.x - img.getRadiusW(), location.y - img.getRadiusH());
		g.drawImage(img.get(), (float) imgDrawPoint.x, (float) imgDrawPoint.y);
	}

	public void rotateAndRender(Vector2D location, Graphics g, int number, Vector2D target) {
		float targetAng = (float) location.getTargetAngle(target);

		g.rotate((float) location.x, (float) location.y, targetAng);
		renderAt(location, g, number);
		g.rotate((float) location.x, (float) location.y, -targetAng);

	}

	public void renderShapes(Vector2D location, Graphics g) {
		g.setColor(Color.green);
		shapes.stream().filter(e -> e != shapes.get(0)).forEach(e -> {
			e.setCenterX((float) location.x);
			e.setCenterY((float) location.y);
			g.draw(e);
		});
	}

	public void renderCollisionShape(Vector2D location, Graphics g) {
		shapes.get(0).setCenterX((float) location.x);
		shapes.get(0).setCenterY((float) location.y);
		Color.red.a = 0.4f;
		g.setColor(Color.red);
		g.fill(shapes.get(0));
	}

	public void addShapeToRender(Shape shape) {
		shapes.add(shape);
	}

	public Shape getShape(int index) {
		return shapes.get(index);
	}
}
