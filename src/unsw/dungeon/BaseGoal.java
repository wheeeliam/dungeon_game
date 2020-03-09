package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class BaseGoal implements Goal {
	
	private String name;
	private Dungeon dungeon;
	private GoalStrategy goalCompletion;
	private BooleanProperty isCompleteBP;
	
	
	public BaseGoal(Dungeon dungeon, GoalStrategy goalCompletion, String name) {
		this.dungeon = dungeon;
		this.goalCompletion = goalCompletion; 
		this.name = name;
		this.isCompleteBP = new SimpleBooleanProperty(false);
	}
	
	@Override
	public boolean isComplete() {
		return goalCompletion.isComplete();
	}
	
	public String getName() {
		return this.name;
	}
	
	public BooleanProperty getIsCompleteBP() {
		return this.isCompleteBP;
	}
	
	public void setIsCompleteBP(Boolean b) {
		this.isCompleteBP.set(b);
	}
}
