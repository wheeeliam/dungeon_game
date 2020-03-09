package unsw.dungeon;

public class ExitGoalStrategy implements GoalStrategy, Observer {
	
	private Exit exit;
	private boolean justSteppedOnExit;
	
	public ExitGoalStrategy(Dungeon dungeon) {
		this.exit = dungeon.getExit();
		exit.addObserver(this);
		justSteppedOnExit = false;
	}
	
	@Override
	public boolean isComplete() {
		// Also have to check if other goals have been completed
		if (justSteppedOnExit) return true;
		return false;
	}

	@Override
	public void update(Subject s) {
		if (!justSteppedOnExit && ((Exit)s).getNumTurns() == 1) justSteppedOnExit = true;
		else justSteppedOnExit = false;
		
	}
	

}
