package ch.zombieInvasion.Pathfinding;

import java.util.ArrayDeque;
import java.util.ArrayList;

import ch.zombieInvasion.Zombies.Zombie;
import ch.zombieInvasion.util.Vector2D;

public class Node {
	private String name = "";
	private ArrayList<Node> neighbors;
	private int weight = 6;
	private int distance = -1;
	private int x = 0;
	private int y = 0;
	private ArrayDeque<Zombie> zombies = new ArrayDeque<>();

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

	public void addZombie(Zombie o) {
		zombies.add(o);
	}

	public void removeEntity(Zombie o) {
		zombies.remove(o);
	}

	public ArrayDeque<Zombie> getZombies() {
		return zombies;
	}

	public void resetEntities() {
		zombies.clear();
	}

	public Vector2D getVector(int tileSize) {
		return new Vector2D(x * tileSize + tileSize / 2, y * tileSize + tileSize / 2);
	}

	@Override
	public String toString() {
		return "Node name: " + name;
	}
}
