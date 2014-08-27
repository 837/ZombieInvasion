package ch.zombieInvasion.Items;

import ch.zombieInvasion.util.LOGGER;
import ch.zombieInvasion.util.Vector2D;

public class Drops {
	public static Drop newDrop(DropTyp typ, Vector2D location, int quantity) {
		switch (typ) {
			case Money:
				return new Drop();
			case Life:
				return new Drop();
			case Ammo:
				return new Drop();

			default:
				LOGGER.LOG("This typ of Drop does not exist: " + typ.name());
			break;
		}
		return null;
	}
}
