package ch.zombieInvasion.Objekte;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import com.google.gson.Gson;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.Camera.Camera;
import ch.zombieInvasion.Components.BaseComponent;
import ch.zombieInvasion.util.Util;

public class Entity {
  private String id;
  private ArrayList<BaseComponent> components = new ArrayList<>();

  public Entity() {
    id = Util.uniqueID();
  }

  public ArrayList<BaseComponent> getComponents() {
    return components;
  }

  public void addComponent(BaseComponent component) {
    components.add(component);
  }

  public BaseComponent getComponent(String componentName) {
    return components.stream().filter(e -> e.getName().equals(componentName)).findFirst().get();
  }

  public void removeComponent(String componentName) {
    ArrayList<BaseComponent> tempList = new ArrayList<>();
    components.stream().filter(e -> e.getName().equals(componentName)).forEach(e2 -> tempList.add(e2));
    components.removeAll(tempList);
  }

  public boolean containsComponents(ArrayList<String> names) {
    ArrayList<String> templist = new ArrayList<>();
    components.forEach(e -> templist.add(e.getName()));
    return templist.containsAll(names);
  }

  public String toJSON() {
    return new Gson().toJson(this);
  }
}
