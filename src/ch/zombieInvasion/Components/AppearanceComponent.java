package ch.zombieInvasion.Components;

import ch.zombieInvasion.util.ImageM;

import com.google.gson.Gson;

public class AppearanceComponent implements BaseComponent {
  private String componentName = "AppearanceComponent";
  private ImageM img;

  public AppearanceComponent(ImageM img) {
    this.img = img;
  }

  public ImageM getImage() {
    return img;
  }

  @Override
  public String getName() {
    return componentName;
  }

  @Override
  public String toJSON() {
    return new Gson().toJson(this);
  }
}
