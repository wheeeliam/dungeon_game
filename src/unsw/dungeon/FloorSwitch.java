package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class FloorSwitch extends Entity implements Subject {

	private boolean triggered;
	private ArrayList<Observer> observers;

	public FloorSwitch(int x, int y, Dungeon dungeon) {
		super(x,y, dungeon);
		this.triggered = false;
		observers = new ArrayList<>();
		dungeon.addFloorSwitch(this);

	}
	
	@Override
    public boolean isPickUp() {
    	return false;
    }
    
	@Override
    public boolean isBlocking() {
    	return false;
    }
	
	public void updateTriggerStatus() { 
		if (hasBoulder() && !isTriggered()) {
			this.setTriggered(true);
			notifyObservers();
		} else if (isTriggered() && !hasBoulder()) {
			this.setTriggered(false);
			notifyObservers();
		}
	}

	public boolean isTriggered() {
		return triggered;
	}
	
	private boolean hasBoulder() {
		List<Entity> entities = getDungeon().getEntitiesOnCoordinates(this.getX(), this.getY());
		for (Entity entity : entities) {
			if (entity instanceof Boulder) return true;
		}
		return false;
	}

	public void setTriggered(boolean triggered) {
		this.triggered = triggered;
	}

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
		
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
		
	}

	@Override
	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(this);
		}
	}
}
