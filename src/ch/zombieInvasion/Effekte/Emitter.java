package ch.zombieInvasion.Effekte;

import java.io.IOException;

import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

import ch.zombieInvasion.util.LOGGER;

public class Emitter {

	private static ParticleSystem particleSystem;
	private static ConfigurableEmitter zombieBlood;
	private static ConfigurableEmitter gunSmoke;
	private static ConfigurableEmitter bloodSplurt;
	private static ConfigurableEmitter bloodPlayer;
	private static ConfigurableEmitter flameThrower;
	private static ConfigurableEmitter burningZombie;
	private static ConfigurableEmitter flameThrower2;

	public Emitter() {
		try {
			particleSystem = ParticleIO.loadConfiguredSystem("res/particles/emptySystem.xml");
			zombieBlood = ParticleIO.loadEmitter("res/particles/ZombieBlood.xml");
			gunSmoke = ParticleIO.loadEmitter("res/particles/GunSmoke.xml");
			bloodSplurt = ParticleIO.loadEmitter("res/particles/bloodSplurt.xml");
			bloodPlayer = ParticleIO.loadEmitter("res/particles/bloodPlayer.xml");
			flameThrower = ParticleIO.loadEmitter("res/particles/flammenwerfer.xml");
			flameThrower2 = ParticleIO.loadEmitter("res/particles/flammenwerfer2.xml");
			burningZombie = ParticleIO.loadEmitter("res/particles/burningZombie.xml");
			particleSystem.setRemoveCompletedEmitters(true);

		} catch (IOException e) {
			LOGGER.LOG("Error while creating emitter.");
		}
	}

	// zombie blut add
	public static ConfigurableEmitter ZombieBlood() {
		zombieBlood.setEnabled(false);
		ConfigurableEmitter blood = zombieBlood.duplicate();
		blood.setEnabled(false);
		particleSystem.addEmitter(blood);
		return blood;
	}

	public static ConfigurableEmitter FlameThrower() {
		flameThrower.setEnabled(false);
		ConfigurableEmitter flamethrower = flameThrower.duplicate();
		flamethrower.setEnabled(false);
		particleSystem.addEmitter(flamethrower);
		return flamethrower;
	}

	public static ConfigurableEmitter FlameThrower2() {
		flameThrower2.setEnabled(false);
		ConfigurableEmitter flamethrower = flameThrower2.duplicate();
		flamethrower.setEnabled(false);
		particleSystem.addEmitter(flamethrower);
		return flamethrower;
	}

	public static ConfigurableEmitter BurningZombie() {
		burningZombie.setEnabled(false);
		ConfigurableEmitter burningZombieEmitter = burningZombie.duplicate();
		burningZombieEmitter.setEnabled(false);
		particleSystem.addEmitter(burningZombieEmitter);
		return burningZombieEmitter;
	}

	// zombie blut shot
	public static ConfigurableEmitter bloodSplurt() {
		bloodSplurt.setEnabled(false);
		ConfigurableEmitter blood2 = bloodSplurt.duplicate();
		blood2.setEnabled(false);
		particleSystem.addEmitter(blood2);
		return blood2;
	}

	// player blut add
	public static ConfigurableEmitter bloodPlayer() {
		bloodPlayer.setEnabled(false);
		ConfigurableEmitter blood3 = bloodPlayer.duplicate();
		blood3.setEnabled(false);
		particleSystem.addEmitter(blood3);
		return blood3;
	}

	// emitter delete
	public void ZombieBloodDel(ConfigurableEmitter emitter) {
		particleSystem.removeEmitter(emitter);

	}

	// unused
	public void GunSmoke(float f, float g) {
		ConfigurableEmitter gunSmoke1 = gunSmoke.duplicate();
		gunSmoke1.setPosition(f, g);
		particleSystem.addEmitter(gunSmoke1);
	}

}
