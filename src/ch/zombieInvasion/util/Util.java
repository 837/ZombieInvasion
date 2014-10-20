package ch.zombieInvasion.util;

import java.util.Random;


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

  public static final double map(double value, double istart, double istop, double ostart,
      double ostop) {
    return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
  }

  public static String uniqueID() {
    return java.util.UUID.randomUUID().toString();
  }
}
