/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon implements Subject {

    private int width, height;
    private List<Entity> entities;
    private List<Treasure> treasures;
    private List<Enemy> enemies;
    private List<FloorSwitch> switches;
    private Exit exit;
    private Player player;
    private String dungeonState;
    private Goal goal;
    private List<Bomb> explodedBombs;
    
    private List<Observer> observers;
    
    private DungeonController dc;
    
    
    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.treasures = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.switches = new ArrayList<>();
        this.exit = null;
        this.player = null;
        this.dungeonState = "Ongoing";
        this.goal = null;
        this.observers = new ArrayList<>();
        this.explodedBombs = new ArrayList<>();
        this.dc = null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }
    
    public Goal getGoal() {
    	return goal;
    }
    
    public List<Treasure> getTreasures() {
		return treasures;
	}
    

	public List<Enemy> getEnemies() {
		return enemies;
	}
	
	public void removeEnemy(Enemy enemy) {
		enemies.remove(enemy);
	}

	public List<FloorSwitch> getSwitches() {
		return switches;
	}

	public Exit getExit() {
		return exit;
	}

	public String getDungeonState() {
    	return dungeonState;
    }
    
    public List<Entity> getEntities(){
    	return entities;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    	entity.setOnMap(true);
    }
    
    public void addTreasure(Treasure treasure) {
        treasures.add(treasure);
    }
    
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    
    public void addFloorSwitch(FloorSwitch floorSwitch) {
        switches.add(floorSwitch);
    }
    
    public void addExit(Exit exit) {
        this.exit = exit;
    }
    
    
    public void setGoal(Goal goal) {
    	this.goal = goal;
    }
    
    public List<Entity> getEntitiesOnCoordinates(int x, int y) {
    	
    	List<Entity> entityList = new ArrayList<>();
    	for (Entity e : this.entities) {

    		if (x == e.getX() && y == e.getY()) {
    			
    			entityList.add(e);
    		}
    	}
    	return entityList;
    }
    
    public boolean hasBlocking(int x, int y) {
    	List<Entity> entityList = getEntitiesOnCoordinates(x, y);
    	for (Entity e : entityList) {
    		if (e.isBlocking()) return true;
    	}
    	return false;
    }
    
    public boolean hasPickUp(int x, int y) {
    	List<Entity> entityList = getEntitiesOnCoordinates(x, y);
    	for (Entity e : entityList) {
    		if (e.isPickUp()) return true;
    	}
    	return false;
    }
    
    public boolean hasEnemy(int x, int y) {
    	List<Entity> entityList = getEntitiesOnCoordinates(x, y);
    	for (Entity e : entityList) {
    		if (e instanceof Enemy) return true;
    	}
    	return false;
    }
     
    public void setDungeonState(String state) {
    	this.dungeonState = state;
    }
    
    public void removeEntity(Entity entity) {
    	entities.remove(entity);
    	if (observers.contains(entity)) {
    		removeObserver((Observer)entity);
    	}
    	entity.setOnMap(false);
    	//remove from gridpane
    }
    
    public void setFail() {
    	this.dungeonState = "Failed";
//    	this.entities.remove(this.player);
//    	System.out.println("player removed");
//    	this.player = null;
//    	System.out.println("player is null");

    	//terminate();
    }
    
    public void passTurn() {
    	if (dungeonState.equals("Ongoing")) {
    		checkGoal();
    	}
    	notifyObservers();
//    	System.out.println("end of turn");
//    	for (Entity e : entities) {
//    		if (!(e instanceof Wall)) {
//    			System.out.println(e);
//    		}
//    	}
//    	progressBombs();
//    	moveEnemies();
//    	progressPlayerState();
    	
    }
    
    public void checkGoal() {
    	if (this.goal == null) return;
    	if (this.goal.isComplete()) {
    		this.dungeonState = "Complete";
    		terminate();
    	}
    }
    
    private void terminate() {
    	if (dc == null) return;
    	if (this.dungeonState.equals("Complete")) dc.setVictoryScene();
    	else if (this.dungeonState.equals("Failed")) dc.setFailedScene();
     }
    
    public void setController(DungeonController dc) {
    	this.dc = dc;
    }
    

    
    public void updateFloorSwitches() {
    	for (FloorSwitch fs : switches) {
    		fs.updateTriggerStatus();
    	}
    }
    
    public void updateExitStatus() {
    	if (exit != null)
    		exit.notifyObservers();
    }
    
    public void startTurn() {
    	updateExitStatus();
    	updateExplodedBombs();
    	terminate();
    	
    }
    
    private void updateExplodedBombs() {
    	for (Bomb b : explodedBombs) {
    		b.setHasBombActivated(false);
    	}
    }
    
    public void removePlayer(Player player) {
    	removeEntity(player);
//    	this.player = null;
    	for (Enemy e : enemies) {
    		e.removePlayer(player);
    	}
    	setFail();
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
		List <Observer> tmpList = new ArrayList<>();
		
		for (Observer o : observers) {
			tmpList.add(o);
		}
		for (Observer o : tmpList) {
			o.update(this);
		}
	}
	
	public void addExplodedBomb(Bomb bomb) {
		this.explodedBombs.add(bomb);
	}
	
	public boolean surroundedByBlocking(int x, int y) {
		if (!hasBlocking(x, y) && withinDungeon(x, y)) return false;
		if (!hasBlocking(x+1, y) && withinDungeon(x+1, y)) return false;
		if (!hasBlocking(x-1, y) && withinDungeon(x-1, y)) return false;
		if (!hasBlocking(x, y+1) && withinDungeon(x, y+1)) return false;
		if (!hasBlocking(x, y-1) && withinDungeon(x, y-1)) return false;
		return true;



	}
	
	public boolean withinDungeon(int x, int y) {
		if (x < 0) return false;
    	if (y < 0) return false;
    	if (x >= width) return false;
    	if (y >= height) return false;
    	return true;
	}
    
    
}
