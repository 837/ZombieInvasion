package ch.zombieInvasion.util;

import java.util.Random;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;


public class Util {
	static Random rnd = new Random();

	/**
	 * Returns a float between -1 and 1
	 * 
	 * @return float
	 */
	public static float smallRndFloat() {
		return rnd.nextFloat() - rnd.nextFloat();
	}

	public static final float map(float value, float istart, float istop, float ostart, float ostop) {
		return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
	}

  public static String uniqueID() {
    return java.util.UUID.randomUUID().toString();
  }
}
