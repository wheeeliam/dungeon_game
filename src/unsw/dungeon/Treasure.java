package unsw.dungeon;

import java.util.ArrayList;

public class Treasure extends PickUpEntity implements Subject {
	
	private ArrayList<Observer> observers;

    public Treasure(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
//        setPlayerPickUp(new PlayerPickUpTreasure());
		dungeon.addTreasure(this);

        observers = new ArrayList<>();

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
		for (Observer o : observers) {
			o.update(this);
		}
	}
	
	@Override
	public boolean pickUp(Player player) {
		player.addTreasure(this);
    	notifyObservers();
    	return true;
	}
    
    

}
