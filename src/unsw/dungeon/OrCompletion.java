package unsw.dungeon;

import java.util.ArrayList;

public class OrCompletion implements Completion {

	@Override
	public boolean isComplete(ArrayList<Goal> goals) {
		for (Goal g : goals) {
			if (g.isComplete()) {
				return true;
			}
		}
		return false;
	}
}
