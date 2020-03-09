package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Door extends Entity {
	
	private int id;
	private boolean open;
	private BooleanProperty closedImage;
	
    public Door(int x, int y, int id, Dungeon dungeon) {
        super(x, y, dungeon);
        this.id = id;
        this.open = false;
        this.closedImage = new SimpleBooleanProperty(true);
        setInteraction(new PlayerOpenDoorInteraction());
    }
    
    public boolean isCorrespondingKey(Key key) {
    	if (key.getID() == this.id) return true;
    	return false;
    }
    
    @Override
    public boolean isPickUp() {
    	return false;
    }
    
    @Override
    public boolean isBlocking() {
    	return !this.open;
    }
    
    public void open() {
    	this.open = true;
    	this.closedImage.set(false);
    }
    
    public void close() {
    	this.open = false;
    	this.closedImage.set(true);
    }
    
    public boolean isOpen() {
    	return this.open;
    }
    
    public BooleanProperty getClosedImage() {
    	return this.closedImage;
    }
    
    public int getID() {
    	return this.id;
    }
    

}