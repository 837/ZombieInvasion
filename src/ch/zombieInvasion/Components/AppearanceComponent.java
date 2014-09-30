package ch.zombieInvasion.Components;

import ch.zombieInvasion.util.ImageTypes;

public class AppearanceComponent implements BaseComponent {
  private ImageTypes imageType;
  private boolean enabled = true;

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

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public void setEnabled(boolean b) {
    enabled = b;
  }



}
