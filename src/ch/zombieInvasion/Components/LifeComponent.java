package ch.zombieInvasion.Components;

public class LifeComponent {
	private boolean dead = false;
	private int hp;

	public LifeComponent(int life) {
		hp = life;
	}

	public void addDamage(int dmg) {
		hp -= dmg;
		if (hp <= 0) {
			dead = true;
		}
	}

	public void addLife(int life) {
		hp += life;
	}

	public boolean isDead() {
		return dead;
	}
}
