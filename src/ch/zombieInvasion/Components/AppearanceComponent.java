package ch.zombieInvasion.Components;

import ch.zombieInvasion.Components.base.BaseComponent;
import ch.zombieInvasion.Components.base.ComponentType;
import ch.zombieInvasion.util.ImageTypes;

public class AppearanceComponent extends BaseComponent {
  private ImageTypes imageType;


  public AppearanceComponent(ImageTypes imageType) {
    this.imageType = imageType;
  }

  public ImageTypes getImageType() {
    return imageType;
  }

  public void setImageType(ImageTypes imageType) {
    this.imageType = imageType;
  }

  @Override
  public ComponentType getType() {
    return ComponentType.Appearance;
  }

}
