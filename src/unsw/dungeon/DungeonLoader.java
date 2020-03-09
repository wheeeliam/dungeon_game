package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        
        JSONObject jsonGoals = json.getJSONObject("goal-condition");

        loadGoals(dungeon, jsonGoals);
        System.out.println(dungeon.getGoal() == null);
        System.out.println(dungeon.getEntities());

        return dungeon;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(x, y, dungeon);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y, dungeon);
            onLoad(wall);
            entity = wall;
            break;
        case "boulder":
        	Boulder boulder = new Boulder(x, y, dungeon);
        	onLoad(boulder);
        	entity = boulder;
        	break;
        case "exit":
        	Exit exit = new Exit(x, y, dungeon);
        	onLoad(exit);
        	entity = exit;
        	break;
        case "treasure":
        	Treasure treasure = new Treasure(x, y, dungeon);
        	onLoad(treasure);
        	entity = treasure;
        	break;
        case "door":
            int doorID = json.getInt("id");
        	Door door = new Door(x, y, doorID, dungeon);
        	onLoad(door);
        	entity = door;
        	break;
        case "key":
        	int keyID = json.getInt("id");
        	Key key = new Key(x, y, keyID, dungeon);
        	onLoad(key);
        	entity = key;
        	break;
        case "switch":
        	FloorSwitch floorSwitch = new FloorSwitch(x, y, dungeon);
        	onLoad(floorSwitch);
        	entity = floorSwitch;
        	break;
        case "portal":
        	int portalID = json.getInt("id");
        	Portal portal = new Portal(x, y, portalID, dungeon);
        	onLoad(portal);
        	entity = portal;
        	break;
        case "enemy":
        	Enemy enemy = new Enemy(x, y, dungeon);
        	onLoad(enemy);
        	entity = enemy;
        	break;
        case "sword":
        	Sword sword = new Sword(x, y, dungeon);
        	onLoad(sword);
        	entity = sword;
        	break;
        case "invincibility":
        	Potion potion = new Potion(x, y, dungeon, new PotionAbilityInvincible());
        	onLoad(potion);
        	entity = potion;
        	break;
        case "invisibility":
        	Potion potion2 = new Potion(x, y, dungeon, new PotionAbilityGhost());
        	onLoad(potion2);
        	entity = potion2;
        	break;
        case "bomb":
        	int bombTime = json.getInt("time");
        	Bomb bomb = new Bomb(x, y, dungeon, bombTime);
        	onLoad(bomb);
        	entity = bomb;
        	break;
        case "ghost":
        	Ghost ghost = new Ghost(x, y, dungeon);
        	onLoad(ghost);
        	entity = ghost;
        	break;
        default:
        	break;

        }
        dungeon.addEntity(entity);
    }
    
    private void loadGoals(Dungeon dungeon, JSONObject jsonGoals) {
    	dungeon.setGoal(processGoal(dungeon, jsonGoals));

    }
    
    private Goal processGoal(Dungeon dungeon, JSONObject jsonGoals) {
    	// Base goal when only one key
		if (jsonGoals.length() == 1) {
	    	String goalName = jsonGoals.getString("goal");
	    	return getBaseGoal(dungeon, goalName);
	    	
		// Complex goal when more than one key
		} else if (jsonGoals.length() > 1) {
			ComplexGoal newCompGoal = null;
			if (jsonGoals.getString("goal").equals("OR")) {
	        	newCompGoal = new ComplexGoal(new OrCompletion());	
			} else {
	        	newCompGoal = new ComplexGoal(new AndCompletion());	
			}
    		JSONArray subgoals = jsonGoals.getJSONArray("subgoals");
    		for (int i = 0; i < subgoals.length(); i++) {
    			JSONObject subgoal = subgoals.getJSONObject(i);
    			newCompGoal.addGoal(processGoal(dungeon, subgoal));
    		}
        	return newCompGoal;
		}
		return null;
	}
    
    
    private BaseGoal getBaseGoal(Dungeon dungeon, String goalname) {
        switch (goalname) {
        case "boulders":
            return new BaseGoal(dungeon, new BoulderGoalStrategy(dungeon), goalname);
        case "exit":
            return new BaseGoal(dungeon, new ExitGoalStrategy(dungeon), goalname);
        case "treasure":
        	return new BaseGoal(dungeon, new TreasureGoalStrategy(dungeon), goalname);
        case "enemies":
        	return new BaseGoal(dungeon, new EnemiesGoalStrategy(dungeon), goalname);
        }
        return null;
    }

    // TODO Handle other possible entities

    public abstract void onLoad(Player player);

    public abstract void onLoad(Wall wall);
    
    public abstract void onLoad(Boulder boulder);
    
    public abstract void onLoad(Exit exit);

    public abstract void onLoad(Treasure treasure);

    public abstract void onLoad(Door door);

    public abstract void onLoad(Key key);

    public abstract void onLoad(FloorSwitch floorSwitch);

    public abstract void onLoad(Portal portal);

    public abstract void onLoad(Enemy enemy);

    public abstract void onLoad(Sword sword);

    public abstract void onLoad(Potion potion);

	public abstract void onLoad(Bomb bomb);
	
	public abstract void onLoad(Ghost ghost);

}


    

    // TODO Create additional abstract methods for the other entities


