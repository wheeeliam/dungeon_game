package unsw.dungeon;

import java.util.ArrayList;

public class AndCompletion implements Completion {
	
	@Override
	public boolean isComplete(ArrayList<Goal> goals) {
		for (Goal g : goals) {
			if (!g.isComplete()) {
				return false;
			}
		}
		return true;
	}
}
