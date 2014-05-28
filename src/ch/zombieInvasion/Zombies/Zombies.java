package ch.zombieInvasion.Zombies;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import ch.zombieInvasion.EntityType;
import ch.zombieInvasion.util.Sounds;

public class Zombies {
	public static Zombie newZombie(EntityType zbTyp, Vector2f vPosition) {

		switch (zbTyp) {
			case Zombie_Normal:
				return new Zombie(null);
			case Zombie_Hard:
				return new Zombie(null);
			case Zombie_Teleport:
				return new Zombie(null);
			case Zombie_Sprint:
				return new Zombie(null);
			case Zombie_Boss:
				return new BossZombie(null);

			default:
			break;
		}

		return null;

	}

	// makesZombie
	public static ArrayList<Zombie> makeZombies(int anzahl, GameContainer container, int wave) throws SlickException {
		Random rnd = new Random();
		Vector2f spawn = new Vector2f(rnd.nextInt(container.getWidth()), 0);
		int waveCount = wave - 3;
		ArrayList<Zombie> localZombies = new ArrayList<>();

		if (waveCount > 12) {
			waveCount = 12;
		} else if (waveCount < 0) {
			waveCount = 0;
		}
		final int FwaveCount = waveCount;
		if (wave != 0 && wave % 5 == 0) {

			localZombies.add(Zombies.newZombie(EntityType.Zombie_Boss, spawn));

		}
		rnd.ints(anzahl).map(e -> e % (4 + FwaveCount)).forEach(e -> {
			// spawnt zombies nur am rand
				switch (e) {
				// Erste 4 sind normale zombies
				// Oben
					case 0:
						localZombies.add(Zombies.newZombie(EntityType.Zombie_Normal, new Vector2f(rnd.nextInt(container.getWidth()), 0)));
					break;
					// links
					case 1:
						localZombies.add(Zombies.newZombie(EntityType.Zombie_Normal, new Vector2f(rnd.nextInt(container.getWidth()), 0)));
					break;
					// unten
					case 2:
						localZombies.add(Zombies.newZombie(EntityType.Zombie_Normal, new Vector2f(rnd.nextInt(container.getWidth()), 0)));
					break;
					// rechts
					case 3:
						localZombies.add(Zombies.newZombie(EntityType.Zombie_Normal, new Vector2f(rnd.nextInt(container.getWidth()), 0)));
					break;
					// 4 mal sprinter zombies
					// Oben
					case 4:
						localZombies.add(Zombies.newZombie(EntityType.Zombie_Sprint, new Vector2f(rnd.nextInt(container.getWidth()), 0)));
					break;
					// links
					case 5:
						localZombies.add(Zombies.newZombie(EntityType.Zombie_Sprint, new Vector2f(rnd.nextInt(container.getWidth()), 0)));
					break;
					// unten
					case 6:
						localZombies.add(Zombies.newZombie(EntityType.Zombie_Sprint, new Vector2f(rnd.nextInt(container.getWidth()), 0)));
					break;
					// rechts
					case 7:
						localZombies.add(Zombies.newZombie(EntityType.Zombie_Sprint, new Vector2f(rnd.nextInt(container.getWidth()), 0)));
					break;
					// 4 mal hard zombies
					// Oben
					case 8:
						localZombies.add(Zombies.newZombie(EntityType.Zombie_Hard, new Vector2f(rnd.nextInt(container.getWidth()), 0)));
					break;
					// links
					case 9:
						localZombies.add(Zombies.newZombie(EntityType.Zombie_Hard, new Vector2f(rnd.nextInt(container.getWidth()), 0)));
					break;
					// unten
					case 10:
						localZombies.add(Zombies.newZombie(EntityType.Zombie_Hard, new Vector2f(rnd.nextInt(container.getWidth()), 0)));
					break;
					// rechts
					case 11:
						localZombies.add(Zombies.newZombie(EntityType.Zombie_Hard, new Vector2f(rnd.nextInt(container.getWidth()), 0)));
					break;
					// 4 mal TeleportZombies
					// Oben
					case 12:
						localZombies.add(Zombies.newZombie(EntityType.Zombie_Teleport, new Vector2f(rnd.nextInt(container.getWidth()), 0)));
					break;
					// links
					case 13:
						localZombies.add(Zombies.newZombie(EntityType.Zombie_Teleport, new Vector2f(rnd.nextInt(container.getWidth()), 0)));
					break;
					// unten
					case 14:
						localZombies.add(Zombies.newZombie(EntityType.Zombie_Teleport, new Vector2f(rnd.nextInt(container.getWidth()), 0)));
					break;
					// rechts
					case 15:
						localZombies.add(Zombies.newZombie(EntityType.Zombie_Teleport, new Vector2f(rnd.nextInt(container.getWidth()), 0)));
					break;

					default:
						localZombies.add(Zombies.newZombie(EntityType.Zombie_Normal, new Vector2f(rnd.nextInt(container.getWidth()), 0)));
				}
			});

		// ZombieSound
		Sounds.rndZombieSound().play(1f, 0.27f);

		return localZombies;
	}
}
