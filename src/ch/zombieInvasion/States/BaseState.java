package ch.zombieInvasion.States;

import org.newdawn.slick.Graphics;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.Eventhandling.Event;

public interface BaseState<entity_typ> {
	void Enter(entity_typ owner);

	void Update(entity_typ owner, Game game);

	void Render(entity_typ owner, Graphics g, double extrapolation);

	void Exit(entity_typ owner);

}
