package ch.zombieInvasion.util;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageM {
	Image img;

	public ImageM(String data) {
		try {
			img = new Image(data);
		} catch (SlickException e) {
			LOGGER.LOG("Error while creating an ImageM");
		}
	}

	/**
	 * 
	 * @return this img data;
	 */
	public Image get() {
		return img;
	}

	/**
	 * 
	 * @return img.getWidth() / 2;
	 */
	public float getRadiusW() {
		return img.getWidth() / 2;
	}

	/**
	 * 
	 * @return img.getHeight() / 2;
	 */
	public float getRadiusH() {
		return img.getHeight() / 2;
	}
}
