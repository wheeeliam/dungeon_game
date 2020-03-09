package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class Exit extends Entity implements Subject {
	
	private ArrayList<Observer> observers;
	private int numTurnsOnExit;
	
	public Exit(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		observers = new ArrayList<>();
		setCollision(new PlayerExitCollision());
		dungeon.addExit(this);

		numTurnsOnExit = 0;
	}
	
	@Override
    public boolean isPickUp() {
    	return false;
    }
    
	@Override
    public boolean isBlocking() {
    	return false;
    }
	
//	public boolean arriveAtExit() {
//		// TODO: Check goals before signifying completed
//		List<Entity> exitEntities = getDungeon().getEntitiesOnCoordinates(this.getX(), this.getY());
//    	for (Entity e : exitEntities) {
//    		if (e instanceof Player) {
//    			return true;
//    		}
//    	}
//    	return false;
//	}

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
		if (getDungeon().getPlayer().getX() == this.getX() && getDungeon().getPlayer().getY() == this.getY()) {
			numTurnsOnExit++;
		} else {
			numTurnsOnExit = 0;
		}
		for (Observer observer: observers) {
			observer.update(this);
		}
		
	}
	
	public int getNumTurns() {
		return numTurnsOnExit;
	}
	
} 