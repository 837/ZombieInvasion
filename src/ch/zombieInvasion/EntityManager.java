package ch.zombieInvasion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.newdawn.slick.Graphics;

import ch.zombieInvasion.Camera.Camera;
import ch.zombieInvasion.Eventhandling.EventDispatcher;
import ch.zombieInvasion.Eventhandling.EventType;
import ch.zombieInvasion.Items.Drop;
import ch.zombieInvasion.Objekte.Entity;
import ch.zombieInvasion.Objekte.Helicopter;
import ch.zombieInvasion.Objekte.Obstacle;
import ch.zombieInvasion.Objekte.Player;
import ch.zombieInvasion.Objekte.Tower;
import ch.zombieInvasion.Objekte.Zivilist;
import ch.zombieInvasion.Weapons.Weapon;
import ch.zombieInvasion.Zombies.Zombie;

public class EntityManager {
	private ArrayList<Drop> drops = new ArrayList<>();
	private ArrayList<Zombie> zombies = new ArrayList<>();
	private ArrayList<Weapon> weapons = new ArrayList<>();
	private ArrayList<Zivilist> zivilists = new ArrayList<>();
	private ArrayList<Tower> towers = new ArrayList<>();
	private ArrayList<Helicopter> helicopter = new ArrayList<>();
	private ArrayList<Player> player = new ArrayList<>();

	private ArrayList<Obstacle> obstacles = new ArrayList<>();

	public void Update(Game game) {
		update(drops, game);
		update(weapons, game);
		update(towers, game);
		update(zombies, game);
		update(zivilists, game);
		update(player, game);
		update(obstacles, game); // doesn't need updates
		update(helicopter, game);
	}

	public void Render(Graphics g, double extrapolation, Camera camera) {
		render(drops, g, extrapolation, camera);
		render(weapons, g, extrapolation, camera);
		render(towers, g, extrapolation, camera);
		render(zombies, g, extrapolation, camera);
		render(zivilists, g, extrapolation, camera);
		render(player, g, extrapolation, camera);
		render(obstacles, g, extrapolation, camera);
		render(helicopter, g, extrapolation, camera);
	}

	public void update(List<? extends Entity> l, Game game) {
		EventDispatcher.getEvents().stream().filter(e -> e.getEvent() == EventType.DELETE_ME).forEach(e -> {
			l.remove(e.getAdditionalInfo());
		});
		l.stream().forEach(e -> e.update(game));
	}

	public void render(List<? extends Entity> l, Graphics g, double extrapolation, Camera camera) {
		l.stream().forEach(e -> e.render(g, extrapolation, camera));
	}

	public void deleteAll() {
		drops = new ArrayList<>();
		zombies = new ArrayList<>();
		weapons = new ArrayList<>();
		zivilists = new ArrayList<>();
		towers = new ArrayList<>();
		helicopter = new ArrayList<>();
		obstacles = new ArrayList<>();
	}

	public void addDrop(Drop e) {
		drops.add(e);
	}

	public void addZombie(Zombie e) {
		zombies.add(e);
	}

	public void addWeapon(Weapon e) {
		weapons.add(e);
	}

	public void addZivilist(Zivilist e) {
		zivilists.add(e);
	}

	public void addTower(Tower e) {
		towers.add(e);
	}

	public void addHelicopter(Helicopter e) {
		helicopter.add(e);
	}

	public void addPlayer(Player e) {
		player.add(e);
	}

	public void addObstacle(Obstacle e) {
		obstacles.add(e);
	}

	public ArrayList<Drop> getDrops() {
		return drops;
	}

	public ArrayList<Zombie> getZombies() {
		return zombies;
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public ArrayList<Zivilist> getZivilists() {
		return zivilists;
	}

	public ArrayList<Tower> getTowers() {
		return towers;
	}

	public ArrayList<Helicopter> getHelicopter() {
		return helicopter;
	}

	public ArrayList<Player> getPlayer() {
		return player;
	}

	public ArrayList<Obstacle> getObstacle() {
		return obstacles;
	}
}
