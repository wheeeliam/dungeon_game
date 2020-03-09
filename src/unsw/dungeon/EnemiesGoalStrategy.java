package unsw.dungeon;

public class EnemiesGoalStrategy implements GoalStrategy, Observer {
	
	private int numEnemies;
	private int numKilledEnemies;
	
	public EnemiesGoalStrategy(Dungeon dungeon) {
		numEnemies = dungeon.getEnemies().size();
		numKilledEnemies = 0;
		for (Enemy e : dungeon.getEnemies()) {
			e.addObserver(this);
		}
	}
	
	@Override
	public void update(Subject s) {
		numKilledEnemies++;
		
	}
	@Override
	public boolean isComplete() {
		if (numKilledEnemies >= numEnemies) return true;
		return false;
	}
}
