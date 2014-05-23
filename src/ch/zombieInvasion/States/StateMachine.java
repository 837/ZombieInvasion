package ch.zombieInvasion.States;

import org.newdawn.slick.Graphics;

import ch.zombieInvasion.Game;
import ch.zombieInvasion.Camera.Camera;
import ch.zombieInvasion.Eventhandling.Event;
import ch.zombieInvasion.util.LOGGER;

public class StateMachine<entity_typ> {
	private BaseState<entity_typ> currentState;
	private BaseState<entity_typ> previousState;
	private BaseState<entity_typ> globalState;
	private entity_typ owner;

	public StateMachine(entity_typ owner) {
		this.owner = owner;
		this.currentState = null;
		this.previousState = null;
		this.globalState = null;
		LOGGER.LOG("Creating StateMachine: " + owner.getClass().getSimpleName());
	}

	public void SetCurrentState(BaseState<entity_typ> state) {
		this.currentState = state;
		LOGGER.LOG(owner.getClass().getSimpleName() + " SetCurrentState: " + state.getClass().getSimpleName());
	}

	public void SetPreviousState(BaseState<entity_typ> state) {
		this.previousState = state;
		LOGGER.LOG(owner.getClass().getSimpleName() + " SetPreviousState: " + state.getClass().getSimpleName());
	}

	public void SetGlobalState(BaseState<entity_typ> state) {
		this.globalState = state;
		LOGGER.LOG(owner.getClass().getSimpleName() + " SetGlobalState: " + state.getClass().getSimpleName());
	}

	public void ChangeState(BaseState<entity_typ> newState) {
		LOGGER.LOG(owner.getClass().getSimpleName() + " ChangeState: " + newState.getClass().getSimpleName());

		previousState = currentState;
		currentState.Exit(owner);
		currentState = newState;
		currentState.Enter(owner);
	}

	public void Update(Game game) {
		if (globalState != null) {
			globalState.Update(owner, game);
		}
		currentState.Update(owner, game);
	}

	public void Render(Graphics g, double extrapolation, Camera camera) {
		if (globalState != null) {
			globalState.Render(owner, g, extrapolation, camera);
		}
		currentState.Render(owner, g, extrapolation, camera);
	}

	public void RevertToPreviousState() {
		ChangeState(previousState);
	}

	
}
