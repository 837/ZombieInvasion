package ch.zombieInvasion.util;

import java.util.HashMap;

public class Images {
  private static HashMap<ImageTypes, ImageM> loadedImages = new HashMap<>();

  public static ImageM getImage(ImageTypes type) {
    if (loadedImages.containsKey(type)) {
      return loadedImages.get(type);
    }
    ImageM img = new ImageM(type);
    loadedImages.put(type, img);
    return img;
  }
}
