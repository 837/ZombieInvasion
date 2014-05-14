package ch.zombieInvasion.Eventhandling;

import java.util.ArrayList;

public class Event {
	private EventType event;
	private long delayMillis;
	private boolean persistent = false;
	private ArrayList<Object> additionalInfos;

	public Event(long delayMillis, EventType event, ArrayList<Object> additionalInfos) {
		this.event = event;
		this.delayMillis = delayMillis;
		this.additionalInfos = additionalInfos;
	}

	public EventType getEvent() {
		return event;
	}

	public long getDelayMillis() {
		return delayMillis;
	}

	public boolean isPersistent() {
		return persistent;
	}

	public ArrayList<Object> getAdditionalInfos() {
		return additionalInfos;
	}

	public void setDelayMillis(long delayMillis) {
		this.delayMillis = delayMillis;
	}

	public void setPersistent(boolean persistent) {
		this.persistent = persistent;
	}

}
