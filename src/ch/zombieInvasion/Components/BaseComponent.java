package ch.zombieInvasion.Components;

public interface BaseComponent {
	public ComponentType getType();

	public boolean isEnabled();
	public void setEnabled(boolean b);
}
