package ch.zombieInvasion;

import java.util.ArrayList;
import java.util.List;

import ch.zombieInvasion.Eventhandling.EventDispatcher;
import ch.zombieInvasion.Eventhandling.EventType;
import ch.zombieInvasion.Objekte.Entity;

public class EntityManager {
  private final String name = "EntityManager";

  private ArrayList<Entity> entities = new ArrayList<>();

  public void Update(Game game) {
    update(entities, game);
  }

  public void update(List<? extends Entity> l, Game game) {
    EventDispatcher.getEvents().stream().filter(e -> e.getEvent() == EventType.DELETE_ME)
        .forEach(e -> {
          l.remove(e.getAdditionalInfo());
          EventDispatcher.removePersistentEvent(e);
        });
  }

  public void deleteAll() {
    entities.clear();
  }

  public ArrayList<Entity> getEntities() {
    return entities;
  }

  public void addEntity(Entity o) {
    entities.add(o);
  }

}
