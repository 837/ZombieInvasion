package ch.zombieInvasion.Pathfinding;

import java.util.ArrayDeque;

public class PathFindAlgorithm {

	static public void searchFrom(Node start) {
		Node current;
		ArrayDeque<Node> open = new ArrayDeque<>();
		ArrayDeque<Node> openObj = new ArrayDeque<>();

		open.push(start);
		start.setDistance(0);
		while (!open.isEmpty()) {
			current = open.poll();

			for (Node e : current.getNeighbors()) {
				if (e.getDistance() == -1) {
					if (e.getWeight() != 999) {
						open.add(e);
						e.setDistance(current.getDistance() + e.getWeight());
					} else {
						openObj.add(e);
						e.setDistance(current.getDistance() + e.getWeight());
					}
				} else {
					if (e.getWeight() != 999) {
						if (current.getDistance() + e.getWeight() < e.getDistance()) {
							open.add(e);
							e.setDistance(current.getDistance() + e.getWeight());
						}
					}
				}
			}
		}
		while (!openObj.isEmpty()) {
			current = openObj.poll();
			for (Node e : current.getNeighbors()) {
				if (e.getDistance() == -1) {
					openObj.add(e);
					e.setDistance(current.getDistance() + e.getWeight());
				}
			}
		}
	}
}
