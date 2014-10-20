package ch.zombieInvasion.Achievements;

import java.util.function.BooleanSupplier;


public class Achievement {
	private String text;
	private boolean triggered;
	private BooleanSupplier condition;
	
	public Achievement(String text, BooleanSupplier condition) {
		this.text = text;
		this.triggered = false;
		this.condition = condition;
	}
	
	public void check() {
		triggered = condition.getAsBoolean();
	}
	public boolean isTriggered(){
		return triggered;
	}
	public void render() {
		System.out.println(text);
		
	}
	
}