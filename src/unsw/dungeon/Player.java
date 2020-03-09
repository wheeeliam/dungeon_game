package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.dungeon.Observer;
import unsw.dungeon.PlayerInventory;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity implements Subject, Observer {

    private PlayerInventory inventory;
    private PlayerState state;
    private PlayerDirection direction;
    private ArrayList<Observer> observers;
	private IntegerProperty directionImage;
	private BooleanProperty invisibleImage;
	private BooleanProperty invincibleImage;
	private IntegerProperty stateImage;
	
	private IntegerProperty hitsLeft;
	private BooleanProperty keyObtained;
	private IntegerProperty	treasuresCollected;
	private IntegerProperty stepsLeft;


    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);        
        this.inventory = new PlayerInventory();
        this.state = new PlayerNormalState();
        this.direction = new PlayerDownDirection();
        this.directionImage = new SimpleIntegerProperty(0);
        this.invisibleImage = new SimpleBooleanProperty(false);
        this.invincibleImage = new SimpleBooleanProperty(false);
        this.stateImage = new SimpleIntegerProperty(0);
        this.observers = new ArrayList<Observer>();
        setCollision(new PlayerEnemyCollision());
        setMovement(new NormalMovement());
        getDungeon().addObserver(this);
        
        this.hitsLeft = new SimpleIntegerProperty(0);
        this.keyObtained = new SimpleBooleanProperty(false);
        this.treasuresCollected = new SimpleIntegerProperty(0);
        this.stepsLeft = new SimpleIntegerProperty(0);
    }

    public IntegerProperty getPlayerState() {
    	return this.stateImage;
    }
    
    public void setPlayerStateImage(int i) {
    	this.stateImage.set(i);
    }
    
    public void moveUp() {
    	getDungeon().startTurn();
        move(getX(), getY() - 1);
        setDirection(new PlayerUpDirection());
        setDirectionImage(determineDirectionImage());
        getDungeon().passTurn();
    }

    public void moveDown() {
    	getDungeon().startTurn();
    	move(getX(), getY() + 1);
        setDirection(new PlayerDownDirection());
        setDirectionImage(determineDirectionImage());
        getDungeon().passTurn();
    }

    public void moveLeft() {
    	getDungeon().startTurn();
    	move(getX() - 1, getY());
        setDirection(new PlayerLeftDirection());
        setDirectionImage(determineDirectionImage());
        getDungeon().passTurn();
    }

    public void moveRight() {
    	getDungeon().startTurn();
    	move(getX() + 1, getY());
        setDirection(new PlayerRightDirection());
        setDirectionImage(determineDirectionImage());
        getDungeon().passTurn();
    }
    

    private int determineDirectionImage() {
    	if (state instanceof PlayerNormalState) {
    		if (direction instanceof PlayerDownDirection) return 0;
    		else if (direction instanceof PlayerLeftDirection) return 1;
    		else if (direction instanceof PlayerUpDirection) return 2;
    		else if (direction instanceof PlayerRightDirection) return 3;
    	} else if (state instanceof PlayerInvincibleState) {
    		if (direction instanceof PlayerDownDirection) return 4;
    		else if (direction instanceof PlayerLeftDirection) return 5;
    		else if (direction instanceof PlayerUpDirection) return 6;
    		else if (direction instanceof PlayerRightDirection) return 7;
    	} else if (state instanceof PlayerStateInvisible) {
    		if (direction instanceof PlayerDownDirection) return 8;
    		else if (direction instanceof PlayerLeftDirection) return 9;
    		else if (direction instanceof PlayerUpDirection) return 10;
    		else if (direction instanceof PlayerRightDirection) return 11;
    	}
    	return -1;
    }
    
    public int getDirectionNumber() {
		if (direction instanceof PlayerDownDirection) return 0;
		else if (direction instanceof PlayerLeftDirection) return 1;
		else if (direction instanceof PlayerUpDirection) return 2;
		else if (direction instanceof PlayerRightDirection) return 3;
		return -1;
    }
    
    public void progressState() {
    	this.state.progress(this);
    }

    public void collideWithEnemy(Enemy enemy) {
    	this.state.collideWithEnemy(this, enemy);
    }
    
    private void setDirection(PlayerDirection direction) {
    	this.direction = direction;
    }
    
    public PlayerDirection getDirection() {
    	return this.direction;
    }
    
    public boolean hasSword () {
		return inventory.hasSword();
	}
	
	public boolean hasKey() {
		return inventory.hasKey();
	}
	
	public Key getKey() {
		return inventory.getKey();
	}
	
	public void removeKey() {
		this.setHasKey(false);
		inventory.removeKey();
	}

	
	public Sword getSword() {
		return inventory.getSword();
	}
	
	public void removeSword() {
		inventory.removeSword();
	}
	
	public void setSword(Sword sword) {
		inventory.setSword(sword);
	}
	
	public void setKey(Key key) {
		this.setHasKey(true);
		inventory.setKey(key);
	}
	
	
	public void addTreasure(Treasure treasure) {
		this.setTreasuresCollected(this.getTreasuresCollected().intValue() + 1);
		inventory.addTreasure(treasure);
	}
	
	public int getNumberOfTreasures() {
		return inventory.getNumberOfTreasures();
	}
	
	public ArrayList<Treasure> getTreasures() {
		return this.inventory.getTreasures();
	}
    
    private int getAdjacentX() {
    	return this.direction.getAdjacentX(this);
    }
    
    private int getAdjacentY() {
    	return this.direction.getAdjacentY(this);
    }
    
    public IntegerProperty getDirectionImage() {
    	return this.directionImage;
    }
    
    public void setDirectionImage(int i) {
    	this.directionImage.set(i);
    }
    
    //TODO
    public void interactWithEntity() {
    	getDungeon().startTurn();
    	List<Entity> entityList = getDungeon().getEntitiesOnCoordinates(getAdjacentX(), getAdjacentY());    	
    	for (Entity e : entityList) {
    		e.interact(this);
    	}
    	getDungeon().passTurn();
    }
    
    public void pickUp() {
    	getDungeon().startTurn();
    	List<Entity> entityList = getDungeon().getEntitiesOnCoordinates(getX(), getY()); 
    	for (Entity e : entityList) {
    		if (e.pickUp(this)) {
    			getDungeon().removeEntity(e);
    			break;
	    		
    		}
    	}
    	getDungeon().passTurn();
    }
    
    public BooleanProperty getInvincibleImage() {
    	return this.invincibleImage;
    }
    
    public void setInvincibleImage(Boolean b) {
    	this.invincibleImage.set(b);
    }
    
    public BooleanProperty getInvisibleImage() {
    	return this.invisibleImage;
    }
    
    public void setInvisibleImage(Boolean b) {
    	this.invisibleImage.set(b);
    }
    
