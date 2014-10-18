package ch.zombieInvasion;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import ch.zombieInvasion.Camera.Camera;
import ch.zombieInvasion.ComponentSystems.EntityMovement.EntityMovementSystem;
import ch.zombieInvasion.Pathfinding.Node;
import ch.zombieInvasion.Pathfinding.PathFindAlgorithm;
import ch.zombieInvasion.util.LOGGER;
import ch.zombieInvasion.util.Vector2D;

public class World {

  public EntityManager eManager;
  public TiledMap map;
  public ArrayList<Node> all_nodes = new ArrayList<>();

  boolean draw = false;

  public World() throws SlickException {
    eManager = new EntityManager();
    map = new TiledMap("res/map.tmx");
    calculateNodesAndGraph();
    updatePath(new Vector2D(Math.round(map.getWidth() / 2), map.getHeight()));

  }

  public void Update(Game game) {
    resetEntitiesInNodes();
    eManager.Update(game);
    if (game.getContainer().getInput().isKeyPressed(Input.KEY_T)) {
      draw = !draw;
    }

    Input i = game.getContainer().getInput();
    if (i.isKeyDown(Input.KEY_W)) {
      game.getCamera().move(new Vector2D(0, -10));
    }
    if (i.isKeyDown(Input.KEY_A)) {
      game.getCamera().move(new Vector2D(-10, 0));
    }
    if (i.isKeyDown(Input.KEY_S)) {
      game.getCamera().move(new Vector2D(0, 10));
    }
    if (i.isKeyDown(Input.KEY_D)) {
      game.getCamera().move(new Vector2D(10, 0));
    }

    EntityMovementSystem ems = new EntityMovementSystem(eManager.getEntities());
    ems.Update();
  }

  public void Render(Graphics g, double extrapolation, Camera camera) {

    renderMap(camera, extrapolation);
    if (draw) {
      all_nodes.stream().forEach(e -> {
        g.setColor(Color.black);
        g.drawString(e.getDistance() + "", (float) camera.getScreenPosX(e.getX() * 32) + 5, (float) camera.getScreenPosY(e.getY() * 32) + 5);
      });
    }
  }

  public void updatePath(Vector2D start) {
    resetDistance();
    for (int x = 0; x < map.getWidth(); x++) {
      for (int y = 0; y < map.getHeight(); y++) {
        int id = map.getTileId(x, y, 1);
        if (id == 55 || id == 3 || id == 4 || id == 57) {
          all_nodes.get(x * map.getWidth() + y).setWeight(999);
        }
        if (id == 68 || id == 84 || id == 83 || id == 67) {
          all_nodes.get(x * map.getWidth() + y).setWeight(12);
        }
        if (id == 5 || id == 6 || id == 53 || id == 54) {
          all_nodes.get(x * map.getWidth() + y).setWeight(1);
        }
        if (id == 60) {
          all_nodes.get(x * map.getWidth() + y).setWeight(10000);
        }
      }
    }
    try {
      PathFindAlgorithm.searchFrom(all_nodes.get((int) (start.x * map.getHeight() + start.y - 1)));
    } catch (Exception e2) {
      LOGGER.LOG("Error in updateNodes()");
    }
  }

  private void renderMap(Camera camera, double extrapolation) {
    // Render positions as integers
    int renderX = (int) camera.getCamPosX();
    int renderY = (int) camera.getCamPosY();

    int offsetX = -renderX % map.getTileWidth();
    int offsetY = -renderY % map.getTileHeight();

    // Get the last tile to be rendered
    int lastTileX = (int) (renderX + camera.getViewport_size_X()) / map.getTileWidth() + 1;
    int lastTileY = (int) (renderY + camera.getViewport_size_Y()) / map.getTileHeight() + 1;

    // Get the first tile to be rendered
    int firstTileX = (int) renderX / map.getTileWidth();
    int firstTileY = (int) renderY / map.getTileHeight();

    map.render(offsetX, offsetY, firstTileX, firstTileY, lastTileX, lastTileY, 0, true);
    map.render(offsetX, offsetY, firstTileX, firstTileY, lastTileX, lastTileY, 1, true);
  }

  private void calculateNodesAndGraph() {
    all_nodes.clear();
    for (int x = 0; x < map.getWidth(); x++) {
      for (int y = 0; y < map.getHeight(); y++) {
        Node n = new Node(x + "" + y, x, y);
        all_nodes.add(n);
      }
    }
    all_nodes.stream().forEach(e -> e.setNeighbors(setNeighbors(e)));
  }

  private ArrayList<Node> setNeighbors(Node e) {
    ArrayList<Node> result = new ArrayList<>();
    Node neighbor;
    // int[][] dirs = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 }, { -1, -1
    // }, { 1, 1 }, { -1, 1 }, { 1, -1 } }; // plus
    // diagonal
    int[][] dirs = { {1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    for (int i = 0; i < dirs.length; i++) {
      neighbor = new Node((e.getX() + dirs[i][0]) + "" + (e.getY() + dirs[i][1]), e.getX() + dirs[i][0], e.getY() + dirs[i][1]);
      for (int j = 0; j < all_nodes.size(); j++) {
        Node currentNode = all_nodes.get(j);
        if (currentNode.getX() == neighbor.getX() && currentNode.getY() == neighbor.getY()) {
          result.add(currentNode);
          break;
        }
      }
    }
    return result;
  }

  public void resetDistance() {
    all_nodes.stream().forEach(e -> e.setDistance(-1));
  }

  public void resetEntitiesInNodes() {
    all_nodes.stream().forEach(e -> e.resetEntities());
  }

}
