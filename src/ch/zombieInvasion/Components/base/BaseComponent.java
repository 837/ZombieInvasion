package ch.zombieInvasion.Components.base;

public abstract class BaseComponent implements IBaseComponent {
  private boolean enabled = true;

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean b) {
    enabled = b;
  };
}
