package unsw.dungeon;

import java.util.concurrent.TimeUnit;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Bomb extends Entity implements Observer {
	
	private int timeLeft;
	private BooleanProperty hasBombActivated;
	
    public Bomb(int x, int y, Dungeon dungeon, int time) {
        super(x, y, dungeon);
        this.timeLeft = time;
        dungeon.addObserver(this);
        this.hasBombActivated = new SimpleBooleanProperty(false);
    }

    
    @Override
    public boolean isPickUp() {
    	return false;
    }
    
    @Override
    public boolean isBlocking() {
    	return false;
    }
    
    public void decrementTime() {
    	timeLeft--;
    	if (timeLeft == 0) explode();
    }
    
    public void explode() {
    	setHasBombActivated(true);

    	for (int i = getX() - 1; i <= getX() + 1; i++) {
    		for (int j = getY() - 1; j <= getY() + 1; j++) {
    			for (Entity e : getDungeon().getEntitiesOnCoordinates(i, j)) {
    				if (e instanceof Player) {
    					((Player)e).getsKilled();
    				} else if (e instanceof Enemy) {
    					((Enemy)e).getsKilled();
    				}
    			}
    		}
    	}
    	getDungeon().removeEntity(this);
    	getDungeon().addExplodedBomb(this);
    }


	@Override
	public void update(Subject s) {
		decrementTime();
	}
    
	public BooleanProperty getHasBombActivated() {
		return this.hasBombActivated;
	}
	
	public void setHasBombActivated(Boolean b) {
		this.hasBombActivated.set(b);
	}

}