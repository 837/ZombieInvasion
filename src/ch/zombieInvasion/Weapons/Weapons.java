package ch.zombieInvasion.Weapons;

import org.newdawn.slick.geom.Vector2f;

import ch.zombieInvasion.util.Images;

public class Weapons {
	public static Weapon newWeapon(WeaponTyp wpTyp, Vector2f vPosition) {
		switch (wpTyp) {
			case AUG:
				return new Weapon();
			case AutoSniper:
				return new Weapon();
			case AWP:
				return new Weapon();
			case DEagle:
				return new Weapon();
			case DualPistol:
				return new Weapon();
			case Famas:
				return new Weapon();
			case Flamethrower:
				return new Weapon();
			case M16:
				return new Weapon();
			case MG:
				return new Weapon();
			case MP5:
				return new Weapon();
			case Pistol1:
				return new Weapon();
			case Pistol2:
				return new Weapon();
			case Pump1:
				return new Weapon();
			case Pump2:
				return new Weapon();
			case Sniper:
				return new Weapon();
			case UMP:
				return new Weapon();
			default:
			break;
		}
		return null;
	}
}
