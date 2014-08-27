package ch.zombieInvasion.Achievements;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import ch.zombieInvasion.Game;

public class AchievementManager {

	private List<Achievement> incompleteAchievements = new ArrayList<>();
	private LinkedList<Achievement> pendingAchievements = new LinkedList<>();
	private Achievement activeAchievement = null;
	private final int visibleTime = 200;
	private int counter = 0;

	public AchievementManager(Game game) {
		/*
		 * HUD hud = gameField.getHud(); incompleteAchievements.add(new
		 * Achievement("First Blood!", () -> hud.getKills() == 1));
		 * incompleteAchievements.add(new Achievement("50 Kills", () ->
		 * hud.getKills() == 50)); incompleteAchievements.add(new
		 * Achievement("100 Kills!", () -> hud.getKills() == 100));
		 * incompleteAchievements.add(new Achievement("1000 Kills!", () ->
		 * hud.getKills() == 1000)); incompleteAchievements.add(new
		 * Achievement("10000 Kills!", () -> hud.getKills() == 10000));
		 * incompleteAchievements.add(new Achievement("First Boss Killed!", ()
		 * -> gameField.isFirstBossKilled())); incompleteAchievements.add(new
		 * Achievement("The cake is a lie!", () -> gameField.isCakeTouched()));
		 * incompleteAchievements.add(new Achievement("200 Life!", () ->
		 * hud.getLeben() >= 200)); // incompleteAchievements.add(new
		 * Achievement("10min fun!", () -> // time/1000 == 600));
		 * incompleteAchievements.add(new Achievement("Arms dealer!", () ->
		 * hud.getAktuelleWaffe().getWpTyp() != WeaponTyp.Pistol2));
		 * incompleteAchievements.add(new Achievement("First wave!", () ->
		 * gameField.getWave() == 1)); incompleteAchievements.add(new
		 * Achievement("10 waves!", () -> gameField.getWave() == 10));
		 * incompleteAchievements.add(new Achievement("20 waves!", () ->
		 * gameField.getWave() == 20)); incompleteAchievements.add(new
		 * Achievement("50 waves!", () -> gameField.getWave() == 50));
		 */
	}

	public void update() {

		if (activeAchievement != null) {
			counter++;
			if (counter == visibleTime) {
				activeAchievement = null;
			}
		}

		incompleteAchievements.stream().forEach(Achievement::check);
		pendingAchievements.addAll(incompleteAchievements.stream().filter(e -> e.isTriggered()).collect(Collectors.toList()));
		incompleteAchievements = incompleteAchievements.stream().filter(e -> !e.isTriggered()).collect(Collectors.toList());
		if (activeAchievement == null && pendingAchievements.size() > 0) {
			activeAchievement = pendingAchievements.poll();
			counter = 0;
		}
	}

	public void render() {
		if (activeAchievement != null) {
			activeAchievement.render();
		}
	}
}
