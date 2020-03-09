package unsw.dungeon;

import java.util.ArrayList;

public class PlayerInventory {
	private ArrayList<Treasure> treasures;
	private Sword sword;
	private Key key;
	
	public PlayerInventory() {
        this.treasures = new ArrayList<>();
        this.sword = null;
        this.key = null;
	}
	
	public boolean hasSword () {
		if (this.sword == null) return false;
		return true;
	}
	
	public boolean hasKey() {
		if (this.key == null) return false;
		return true;
	}
	
	
	public Key getKey() {
		return this.key;
	}
	
	public void removeKey() {
		this.key = null;
	}

	
	public Sword getSword() {
		return this.sword;
	}
	
	public void removeSword() {
		this.sword = null;
	}
	
	public void setSword(Sword sword) {
		this.sword = sword;
	}
	
	public void setKey(Key key) {
		this.key = key;
	}
	
	public void addTreasure(Treasure treasure) {
		this.treasures.add(treasure);
	}
	
	public ArrayList<Treasure> getTreasures() {
		return this.treasures;
	}
	
	public int getNumberOfTreasures() {
		return this.treasures.size();
	}
}

