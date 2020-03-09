package unsw.dungeon;

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public abstract class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;
    private Dungeon dungeon;
    private PlayerInteraction interaction;
    private Movement movement;
//    private PlayerPickUp pickUp;
    private PlayerCollision collision;
    private BooleanProperty onMap;


    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y, Dungeon dungeon) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.dungeon = dungeon;
        this.interaction = new PlayerNoInteraction();
        this.movement = new NoMovement();
//        this.pickUp = new PlayerNoPickUp();
        this.collision = new PlayerNoCollision();
        this.onMap = new SimpleBooleanProperty(false);
    }
    
    public void interact(Player player) {
    	interaction.interact(player, this);
    }
    
    public void move(int x, int y) {
    	if (movement.move(x, y, this)) {
    		runCollisions();
    	}
    }
    
    public boolean canMove(int x, int y) {
    	return this.movement.canMove(x, y, this.dungeon);
    }
    
    private void runCollisions() {
    	List<Entity> entityList = getDungeon().getEntitiesOnCoordinates(getX(), getY());
    	for (Entity e : entityList) {
    		if (!e.equals(this)) {
    			e.collide(this);
    		}
    	}
    }
    
    public boolean pickUp(Player player) {
    	return false;
    }
    
    public Dungeon getDungeon() {
    	return this.dungeon;
    }
    
    public Movement getMovement() {
    	return this.movement;
    }
        
    public PlayerInteraction getInteraction() {
    	return this.interaction;
    }

    public void setOnMap (boolean b) {
    	this.onMap.set(b);
    }
    
    public BooleanProperty getOnMap() {
    	return this.onMap;
    }
    
    
    public void setInteraction(PlayerInteraction interaction) {
		this.interaction = interaction;
	}

	public void setMovement(Movement movement) {
		this.movement = movement;
	}
	
//	public void setPlayerPickUp(PlayerPickUp pickUp) {
//		this.pickUp = pickUp;
//	}
	
	public void setCollision(PlayerCollision collision) {
		this.collision = collision;
	}
	
	public void collide(Entity entity) {
		this.collision.collide(entity, this);
	}

	public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

    public int getY() {
        return y().get();
    }

    public int getX() {
        return x().get();
    }
    
    public void setPosition(int x, int y) {
    	this.x().set(x);
    	this.y().set(y);
    }
    
    public abstract boolean isPickUp();
    
    public abstract boolean isBlocking();
    
}
