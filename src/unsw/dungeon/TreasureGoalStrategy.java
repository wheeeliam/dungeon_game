package unsw.dungeon;

public class TreasureGoalStrategy implements GoalStrategy, Observer {
	
	private int numTreasures;
	private int numTreasuresPickedUp;
	
	public TreasureGoalStrategy(Dungeon dungeon) {
		numTreasures = dungeon.getTreasures().size();
		numTreasuresPickedUp = 0;
		for (Treasure t : dungeon.getTreasures()) {
			t.addObserver(this);
		}
	}

	@Override
	public boolean isComplete() {
		if (numTreasuresPickedUp >= numTreasures) return true;
		return false;
	}

	@Override
	public void update(Subject s) {
		numTreasuresPickedUp++;
	}
	
	

}
