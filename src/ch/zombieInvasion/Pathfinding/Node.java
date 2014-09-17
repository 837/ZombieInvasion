package ch.zombieInvasion.Pathfinding;

import java.util.ArrayDeque;
import java.util.ArrayList;

import ch.zombieInvasion.Objekte.Entity;
import ch.zombieInvasion.util.Vector2D;

public class Node {
	private String name = "";
	private ArrayList<Node> neighbors;
	private int weight = 6;
	private int distance = -1;
	private int x = 0;
	private int y = 0;
	private ArrayDeque<Entity> entities = new ArrayDeque<>();

	public Node(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}

	public void setNeighbors(ArrayList<Node> neighbors) {
		this.neighbors = neighbors;
	}

	public ArrayList<Node> getNeighbors() {
		return neighbors;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void addEntity(Entity o) {
		entities.add(o);
	}

	public void removeEntity(Entity o) {
		entities.remove(o);
	}

	public ArrayDeque<Entity> getEntities() {
		return entities;
	}

	public void resetEntities() {
		entities.clear();
	}

	public Vector2D getVector(int tileSize) {
		return new Vector2D(x * tileSize + tileSize / 2, y * tileSize + tileSize / 2);
	}

	@Override
	public String toString() {
		return "Node name: " + name;
	}
}
