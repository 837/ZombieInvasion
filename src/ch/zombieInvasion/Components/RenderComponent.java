package ch.zombieInvasion.Components;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import ch.zombieInvasion.Camera.Camera;
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

	public void renderAt(Vector2D location, Graphics g, int number, Camera camera) {
		if (isVisible(location, camera)) {
			ImageM img = images.get(number);
			Vector2D imgDrawPoint = new Vector2D(location.x - img.getRadiusW(), location.y - img.getRadiusH());
			g.drawImage(img.get(), (float) (imgDrawPoint.x - camera.getCamX()), (float) (imgDrawPoint.y - camera.getCamY()));
		}
	}

	public void rotateAndRender(Vector2D location, Graphics g, int number, Vector2D target, Camera camera) {
		float targetAng = (float) location.sub(new Vector2D(camera.getCamX(), camera.getCamY())).getTargetAngle(
				target.sub(new Vector2D(camera.getCamX(), camera.getCamY())));

		g.rotate((float) (location.x - camera.getCamX()), (float) (location.y - camera.getCamY()), targetAng);
		renderAt(location, g, number, camera);
		g.rotate((float) (location.x - camera.getCamX()), (float) (location.y - camera.getCamY()), -targetAng);

	}

	public void renderShapes(Vector2D location, Graphics g, Camera camera) {
		if (isVisible(location, camera)) {
			g.setColor(Color.blue);
			shapes.stream().filter(e -> e != shapes.get(0)).forEach(e -> {
				e.setCenterX((float) (location.x - camera.getCamX()));
				e.setCenterY((float) (location.y - camera.getCamY()));
				g.draw(e);
				e.setCenterX((float) (location.x + camera.getCamX()));
				e.setCenterY((float) (location.y + camera.getCamY()));
			});
		}
	}

	public void renderCollisionShape(Vector2D location, Graphics g, Camera camera) {
		if (isVisible(location, camera)) {
			shapes.get(0).setCenterX((float) (location.x - camera.getCamX()));
			shapes.get(0).setCenterY((float) (location.y - camera.getCamY()));
			Color.red.a = 0.4f;
			g.setColor(Color.red);
			g.fill(shapes.get(0));
			shapes.get(0).setCenterX((float) (location.x + camera.getCamX()));
			shapes.get(0).setCenterY((float) (location.y + camera.getCamY()));
		}
	}

	private boolean isVisible(Vector2D location, Camera camera) {
		return (location.x + 15 >= camera.getCamX() && location.x <= (camera.getCamX() + camera.getViewport_size_X())
				&& location.y + 15 >= camera.getCamY() && location.y <= (camera.getCamY() + camera.getViewport_size_Y()));
	}

	public void addShapeToRender(Shape shape) {
		shapes.add(shape);
	}

	public Shape getShape(int index) {
		return shapes.get(index);
	}
}
