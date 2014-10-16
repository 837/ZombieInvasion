package ch.zombieInvasion.util;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageM {
  private Image image;
  private ImageTypes type;

  public ImageM() {

  }

  public ImageM(ImageTypes type) {
    try {
      image = new Image(type.toString());
      this.type = type;
    } catch (SlickException e) {
      LOGGER.LOG("Error while creating an ImageM: " + type);
    }
  }

  /**
   * 
   * @return this image data;
   */
  public Image getImg() {
    return image;
  }

  /**
   * 
   * @return image.getWidth() / 2;
   */
  public float getRadiusW() {
    return image.getWidth() / 2;
  }

  /**
   * 
   * @return image.getHeight() / 2;
   */
  public float getRadiusH() {
    return image.getHeight() / 2;
  }

  public ImageTypes getType() {
    return type;
  }

  public void setType(ImageTypes type) {
    this.type = type;
  }

}
