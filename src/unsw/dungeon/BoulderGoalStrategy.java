package unsw.dungeon;

public class BoulderGoalStrategy implements GoalStrategy, Observer {
	
	private int numSwitches;
	private int numSwitchesCovered;
	
	public BoulderGoalStrategy(Dungeon dungeon) {
		numSwitches = dungeon.getSwitches().size();
		numSwitchesCovered = 0;
		for (FloorSwitch fs : dungeon.getSwitches()) {
			fs.addObserver(this);
		}
	}
	
	@Override
	public boolean isComplete() {
		if (numSwitchesCovered >= numSwitches) return true;
		return false;
	}

	@Override
	public void update(Subject s) {
		if (s instanceof FloorSwitch) {
			FloorSwitch fs = (FloorSwitch) s;
			if (fs.isTriggered()) numSwitchesCovered++;
			else numSwitchesCovered--;
		}
	}
}