//    public void setInvincible(int time) {
//    	if (!this.state.isInvincible()) {
//    		this.notifyObservers();
//    	}
//    	setPlayerState(new PlayerInvincibleState(time));
//    }
    
    public void setNormal() {
    	setMovement(new NormalMovement());
    	setPlayerState(new PlayerNormalState());
    	this.notifyObservers();
    }
    
    public boolean isInvincible() {
    	return this.state.isInvincible();
    }
    
    public boolean isInvisible() {
    	return this.state.isInvisible();
    }
    
    public int potionLeft() {
    	return this.state.potionLeft();
    }
    
    public void setPlayerState(PlayerState state) {
    	this.state = state;
    }
    
    @Override
    public boolean isPickUp() {
    	return false;
    }
    
    @Override
    public boolean isBlocking() {
    	return false;
    }
    
	@Override
	public void addObserver(Observer o) {
		if (!this.observers.contains(o)) {
			this.observers.add(o);
		}
	}

	@Override
	public void removeObserver(Observer o) {
		if (this.observers.contains(o)) {
			this.observers.remove(o);
		}
	}

	@Override
	public void notifyObservers() {
		for (Observer o : this.observers) {
			o.update(this);
		}
	}
	
	public void getsKilled() {
		getDungeon().removePlayer(this);
	}

	@Override
	public void update(Subject s) {
	    this.state.progress(this);
	}
	
	public IntegerProperty getHitsLeft() {
		return this.hitsLeft;
	}
	
	public void setHitsLeft(int i) {
		this.hitsLeft.set(i);
	}
	
	public IntegerProperty getTreasuresCollected() {
		return this.treasuresCollected;
	}
	
	public void setTreasuresCollected(int i) {
		this.treasuresCollected.set(i);
	}
	
	public IntegerProperty getStepsLeft() {
		return this.stepsLeft;
	}
	
	public void setStepsLeft(int i) {
		this.stepsLeft.set(i);
	}
	
	public BooleanProperty getHasKey() {
		return this.keyObtained;
	}
	
	public void setHasKey(Boolean b) {
		this.keyObtained.set(b);
	}
}
