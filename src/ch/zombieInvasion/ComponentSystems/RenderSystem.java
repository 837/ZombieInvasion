package ch.zombieInvasion.ComponentSystems;

import java.util.ArrayList;

import ch.zombieInvasion.Components.RenderComponent;

public class RenderSystem extends BaseSystem {
  private ArrayList<RenderComponent> components = new ArrayList<>();

  @Override
  public void Update() {
    components.forEach(e -> e.Update());
  }

  @Override
  public void Reset() {
    components.clear();
  }

  public void registerComponent(RenderComponent rComp) {
    components.add(rComp);
  }

  public void removeComponent(RenderComponent rComp) {
    components.remove(rComp);
  }
}
