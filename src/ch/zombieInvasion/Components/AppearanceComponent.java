package ch.zombieInvasion.Components;

import ch.zombieInvasion.util.ImageTypes;

public class AppearanceComponent implements BaseComponent {
  private ComponentType type = ComponentType.Appearance;
  private ImageTypes imageType;
  private boolean enabled = true;

  public AppearanceComponent() {

  }

  public AppearanceComponent(ImageTypes imageType) {
    this.imageType = imageType;
  }


  public ImageTypes getImageType() {
    return imageType;
  }

  public void setImageType(ImageTypes imageType) {
    this.imageType = imageType;
  }

  public void setType(ComponentType type) {
    this.type = type;
  }

  @Override
  public ComponentType getType() {
    return type;
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
