package ch.zombieInvasion.Objekte;

import java.util.ArrayList;
import java.util.HashSet;

import ch.zombieInvasion.Components.BaseComponent;
import ch.zombieInvasion.Components.ComponentType;
import ch.zombieInvasion.util.Util;

public class Entity {
  private String id;

  private HashSet<BaseComponent> components = new HashSet<>();

  public Entity() {
    setId(Util.uniqueID());
  }

  public HashSet<BaseComponent> getComponents() {
    return components;
  }

  public void addComponent(BaseComponent comp) {
    components.add(comp);
  }

  public void removeComponent(ComponentType type) {
    components.removeIf(e -> e.getType() == type);
  }

  public boolean hasComponent(ComponentType type) {
    return components.parallelStream().anyMatch(e -> e.getType() == type);
  }

  public boolean hasComponents(ArrayList<ComponentType> types) {
    ArrayList<ComponentType> templist = new ArrayList<>();
    components.forEach(e -> templist.add(e.getType()));
    return templist.containsAll(types);
  }

  public BaseComponent getComponent(ComponentType type) {
    return components.parallelStream().filter(e -> e.getType() == type).findAny().get();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setComponents(HashSet<BaseComponent> components) {
    this.components = components;
  }
}
