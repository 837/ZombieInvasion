package ch.zombieInvasion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.newdawn.slick.Graphics;

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
	private List<Drop> drops = new ArrayList<>();
	private List<Zombie> zombies = new ArrayList<>();
	private List<Weapon> weapons = new ArrayList<>();
	private List<Zivilist> zivilists = new ArrayList<>();
	private List<Tower> towers = new ArrayList<>();
	private List<Helicopter> helos = new ArrayList<>();
	private List<Player> player = new ArrayList<>();

	private List<Obstacle> obstacles = new ArrayList<>();

	public void Update(Game game) {
		update(drops, game);
		update(weapons, game);
		update(towers, game);
		update(zombies, game);
		update(zivilists, game);
		update(player, game);
		update(obstacles, game); // doesn't need updates
		update(helos, game);
	}

	public void Render(Graphics g, double extrapolation) {
		render(drops, g, extrapolation);
		render(weapons, g, extrapolation);
		render(towers, g, extrapolation);
		render(zombies, g, extrapolation);
		render(zivilists, g, extrapolation);
		render(player, g, extrapolation);
		render(obstacles, g, extrapolation);
		render(helos, g, extrapolation);
	}

	public void update(List<? extends Entity> l, Game game) {
		EventDispatcher.getEvents().stream().filter(e -> e.getEvent() == EventType.DELETE_ME).forEach(e -> {
			l.remove(e.getAdditionalInfos().get(0));
		});
		// l.removeAll(l.stream().filter(e ->
		// Entity.disappeard).collect(Collectors.toCollection(ArrayList::new)));
		l.stream().forEach(e -> e.update(game));
	}

	public void render(List<? extends Entity> l, Graphics g, double extrapolation) {
		l.stream().forEach(e -> e.render(g, extrapolation));
	}

	public void deleteAll() {
		drops = new ArrayList<>();
		zombies = new ArrayList<>();
		weapons = new ArrayList<>();
		zivilists = new ArrayList<>();
		towers = new ArrayList<>();
		helos = new ArrayList<>();
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
		helos.add(e);
	}

	public void addPlayer(Player e) {
		player.add(e);
	}

	public void addObstacle(Obstacle e) {
		obstacles.add(e);
	}

	public List<Drop> getDrops() {
		return drops;
	}

	public List<Zombie> getZombies() {
		return zombies;
	}

	public List<Weapon> getWeapons() {
		return weapons;
	}

	public List<Zivilist> getZivilists() {
		return zivilists;
	}

	public List<Tower> getTowers() {
		return towers;
	}

	public List<Helicopter> getHelos() {
		return helos;
	}

	public List<Player> getPlayer() {
		return player;
	}

	public List<Obstacle> getObstacle() {
		return obstacles;
	}
}
