package ch.zombieInvasion.util;

public enum ImageTypes {
  background("res/background.jpg"), player("res/player.png"), normalZombie("res/NormalZombie.png"), normalZombieDead(
      "res/NormalZombieDead.png"), sprinterZombie("res/SprinterZombie.png"), sprinterZombieDead(
      "res/SprinterZombieDead.png"), hardZombie("res/HardZombie.png"), hardZombieDead(
      "res/HardZombieDead.png"), teleportZombie("res/TeleportZombie.png"), teleportZombieDead(
      "res/TeleportZombieDead.png"), bossZombie("res/BossZombie.png"), bossZombieDead(
      "res/BossZombieDead.png"), normalBullet("res/Weapons/normalBullet.png");

  private final String stringValue;

  private ImageTypes(final String s) {
    stringValue = s;
  }

  public String toString() {
    return stringValue;
  }

}
