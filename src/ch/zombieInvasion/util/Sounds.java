package ch.zombieInvasion.util;

import java.util.Random;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Sounds {
	public static Sound backGroundMusic1;
	public static Sound backGroundMusic2;
	public static Sound bissSound;
	public static Sound schussSound;
	public static Sound zombieSound;
	public static Sound reloadSound;
	public static Sound needAmmonSound;
	public static Sound heli1;
	public static Sound playerHeartBeat;
	public static Sound cakeMusic;
	public static Sound helpSound;
	public static Sound flameSound;

	private static Random rnd = new Random();

	static {
		try {
			// schuss sound
			schussSound = new Sound("res/sounds/schuss.wav");
			flameSound = new Sound("res/sounds/flame.ogg");

			// biss Sound
			bissSound = new Sound("res/Sounds/ZombieSounds/bite.wav");

			// heli sounds
			heli1 = new Sound("res/Sounds/heli.wav");

			// waffensounds
			reloadSound = new Sound("res/sounds/reload1.wav");
			needAmmonSound = new Sound("res/sounds/noAmmo.wav");

			// heartBeat
			playerHeartBeat = new Sound("res/sounds/heartbeat.wav");

			// backgroundmusic
			cakeMusic = new Sound("res/sounds/cakemusic.ogg");
			backGroundMusic1 = new Sound("res/sounds/z.ogg");
			backGroundMusic2 = new Sound("res/sounds/z2.ogg");

		} catch (final SlickException ex) {
			LOGGER.LOG("Failed to create Sounds instance in static block.");
			throw new RuntimeException("Failed to create Sounds instance in static block.", ex);
		}
	}

	public static Sound rndZombieSound() throws SlickException {
		switch (rnd.nextInt(11)) {
			case 0:
				zombieSound = new Sound("res/Sounds/ZombieSounds/ZombieSound (1).wav");
			break;
			case 1:
				zombieSound = new Sound("res/Sounds/ZombieSounds/ZombieSound (2).wav");
			break;
			case 2:
				zombieSound = new Sound("res/Sounds/ZombieSounds/ZombieSound (3).wav");
			break;
			case 3:
				zombieSound = new Sound("res/Sounds/ZombieSounds/ZombieSound (4).wav");
			break;
			case 4:
				zombieSound = new Sound("res/Sounds/ZombieSounds/ZombieSound (5).wav");
			break;
			case 5:
				zombieSound = new Sound("res/Sounds/ZombieSounds/ZombieSound (6).wav");
			break;
			case 6:
				zombieSound = new Sound("res/Sounds/ZombieSounds/ZombieSound (7).wav");
			break;
			case 7:
				zombieSound = new Sound("res/Sounds/ZombieSounds/ZombieSound (8).wav");
			break;
			case 8:
				zombieSound = new Sound("res/Sounds/ZombieSounds/ZombieSound (9).wav");
			break;
			case 9:
				zombieSound = new Sound("res/Sounds/ZombieSounds/ZombieSound (10).wav");
			break;
			case 10:
				zombieSound = new Sound("res/Sounds/ZombieSounds/ZombieSound (11).wav");
			break;
			default:
				zombieSound = new Sound("res/Sounds/ZombieSounds/ZombieSound (1).wav");
		}
		return zombieSound;
	}

	public static Sound rndHelpSound() throws SlickException {
		switch (rnd.nextInt(9)) {
			case 0:
				helpSound = new Sound("res/Sounds/help/h1.ogg");
			break;
			case 1:
				helpSound = new Sound("res/Sounds/help/h2.ogg");
			break;
			case 2:
				helpSound = new Sound("res/Sounds/help/h3.ogg");
			break;
			case 3:
				helpSound = new Sound("res/Sounds/help/h4.ogg");
			break;
			case 4:
				helpSound = new Sound("res/Sounds/help/h5.ogg");
			break;
			case 5:
				helpSound = new Sound("res/Sounds/help/h6.ogg");
			break;
			case 6:
				helpSound = new Sound("res/Sounds/help/h7.ogg");
			break;
			case 7:
				helpSound = new Sound("res/Sounds/help/h8.ogg");
			break;
			case 8:
				helpSound = new Sound("res/Sounds/help/h7.ogg");
			break;

			default:
				helpSound = new Sound("res/Sounds/help/h7.ogg");
			break;
		}
		return helpSound;
	}
}
