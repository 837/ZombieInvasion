package ch.zombieInvasion.Components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.zombieInvasion.Items.Drop;
import ch.zombieInvasion.util.Vector2D;

public class DropComponent {
	public List<Drop> releaseDrops(int maxDrops, Vector2D location) {
		Random rnd = new Random();
		List<Drop> drops = new ArrayList<>();
		rnd.ints(maxDrops, 0, 2).forEach(e -> {
			switch (e) {
				case 0:
					drops.add(new Drop());
				break;

				case 1:
					drops.add(new Drop());
				break;

				case 2:
					drops.add(new Drop());
				break;
			}
		});
		return drops;
	}
}
